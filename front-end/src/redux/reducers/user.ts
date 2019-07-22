import * as actionTypes from "../actionTypes/user";
import * as userTypes from "../types/user";

const INITIAL_STATE = {
  user: null,
  isSignin: false
} as userTypes.IUserState;

export default function(state = INITIAL_STATE, action: userTypes.TUserAction) {
  switch (action.type) {
    case actionTypes.FETCH_SIGNUP:
      return state;
    case actionTypes.FETCH_SIGNUP_FULFILLED:
      return state;
    case actionTypes.FETCH_SIGNUP_REJECTED:
      return {
        ...state,
        error: action.error
      };
    default:
      return state;
  }
}
