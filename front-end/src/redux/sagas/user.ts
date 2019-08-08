import { call, put, takeLatest, takeEvery } from "redux-saga/effects";
import { signUp, signIn, findPassword, findIdConfirm, findId, signInActive } from "../../api/user";
import history from "../../history";
import jwt from "jwt-decode";
import * as userActionTypes from "../actionTypes/user";
import * as userActionCreators from "../actionCreators/user";
import * as userTypes from "../../types/user";
import * as cartActionCreators from "../actionCreators/cart";
import { CommonError } from "../../api/CommonError";
import { handleError } from "../../api/common";

export function* signout() {
  localStorage.removeItem("jwt");
  history.push("/signin");
  yield put(userActionCreators.signoutFulfilled());
}

function* fetchSignupSagas(action: userTypes.IFetchSignup) {
  try {
    const data = yield call(signUp, action.payload);
    if (data instanceof CommonError) {
      throw data;
    }

    yield put(userActionCreators.fetchSignupFulfilled());
    alert("가입 인증 메일이 발송되었습니다.");
    history.push("/signin");
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
      yield call(signout);
    }
    yield put(userActionCreators.fetchSignupRejected(result));
  }
}

function* fetchSigninSagas(action: userTypes.IFetchSignin) {
  try {
    const data = yield call(signIn, action.payload);
    if (data instanceof CommonError) {
      throw data;
    }
    const { authorization } = data;

    // 토큰에서 유저 정보를 가져와 상태에 저장한다.
    const jwtToken = jwt(authorization) as userTypes.IJwtToken;
    const parsedUserInfo = JSON.parse(jwtToken.userInfo);
    // jwt를 로컬 스토리지에 저장한다.
    localStorage.setItem("jwt", authorization);
    alert("환영합니다!");
    yield put(
      userActionCreators.fetchSigninFulfilled({
        user: parsedUserInfo,
        isAdmin: parsedUserInfo.role === "ADMIN"
      })
    );
    // 로그인 후 유저의 장바구니를 가져온다. 순서를 보장하기 위해 로그인 사가에.
    yield put(cartActionCreators.fetchSetCart());
    if (/admin/.test(window.location.href)) {
      history.push("/admin");
    } else {
      history.push("/");
    }
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
      yield call(signout);
    }
    yield put(userActionCreators.fetchSigninRejected(result));
  }
}

function* fetchSigninActiveSagas(action: userTypes.IFetchSigninActive) {
  try {
    const data = yield call(signInActive, {
      id: action.payload.id,
      token: action.payload.token
    });
    if (data instanceof CommonError) {
      throw data;
    }

    alert("인증이 완료되었습니다. 이제 로그인이 가능합니다.");
    yield put(userActionCreators.fetchSigninActiveFulfilled());
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
      yield call(signout);
    }
    yield put(userActionCreators.fetchSigninActiveRejected(result));
  }
}

function* fetchFindIdSagas(action: userTypes.IFetchFindId) {
  try {
    const data = yield call(findId, {
      email: action.payload
    });
    if (data instanceof CommonError) {
      throw data;
    }

    alert("입력하신 이메일로 인증코드가 전송되었습니다.");
    yield put(userActionCreators.fetchFindIdFulfilled());
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
      yield call(signout);
    }
    yield put(userActionCreators.fetchFindIdRejected(result));
  }
}

function* fetchFindIdConfirmSagas(action: userTypes.IFetchFindIdConfirm) {
  try {
    const data = yield call(findIdConfirm, {
      authCode: action.payload
    });
    if (data instanceof CommonError) {
      throw data;
    }

    alert(`회원님의 아이디는 ${data.userIdList} 입니다.`);
    yield put(userActionCreators.fetchFindIdConfirmFulfilled());
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
      yield call(signout);
    }
    yield put(userActionCreators.fetchFindIdConfirmRejected(result));
  }
}

function* fetchFindPasswordSagas(action: userTypes.IFetchFindPassword) {
  try {
    const data = yield call(findPassword, {
      email: action.payload.email,
      id: action.payload.id
    });
    if (data instanceof CommonError) {
      throw data;
    }

    alert("입력하신 이메일로 임시 비밀번호가 전송되었습니다.");
    yield put(userActionCreators.fetchFindPasswordFulfilled());
  } catch (error) {
    const result = yield handleError(error);
    if (result === "signout") {
      yield put(userActionCreators.signout());
      yield call(signout);
    }
    yield put(userActionCreators.fetchFindPasswordRejected(result));
  }
}

export default function* userSagas() {
  yield takeLatest(userActionTypes.FETCH_SIGNUP, fetchSignupSagas);
  yield takeLatest(userActionTypes.FETCH_SIGNIN, fetchSigninSagas);
  yield takeLatest(userActionTypes.FETCH_SIGNIN_ACTIVE, fetchSigninActiveSagas);
  yield takeLatest(userActionTypes.FETCH_FIND_ID, fetchFindIdSagas);
  yield takeLatest(userActionTypes.FETCH_FIND_ID_CONFIRM, fetchFindIdConfirmSagas);
  yield takeLatest(userActionTypes.FETCH_FIND_PASSWORD, fetchFindPasswordSagas);
  yield takeEvery(userActionTypes.SIGNOUT, signout);
}
