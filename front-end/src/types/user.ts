import * as userActionTypes from "../redux/actionTypes/user";

export interface ISignoutFunction {
  signout(): void;
}

export interface IUserSigninActive {
  id: string;
  token: string;
}

export interface IUserState {
  user: IUser | null;
  isSignin: boolean;
  isAdmin: boolean;
  isSignupLoading: boolean;
  isFindIdLoading: boolean;
  isFindPasswordLoading: boolean;
}

export interface IUser {
  id: number;
  name: string;
  email: string;
  birthDate: string;
  point: number;
  role: string;
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

export interface ISignout {
  type: typeof userActionTypes.SIGNOUT;
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
  error: string;
}

export interface IFetchSignin {
  type: typeof userActionTypes.FETCH_SIGNIN;
  payload: IUserSigninInfo;
}

export interface IFetchSigninFulfilled {
  type: typeof userActionTypes.FETCH_SIGNIN_FULFILLED;
  payload: {
    user: IUser;
    isAdmin: boolean;
  };
}

export interface IFetchSigninRejected {
  type: typeof userActionTypes.FETCH_SIGNIN_REJECTED;
  error: string;
}

export interface IFetchSigninActive {
  type: typeof userActionTypes.FETCH_SIGNIN_ACTIVE;
  payload: {
    id: string;
    token: string;
  };
}

export interface IFetchSigninActiveFulfilled {
  type: typeof userActionTypes.FETCH_SIGNIN_ACTIVE_FULFILLED;
}

export interface IFetchSigninActiveRejected {
  type: typeof userActionTypes.FETCH_SIGNIN_ACTIVE_REJECTED;
  error: string;
}

export interface IFetchFindId {
  type: typeof userActionTypes.FETCH_FIND_ID;
  payload: string;
}

export interface IFetchFindIdFulfilled {
  type: typeof userActionTypes.FETCH_FIND_ID_FULFILLED;
}

export interface IFetchFindIdRejected {
  type: typeof userActionTypes.FETCH_FIND_ID_REJECTED;
  error: string;
}

export interface IFetchFindIdConfirm {
  type: typeof userActionTypes.FETCH_FIND_ID_CONFIRM;
  payload: string;
}

export interface IFetchFindIdConfirmFulfilled {
  type: typeof userActionTypes.FETCH_FIND_ID_CONFIRM_FULFILLED;
}

export interface IFetchFindIdConfirmRejected {
  type: typeof userActionTypes.FETCH_FIND_ID_CONFIRM_REJECTED;
  error: string;
}

export interface IFetchFindPassword {
  type: typeof userActionTypes.FETCH_FIND_PASSWORD;
  payload: {
    id: string;
    email: string;
  };
}

export interface IFetchFindPasswordFulfilled {
  type: typeof userActionTypes.FETCH_FIND_PASSWORD_FULFILLED;
}

export interface IFetchFindPasswordRejected {
  type: typeof userActionTypes.FETCH_FIND_PASSWORD_REJECTED;
  error: string;
}

export interface IFetchSetPoint {
  type: typeof userActionTypes.FETCH_SET_POINT;
}

export interface IFetchSetPointFulfilled {
  type: typeof userActionTypes.FETCH_SET_POINT_FULFILLED;
  payload: {
    point: number;
  };
}

export interface IFetchSetPointRejected {
  type: typeof userActionTypes.FETCH_SET_POINT_REJECTED;
  error: string;
}

export type TUserAction =
  | ISignout
  | IFetchSignup
  | IFetchSignupFulfilled
  | IFetchSignupRejected
  | IFetchSignin
  | IFetchSigninFulfilled
  | IFetchSigninRejected
  | IFetchFindId
  | IFetchFindIdFulfilled
  | IFetchFindIdRejected
  | IFetchFindPassword
  | IFetchFindPasswordFulfilled
  | IFetchFindPasswordRejected
  | IFetchFindIdConfirm
  | IFetchFindIdConfirmFulfilled
  | IFetchFindIdConfirmRejected
  | IFetchSigninActive
  | IFetchSigninActiveFulfilled
  | IFetchSigninActiveRejected
  | IFetchSetPoint
  | IFetchSetPointFulfilled
  | IFetchSetPointRejected;
