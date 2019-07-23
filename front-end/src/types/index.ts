import * as userTypes from "./user";
import * as cartTypes from "./cart";

export interface IRootState {
  user: userTypes.IUserState;
  cart: cartTypes.ICartState;
}
