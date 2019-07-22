import { put, takeEvery } from "redux-saga/effects";
import axios from "axios";
import history from "../../history";
import * as userActionTypes from "../actionTypes/user";
import * as userActionCreators from "../actionCreators/user";
import * as userTypes from "../types/user";

function* fetchSignupSagas(action: userTypes.IFetchSignup) {
  try {
    yield axios.post("http://localhost:8080/api/users/signup", action.payload);
    yield alert("가입이 완료되었습니다.");
    yield put(userActionCreators.fetchSignupFulfilled());
    yield history.push("/signin");
  } catch (error) {
    yield alert("아이디와 비밀번호를 다시 확인해주세요.");
    yield put(userActionCreators.fetchSignupRejected(error.response));
  }
}

export default function* userSagas() {
  yield takeEvery(userActionTypes.FETCH_SIGNUP, fetchSignupSagas);
}
