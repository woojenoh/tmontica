import * as actionTypes from "../actionTypes/user";
import * as userTypes from "../../types/user";
import { AxiosError } from "axios";

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

export function fetchSignupRejected(error: AxiosError) {
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

export function fetchSigninFulfilled() {
  return {
    type: actionTypes.FETCH_SIGNIN_FULFILLED
  };
}

export function fetchSigninRejected(error: AxiosError) {
  return {
    type: actionTypes.FETCH_SIGNIN_REJECTED,
    error
  };
}

export function signout() {
  return {
    type: actionTypes.SIGNOUT
  };
}

export function setUser(payload: userTypes.IUser) {
  return {
    type: actionTypes.SET_USER,
    payload
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

export function fetchFindIdRejected(error: AxiosError) {
  return {
    type: actionTypes.FETCH_FIND_ID_REJECTED,
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

export function fetchFindPasswordRejected(error: AxiosError) {
  return {
    type: actionTypes.FETCH_FIND_PASSWORD_REJECTED,
    error
  };
}
