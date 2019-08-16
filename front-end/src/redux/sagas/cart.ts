import { call, put, takeLatest, select } from "redux-saga/effects";
import _ from "underscore";
import history from "../../history";
import * as cartActionTypes from "../actionTypes/cart";
import * as cartActionCreators from "../actionCreators/cart";
import * as cartTypes from "../../types/cart";
import * as userActionCreators from "../actionCreators/user";
import { addCart, getCart, changeCart, removeCart } from "../../api/cart";
import { CommonError } from "../../api/CommonError";
import { handleError } from "../../api/common";

function* initializeLocalCartSagas() {
  const initCart = {
    size: 0,
    totalPrice: 0,
    menus: []
  } as cartTypes.ICart;
  yield localStorage.setItem("localCart", JSON.stringify(initCart));
}

function* addLocalCartSagas(action: cartTypes.IAddLocalCart) {
  try {
    // 로컬 카트가 비어있으면 로컬 카트를 생성한다.
    const state = yield select();
    const { localCart } = state.cart;
    if (!localCart) {
      yield put(cartActionCreators.initializeLocalCart());
    }

    // 초기화 후의 로컬 카트를 불러오기 위해 다시 셀렉트한다.
    const newState = yield select();

    // 언더스코어로 새로운 객체를 생성한 뒤 프로퍼티들을 변경한다.
    const newCart = _(newState.cart.localCart).clone() as cartTypes.ICart;
    newCart.size += action.payload.quantity;
    newCart.totalPrice += action.payload.price * action.payload.quantity;
    newCart.menus = newCart.menus.concat(action.payload);

    // 완성된 객체를 로컬 스토리지와 상태에 저장한다.
    yield localStorage.setItem("localCart", JSON.stringify(newCart));
    alert("상품이 담겼습니다.");
    yield put(cartActionCreators.addLocalCartFulfilled(newCart));
  } catch (error) {
    alert("문제가 발생했습니다!");
    yield put(cartActionCreators.addLocalCartRejected(error));
  }
}

function* removeLocalCartSagas(action: cartTypes.IRemoveLocalCart) {
  try {
    const state = yield select();
    const { localCart } = state.cart;

    // 언더스코어로 새로운 객체를 생성한 뒤 프로퍼티들을 변경한다.
    const newCart = _(localCart).clone() as cartTypes.ICart;
    const targetMenus = newCart.menus.filter((m: cartTypes.ICartMenu, index: number) => {
      if (index !== action.payload) {
        return true;
      } else {
        newCart.size -= m.quantity;
        newCart.totalPrice -= m.price * m.quantity;
        return false;
      }
    });
    newCart.menus = targetMenus;

    // 완성된 객체를 로컬 스토리지와 상태에 저장한다.
    yield localStorage.setItem("localCart", JSON.stringify(newCart));
    yield put(cartActionCreators.removeLocalCartFulfilled(newCart));
  } catch (error) {
    alert("문제가 발생했습니다!");
    yield put(cartActionCreators.removeLocalCartRejected(error));
  }
}

function* changeLocalCartSagas(action: cartTypes.IChangeLocalCart) {
  try {
    const state = yield select();

    // 언더스코어로 새로운 객체를 생성한 뒤 프로퍼티들을 변경한다.
    const newCart = _(state.cart.localCart).clone() as cartTypes.ICart;
    const targetMenus = newCart.menus.map((m: cartTypes.ICartMenu, index: number) => {
      if (index === action.payload.id) {
        const changeQuantity = m.quantity - action.payload.quantity;
        if (changeQuantity > 0) {
          // 수량이 줄었을 경우.
          newCart.size -= changeQuantity;
          newCart.totalPrice -= m.price * changeQuantity;
        } else {
          // 수량이 늘었을 경우.
          newCart.size += -changeQuantity;
          newCart.totalPrice += m.price * -changeQuantity;
        }
        m.quantity = action.payload.quantity;
        return m;
      } else {
        return m;
      }
    });
    newCart.menus = targetMenus;

    // 완성된 객체를 로컬 스토리지와 상태에 저장한다.
    yield localStorage.setItem("localCart", JSON.stringify(newCart));
    yield put(cartActionCreators.changeLocalCartFulfilled(newCart));
  } catch (error) {
    alert("문제가 발생했습니다!");
    yield put(cartActionCreators.changeLocalCartRejected(error));
  }
}

function* fetchSetCartSagas(action: cartTypes.IFetchSetCart) {
  try {
    // 로컬 카트에 메뉴가 있으면 먼저 카트 디비에 넣고 로컬 카트는 초기화한다.
    const localCart = yield localStorage.getItem("localCart");
    if (localCart) {
      const parsedLocalCart = JSON.parse(localCart) as cartTypes.ICart;
      if (parsedLocalCart.size > 0) {
        yield put(cartActionCreators.fetchAddCart(parsedLocalCart.menus));
        yield put(cartActionCreators.initializeLocalCart());
      }
    }

    const data = yield call(getCart);
    if (data instanceof CommonError) {
      throw data;
    }

    yield put(cartActionCreators.fetchSetCartFulfilled(data));
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
    }
    yield put(cartActionCreators.fetchSetCartRejected(result));
  }
}

