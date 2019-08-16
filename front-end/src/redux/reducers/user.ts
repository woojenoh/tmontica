import jwt from "jwt-decode";
import _ from "underscore";
import * as actionTypes from "../actionTypes/user";
import * as userTypes from "../../types/user";

// 토큰이 있을 경우 토큰에서 유저 정보를 가져온다.
const localJwt = localStorage.getItem("jwt");
let jwtToken, parsedUserInfo;
if (localJwt) {
  jwtToken = jwt(localJwt) as userTypes.IJwtToken;
  parsedUserInfo = JSON.parse(jwtToken.userInfo) as userTypes.IUser;
}

const INITIAL_STATE = {
  isSignin: localJwt ? true : false,
  isAdmin: parsedUserInfo ? parsedUserInfo.role === "ADMIN" : false,
  user: parsedUserInfo || null,
  isSignupLoading: false,
  isFindIdLoading: false,
  isFindPasswordLoading: false
} as userTypes.IUserState;

export default function(state = INITIAL_STATE, action: userTypes.TUserAction) {
  switch (action.type) {
    // SIGNOUT
    case actionTypes.SIGNOUT:
      return {
        ...state,
        isSignin: false,
        isAdmin: false,
        user: null
      };
    // FETCH_SIGNUP
    case actionTypes.FETCH_SIGNUP:
      return {
        ...state,
        isSignupLoading: true
      };
    case actionTypes.FETCH_SIGNUP_FULFILLED:
      return {
        ...state,
        isSignupLoading: false
      };
    case actionTypes.FETCH_SIGNUP_REJECTED:
      return {
        ...state,
        error: action.error,
        isSignupLoading: false
      };
    // FETCH_SIGNIN
    case actionTypes.FETCH_SIGNIN:
      return state;
    case actionTypes.FETCH_SIGNIN_FULFILLED:
      return {
        ...state,
        user: action.payload.user,
        isSignin: true,
        isAdmin: action.payload.isAdmin
      };
    case actionTypes.FETCH_SIGNIN_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_SIGNIN_ACTIVE
    case actionTypes.FETCH_SIGNIN_ACTIVE:
      return state;
    case actionTypes.FETCH_SIGNIN_ACTIVE_FULFILLED:
      return state;
    case actionTypes.FETCH_SIGNIN_ACTIVE_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_FIND_ID
    case actionTypes.FETCH_FIND_ID:
      return {
        ...state,
        isFindIdLoading: true
      };
    case actionTypes.FETCH_FIND_ID_FULFILLED:
      return {
        ...state,
        isFindIdLoading: false
      };
    case actionTypes.FETCH_FIND_ID_REJECTED:
      return {
        ...state,
        error: action.error,
        isFindIdLoading: false
      };
    // FETCH_FIND_ID_CONFIRM
    case actionTypes.FETCH_FIND_ID_CONFIRM:
      return state;
    case actionTypes.FETCH_FIND_ID_CONFIRM_FULFILLED:
      return state;
    case actionTypes.FETCH_FIND_ID_CONFIRM_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_FIND_PASSWORD
    case actionTypes.FETCH_FIND_PASSWORD:
      return {
        ...state,
        isFindPasswordLoading: true
      };
    case actionTypes.FETCH_FIND_PASSWORD_FULFILLED:
      return {
        ...state,
        isFindPasswordLoading: false
      };
    case actionTypes.FETCH_FIND_PASSWORD_REJECTED:
      return {
        ...state,
        error: action.error,
        isFindPasswordLoading: false
      };
    // FETCH_SET_POINT
    case actionTypes.FETCH_SET_POINT:
      return state;
    case actionTypes.FETCH_SET_POINT_FULFILLED:
      const newUser = _(state.user).clone();
      if (newUser) {
        newUser.point = action.payload.point;
      }
      return {
        ...state,
        user: newUser
      };
    case actionTypes.FETCH_SET_POINT_REJECTED:
      return {
        ...state,
        error: action.error
      };
    default:
      return state;
  }
}
