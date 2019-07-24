import { combineReducers } from "redux";
import userReducer from "./user";
import cartReducer from "./cart";

export default combineReducers({ user: userReducer, cart: cartReducer });
