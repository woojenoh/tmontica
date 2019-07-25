import { put, takeEvery, select } from "redux-saga/effects";
import _ from "underscore";
import axios from "axios";
import * as cartActionTypes from "../actionTypes/cart";
import * as cartActionCreators from "../actionCreators/cart";
import * as cartTypes from "../../types/cart";

function* addLocalCartSagas(action: cartTypes.IAddLocalCart) {
  try {
    const state = yield select();
    const { localCart } = yield state.cart;
    // 로컬 카트가 비어있으면 로컬 카트 생성.
    if (!localCart) {
      yield put(cartActionCreators.initializeLocalCart());
    }
    // 초기화 후의 로컬 카트를 불러오기 위해 다시 셀렉트.
    const newState = yield select();
    const { localCart: newLocalCart } = yield newState.cart;
    // 언더스코어로 새로운 객체를 생성한 뒤 프로퍼티들 변경.
    const newCart = yield _(newLocalCart).clone();
    newCart.size += yield action.payload.quantity;
    newCart.totalPrice += yield action.payload.price * action.payload.quantity;
    newCart.menus = yield newCart.menus.concat(action.payload);
    // 완성된 객체를 로컬 스토리지와 상태에 저장.
    yield localStorage.setItem("LocalCart", JSON.stringify(newCart));
    yield put(cartActionCreators.addLocalCartFulfilled(newCart));
  } catch (error) {
    yield alert("문제가 발생했습니다!");
    yield put(cartActionCreators.addLocalCartRejected(error));
  }
}

function* removeLocalCartSagas(action: cartTypes.IRemoveLocalCart) {
  try {
    const state = yield select();
    const { localCart } = yield state.cart;
    // 언더스코어로 새로운 객체를 생성한 뒤 프로퍼티들 변경.
    const newCart = yield _(localCart).clone();
    const targetMenus = yield newCart.menus.filter((m: cartTypes.ICartMenu, index: number) => {
      if (index !== action.payload) {
        return true;
      } else {
        newCart.size -= m.quantity;
        newCart.totalPrice -= m.price * m.quantity;
        return false;
      }
    });
    newCart.menus = targetMenus;
    // 완성된 객체를 로컬 스토리지와 상태에 저장.
    yield localStorage.setItem("LocalCart", JSON.stringify(newCart));
    yield put(cartActionCreators.removeLocalCartFulfilled(newCart));
  } catch (error) {
    yield alert("문제가 발생했습니다!");
    yield put(cartActionCreators.removeLocalCartRejected(error));
  }
}

function* changeLocalCartSagas(action: cartTypes.IChangeLocalCart) {
  try {
    const state = yield select();
    const { localCart } = yield state.cart;
    // 언더스코어로 새로운 객체를 생성한 뒤 프로퍼티들 변경.
    const newCart = yield _(localCart).clone();
    const targetMenus = yield newCart.menus.map((m: cartTypes.ICartMenu, index: number) => {
      if (index === action.id) {
        const changeQuantity = m.quantity - action.quantity;
        if (changeQuantity > 0) {
          // 수량이 줄었을 경우.
          newCart.size -= changeQuantity;
          newCart.totalPrice -= m.price * changeQuantity;
        } else {
          // 수량이 늘었을 경우.
          newCart.size += -changeQuantity;
          newCart.totalPrice += m.price * -changeQuantity;
        }
        m.quantity = action.quantity;
        return m;
      } else {
        return m;
      }
    });
    newCart.menus = targetMenus;
    // 완성된 객체를 로컬 스토리지와 상태에 저장.
    yield localStorage.setItem("LocalCart", JSON.stringify(newCart));
    yield put(cartActionCreators.changeLocalCartFulfilled(newCart));
  } catch (error) {
    yield alert("문제가 발생했습니다!");
    yield put(cartActionCreators.changeLocalCartRejected(error));
  }
}

