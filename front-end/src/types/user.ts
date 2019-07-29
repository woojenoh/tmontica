import * as userActionTypes from "../redux/actionTypes/user";
import { AxiosError } from "axios";

export interface IUserState {
  user: IUser | null;
  isSignin: boolean;
  isAdmin: boolean;
}

export interface IUser {
  id: number;
  name: string;
  email: string;
  birthDate: string;
  point: number;
}

export interface IUserSignupInfo {
  id: string;
  password: string;
  passwordCheck: string;
  name: string;
  email: string;
  birthDate: string;
}

export interface IUserSigninInfo {
  id: string;
  password: string;
  role?: "USER" | "ADMIN";
}

export interface IJwtToken {
  sub: string;
  exp: number;
  userInfo: string;
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

export interface IFetchSignin {
  type: typeof userActionTypes.FETCH_SIGNIN;
  payload: IUserSigninInfo;
}

export interface IFetchSigninFulfilled {
  type: typeof userActionTypes.FETCH_SIGNIN_FULFILLED;
}

export interface IFetchSigninRejected {
  type: typeof userActionTypes.FETCH_SIGNIN_REJECTED;
  error: AxiosError;
}

export interface ISignout {
  type: typeof userActionTypes.SIGNOUT;
}

export interface ISetUser {
  type: typeof userActionTypes.SET_USER;
  payload: IUser;
}

export type TUserAction =
  | IFetchSignup
  | IFetchSignupFulfilled
  | IFetchSignupRejected
  | IFetchSignin
  | IFetchSigninFulfilled
  | IFetchSigninRejected
  | ISignout
  | ISetUser;
