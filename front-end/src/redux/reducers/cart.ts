import * as actionTypes from "../actionTypes/cart";
import * as cartTypes from "../../types/cart";

const localCart = localStorage.getItem("LocalCart");

const INITIAL_STATE = {
  cart: null,
  localCart: localCart ? JSON.parse(localCart) : null
} as cartTypes.ICartState;

export default function(state = INITIAL_STATE, action: cartTypes.TCartAction) {
  switch (action.type) {
    case actionTypes.INITIALIZE_LOCAL_CART:
      const initCart = {
        size: 0,
        totalPrice: 0,
        menus: []
      } as cartTypes.ICart;
      localStorage.setItem("LocalCart", JSON.stringify(initCart));
      return state;
    case actionTypes.ADD_LOCAL_CART:
      return state;
    case actionTypes.REMOVE_LOCAL_CART:
      return state;
    default:
      return state;
  }
}
