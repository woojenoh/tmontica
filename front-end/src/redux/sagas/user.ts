import { put, takeEvery } from "redux-saga/effects";
import axios from "axios";
import history from "../../history";
import jwt from "jwt-decode";
import * as userActionTypes from "../actionTypes/user";
import * as userActionCreators from "../actionCreators/user";
import * as userTypes from "../../types/user";
import * as cartActionCreators from "../actionCreators/cart";
import { API_URL } from "../../API";

function* fetchSignupSagas(action: userTypes.IFetchSignup) {
  try {
    yield axios.post(`${API_URL}/users/signup`, action.payload);
    yield put(userActionCreators.fetchSignupFulfilled());
    yield alert("가입이 완료되었습니다.");
    yield history.push("/signin");
  } catch (error) {
    yield put(userActionCreators.fetchSignupRejected(error.response));
    yield alert("아이디와 비밀번호를 다시 확인해주세요.");
  }
}

function* fetchSigninSagas(action: userTypes.IFetchSignin) {
  try {
    const response = yield axios.post(`${API_URL}/users/signin`, action.payload);
    // 토큰에서 유저 정보를 가져와 상태에 저장한다.
    const jwtToken = jwt(response.data.authorization) as userTypes.IJwtToken;
    const parsedUserInfo = JSON.parse(jwtToken.userInfo);
    yield put(userActionCreators.setUser(parsedUserInfo));
    // JWT를 로컬 스토리지에 저장한다.
    yield localStorage.setItem("JWT", response.data.authorization);
    yield alert("환영합니다!");
    yield put(userActionCreators.fetchSigninFulfilled());
    // 로그인 후 유저의 장바구니를 가져온다. 순서를 보장하기 위해 로그인 사가에.
    yield put(cartActionCreators.fetchSetCart());
    yield history.push("/");
  } catch (error) {
    yield put(userActionCreators.fetchSigninRejected(error.response));
    yield alert("아이디와 비밀번호를 다시 확인해주세요.");
  }
}

export default function* userSagas() {
  yield takeEvery(userActionTypes.FETCH_SIGNUP, fetchSignupSagas);
  yield takeEvery(userActionTypes.FETCH_SIGNIN, fetchSigninSagas);
}
