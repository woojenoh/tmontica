import * as actionTypes from "../actionTypes/user";
import * as userTypes from "../types/user";
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
