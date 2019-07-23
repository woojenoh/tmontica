import * as actionTypes from "../actionTypes/cart";
import * as cartTypes from "../../types/cart";

export function initializeLocalCart() {
  return {
    type: actionTypes.INITIALIZE_LOCAL_CART
  };
}

export function addLocalCart(payload: cartTypes.ICart) {
  return {
    type: actionTypes.ADD_LOCAL_CART,
    payload
  };
}

export function removeLocalCart(payload: number) {
  return {
    type: actionTypes.REMOVE_LOCAL_CART,
    payload
  };
}