function* fetchSetCartSagas(action: cartTypes.IFetchSetCart) {
  try {
    const jwtToken = localStorage.getItem("JWT");
    const response = yield axios.get("http://tmontica-idev.tmon.co.kr/api/carts", {
      headers: {
        Authorization: jwtToken
      }
    });
    yield put(cartActionCreators.fetchSetCartFulfilled(response.data));
  } catch (error) {
    yield put(cartActionCreators.fetchSetCartRejected(error.response));
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
    const jwtToken = localStorage.getItem("JWT");
    const response = yield axios.post("http://tmontica-idev.tmon.co.kr/api/carts", cartReqs, {
      headers: {
        Authorization: jwtToken
      }
    });
    // 현재 카트 상태에 새로운 카트 메뉴들을 추가한다.
    const state = yield select();
    const newCart = _(state.cart.cart).clone() as cartTypes.ICart;
    const newCartMenus = cartMenus.map((m: cartTypes.ICartMenu, index: number) => {
      newCart.size += m.quantity;
      newCart.totalPrice += m.quantity * m.price;
      const newCartMenu: cartTypes.ICartMenu = m;
      newCartMenu.cartId = response.data[index].cartId;
      return newCartMenu;
    });
    newCart.menus = newCart.menus.concat(newCartMenus);
    yield put(cartActionCreators.fetchAddCartFulfilled(newCart));
  } catch (error) {
    yield put(cartActionCreators.fetchAddCartRejected(error.response));
  }
}

function* fetchRemoveCartSagas(action: cartTypes.IFetchRemoveCart) {
  try {
    // 카트 아이디로 디비에 있는 해당 메뉴를 삭제한다.
    const jwtToken = localStorage.getItem("JWT");
    axios.delete(`http://tmontica-idev.tmon.co.kr/api/carts/${action.payload}`, {
      headers: {
        Authorization: jwtToken
      }
    });
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
    yield put(cartActionCreators.fetchRemoveCartRejected(error));
  }
}

function* fetchChangeCartSagas(action: cartTypes.IFetchChangeCart) {
  try {
    // 카트 아이디와 수량으로 디비에 있는 해당 메뉴를 삭제한다.
    const jwtToken = localStorage.getItem("JWT");
    yield axios.put(
      `http://tmontica-idev.tmon.co.kr/api/carts/${action.id}`,
      {
        quantity: action.quantity
      },
      {
        headers: {
          Authorization: jwtToken
        }
      }
    );
    // 해당 아이디에 해당하는 메뉴의 개수를 변경하고, 그에 따라 전체 사이즈와 가격도 변경한다.
    const state = yield select();
    const newCart = _(state.cart.cart).clone() as cartTypes.ICart;
    const targetMenus = newCart.menus.map((m: cartTypes.ICartMenu) => {
      if (m.cartId === action.id) {
        const changeQuantity = m.quantity - action.quantity;
        if (changeQuantity > 0) {
          // 수량이 줄었을 경우.
          newCart.size -= changeQuantity;
          newCart.totalPrice -= m.price * changeQuantity;
        } else {
          // 수량이 늘었을 경우.
          newCart.size += -changeQuantity;
          newCart.totalPrice += m.price * -changeQuantity;
        }
        m.quantity = action.quantity;
        return m;
      } else {
        return m;
      }
    });
    newCart.menus = targetMenus;
    yield put(cartActionCreators.fetchChangeCartFulfilled(newCart));
  } catch (error) {
    yield put(cartActionCreators.fetchChangeCartRejected(error.response));
  }
}

export default function* userSagas() {
  yield takeEvery(cartActionTypes.ADD_LOCAL_CART, addLocalCartSagas);
  yield takeEvery(cartActionTypes.REMOVE_LOCAL_CART, removeLocalCartSagas);
  yield takeEvery(cartActionTypes.CHANGE_LOCAL_CART, changeLocalCartSagas);
  yield takeEvery(cartActionTypes.FETCH_SET_CART, fetchSetCartSagas);
  yield takeEvery(cartActionTypes.FETCH_ADD_CART, fetchAddCartSagas);
  yield takeEvery(cartActionTypes.FETCH_REMOVE_CART, fetchRemoveCartSagas);
  yield takeEvery(cartActionTypes.FETCH_CHANGE_CART, fetchChangeCartSagas);
}