function* fetchAddCartSagas(action: cartTypes.IFetchAddCart) {
  try {
    // 저장하고자 하는 카트 메뉴들을 불러와 API에 전송할 형태로 만든다.
    const cartMenus = action.payload;
    const cartReqs = cartMenus.map((m: cartTypes.ICartMenu) => {
      return {
        direct: m.direct,
        menuId: m.menuId,
        option: m.optionArray,
        quantity: m.quantity
      } as cartTypes.ICartReq;
    });

    // 만든 형태를 API로 전송하고, 응답으로 받은 카트 아이디들을 저장한다.
    // API 요청 분리.
    const data = yield call(addCart, cartReqs);
    if (data instanceof CommonError) {
      throw data;
    }

    // 현재 카트 상태에 새로운 카트 메뉴들을 추가한다.
    const state = yield select();
    const newCart = _(state.cart.cart).clone() as cartTypes.ICart;
    const newCartMenus = cartMenus.map((m: cartTypes.ICartMenu, index: number) => {
      newCart.size += m.quantity;
      newCart.totalPrice += m.quantity * m.price;
      m.cartId = data[index].cartId;
      return m;
    });
    newCart.menus = newCart.menus.concat(newCartMenus);
    alert("상품이 담겼습니다.");
    yield put(cartActionCreators.fetchAddCartFulfilled(newCart));
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
    }
    yield put(cartActionCreators.fetchAddCartRejected(result));
  }
}

function* fetchRemoveCartSagas(action: cartTypes.IFetchRemoveCart) {
  try {
    // 카트 아이디로 디비에 있는 해당 메뉴를 삭제한다.
    const data = yield call(removeCart, action.payload);
    if (data instanceof CommonError) {
      throw data;
    }

    // 현재 카트 상태에서 해당 카트 아이디를 가진 메뉴를 삭제한다.
    const state = yield select();
    const newCart = _(state.cart.cart).clone() as cartTypes.ICart;
    const targetMenu = newCart.menus.filter(m => {
      return m.cartId === action.payload;
    })[0];
    newCart.size -= targetMenu.quantity;
    newCart.totalPrice -= targetMenu.quantity * targetMenu.price;
    newCart.menus = newCart.menus.filter(m => {
      return m.cartId !== action.payload;
    });
    yield put(cartActionCreators.fetchRemoveCartFulfilled(newCart));
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
    }
    yield put(cartActionCreators.fetchRemoveCartRejected(result));
  }
}

function* fetchChangeCartSagas(action: cartTypes.IFetchChangeCart) {
  try {
    // 카트 아이디와 수량으로 디비에 있는 해당 메뉴를 삭제한다.
    const data = yield call(changeCart, action.payload.id, action.payload.quantity);
    if (data instanceof CommonError) {
      throw data;
    }

    // 해당 아이디에 해당하는 메뉴의 개수를 변경하고, 그에 따라 전체 사이즈와 가격도 변경한다.
    const state = yield select();
    const newCart = _(state.cart.cart).clone() as cartTypes.ICart;
    const targetMenus = newCart.menus.map((m: cartTypes.ICartMenu) => {
      if (m.cartId === action.payload.id) {
        const changeQuantity = m.quantity - action.payload.quantity;
        if (changeQuantity > 0) {
          // 수량이 줄었을 경우.
          newCart.size -= changeQuantity;
          newCart.totalPrice -= m.price * changeQuantity;
        } else {
          // 수량이 늘었을 경우.
          newCart.size += -changeQuantity;
          newCart.totalPrice += m.price * -changeQuantity;
        }
        m.quantity = action.payload.quantity;
        return m;
      } else {
        return m;
      }
    });
    newCart.menus = targetMenus;
    yield put(cartActionCreators.fetchChangeCartFulfilled(newCart));
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
    }
    yield put(cartActionCreators.fetchChangeCartRejected(result));
  }
}

function* setOrderCartSagas(action: cartTypes.ISetOrderCart) {
  yield localStorage.setItem("orderCart", JSON.stringify(action.payload));
  yield localStorage.setItem("isDirect", "N");
  yield history.push("/payment");
}

export default function* userSagas() {
  yield takeLatest(cartActionTypes.INITIALIZE_LOCAL_CART, initializeLocalCartSagas);
  yield takeLatest(cartActionTypes.ADD_LOCAL_CART, addLocalCartSagas);
  yield takeLatest(cartActionTypes.REMOVE_LOCAL_CART, removeLocalCartSagas);
  yield takeLatest(cartActionTypes.CHANGE_LOCAL_CART, changeLocalCartSagas);
  yield takeLatest(cartActionTypes.FETCH_SET_CART, fetchSetCartSagas);
  yield takeLatest(cartActionTypes.FETCH_ADD_CART, fetchAddCartSagas);
  yield takeLatest(cartActionTypes.FETCH_REMOVE_CART, fetchRemoveCartSagas);
  yield takeLatest(cartActionTypes.FETCH_CHANGE_CART, fetchChangeCartSagas);
  yield takeLatest(cartActionTypes.SET_ORDER_CART, setOrderCartSagas);
}
