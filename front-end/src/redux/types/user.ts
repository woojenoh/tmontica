import * as userActionTypes from "../actionTypes/user";
import { AxiosError } from "axios";

export interface IUserState {
  user: IUser | null;
  isSignin: boolean;
}

export interface IUser {
  id: number;
  name: string;
  email: string;
  birthDate: string;
  point: number;
}

export interface IUserSignupInfo {
  id: string; // 유저의 아이디
  password: string;
  passwordCheck: string;
  name: string;
  email: string;
  birthDate: string;
}

export interface IFetchSignup {
  type: typeof userActionTypes.FETCH_SIGNUP;
  payload: IUserSignupInfo;
}

export interface IFetchSignupFulfilled {
  type: typeof userActionTypes.FETCH_SIGNUP_FULFILLED;
}

export interface IFetchSignupRejected {
  type: typeof userActionTypes.FETCH_SIGNUP_REJECTED;
  error: AxiosError;
}

export type TUserAction = IFetchSignup | IFetchSignupFulfilled | IFetchSignupRejected;
