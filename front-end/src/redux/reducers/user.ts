import history from "../../history";
import jwt from "jwt-decode";
import * as actionTypes from "../actionTypes/user";
import * as userTypes from "../../types/user";

// 토큰이 있을 경우 토큰에서 유저 정보를 가져온다.
const localJwt = localStorage.getItem("jwt");
let jwtToken, parsedUserInfo;
if (localJwt) {
  jwtToken = jwt(localJwt) as userTypes.IJwtToken;
  parsedUserInfo = JSON.parse(jwtToken.userInfo);
}

const INITIAL_STATE = {
  isSignin: localJwt ? true : false,
  isAdmin: parsedUserInfo ? parsedUserInfo.role === "ADMIN" : false,
  user: parsedUserInfo || null
} as userTypes.IUserState;

export default function(state = INITIAL_STATE, action: userTypes.TUserAction) {
  switch (action.type) {
    // FETCH_SIGNUP
    case actionTypes.FETCH_SIGNUP:
      return state;
    case actionTypes.FETCH_SIGNUP_FULFILLED:
      return state;
    case actionTypes.FETCH_SIGNUP_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_SIGNIN
    case actionTypes.FETCH_SIGNIN:
      return state;
    case actionTypes.FETCH_SIGNIN_FULFILLED:
      return {
        ...state,
        isSignin: true
      };
    case actionTypes.FETCH_SIGNIN_REJECTED:
      return {
        ...state,
        error: action.error
      };
    case actionTypes.SIGNOUT:
      localStorage.removeItem("jwt");
      history.push("/");
      return {
        ...state,
        user: null,
        isSignin: false
      };
    case actionTypes.SET_USER:
      return {
        ...state,
        user: action.payload
      };
    default:
      return state;
  }
}
