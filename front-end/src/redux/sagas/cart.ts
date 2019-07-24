import { put, takeEvery, select } from "redux-saga/effects";
import _ from "underscore";
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
    // 유저 장바구니 불러오는 API 필요.
    // 이건 임시 데이터.
    yield put(
      cartActionCreators.fetchSetCartFulfilled({
        size: 1,
        totalPrice: 1500,
        menus: [
          {
            cartId: 10,
            menuId: 2,
            nameEng: "americano",
            nameKo: "아메리카노",
            imgUrl: "/img/coffee-sample.png",
            option: "HOT/샷추가(1개)/사이즈업",
            quantity: 1,
            price: 1500,
            stock: 100
          }
        ]
      })
    );
  } catch (error) {
    yield put(cartActionCreators.fetchSetCartRejected(error.response));
  }
}

export default function* userSagas() {
  yield takeEvery(cartActionTypes.ADD_LOCAL_CART, addLocalCartSagas);
  yield takeEvery(cartActionTypes.REMOVE_LOCAL_CART, removeLocalCartSagas);
  yield takeEvery(cartActionTypes.CHANGE_LOCAL_CART, changeLocalCartSagas);
  yield takeEvery(cartActionTypes.FETCH_SET_CART, fetchSetCartSagas);
}
