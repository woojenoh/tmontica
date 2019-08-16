import * as actionTypes from "../actionTypes/user";
import * as userTypes from "../../types/user";

export function signout() {
  return {
    type: actionTypes.SIGNOUT
  };
}

export function fetchSignup(payload: userTypes.IUserSignupInfo) {
  return {
    type: actionTypes.FETCH_SIGNUP,
    payload
  };
}

export function fetchSignupFulfilled() {
  return {
    type: actionTypes.FETCH_SIGNUP_FULFILLED
  };
}

export function fetchSignupRejected(error: string) {
  return {
    type: actionTypes.FETCH_SIGNUP_REJECTED,
    error
  };
}

export function fetchSignin(payload: userTypes.IUserSigninInfo) {
  return {
    type: actionTypes.FETCH_SIGNIN,
    payload
  };
}

export function fetchSigninFulfilled(payload: { user: userTypes.IUser; isAdmin: boolean }) {
  return {
    type: actionTypes.FETCH_SIGNIN_FULFILLED,
    payload
  };
}

export function fetchSigninRejected(error: string) {
  return {
    type: actionTypes.FETCH_SIGNIN_REJECTED,
    error
  };
}

export function fetchSigninActive(payload: { id: string; token: string }) {
  return {
    type: actionTypes.FETCH_SIGNIN_ACTIVE,
    payload
  };
}

export function fetchSigninActiveFulfilled() {
  return {
    type: actionTypes.FETCH_SIGNIN_ACTIVE_FULFILLED
  };
}

export function fetchSigninActiveRejected(error: string) {
  return {
    type: actionTypes.FETCH_SIGNIN_ACTIVE_REJECTED,
    error
  };
}

export function fetchFindId(payload: string) {
  return {
    type: actionTypes.FETCH_FIND_ID,
    payload
  };
}

export function fetchFindIdFulfilled() {
  return {
    type: actionTypes.FETCH_FIND_ID_FULFILLED
  };
}

export function fetchFindIdRejected(error: string) {
  return {
    type: actionTypes.FETCH_FIND_ID_REJECTED,
    error
  };
}

export function fetchFindIdConfirm(payload: string) {
  return {
    type: actionTypes.FETCH_FIND_ID_CONFIRM,
    payload
  };
}

export function fetchFindIdConfirmFulfilled() {
  return {
    type: actionTypes.FETCH_FIND_ID_CONFIRM_FULFILLED
  };
}

export function fetchFindIdConfirmRejected(error: string) {
  return {
    type: actionTypes.FETCH_FIND_ID_CONFIRM_REJECTED,
    error
  };
}

export function fetchFindPassword(payload: { id: string; email: string }) {
  return {
    type: actionTypes.FETCH_FIND_PASSWORD,
    payload
  };
}

export function fetchFindPasswordFulfilled() {
  return {
    type: actionTypes.FETCH_FIND_PASSWORD_FULFILLED
  };
}

export function fetchFindPasswordRejected(error: string) {
  return {
    type: actionTypes.FETCH_FIND_PASSWORD_REJECTED,
    error
  };
}

export function fetchSetPoint() {
  return {
    type: actionTypes.FETCH_SET_POINT
  };
}

export function fetchSetPointFulfilled(payload: { point: number }) {
  return {
    type: actionTypes.FETCH_SET_POINT_FULFILLED,
    payload
  };
}

export function fetchSetPointRejected(error: string) {
  return {
    type: actionTypes.FETCH_SET_POINT_REJECTED,
    error
  };
}
