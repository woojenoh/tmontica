import * as actionTypes from "../actionTypes/cart";
import * as cartTypes from "../../types/cart";

export function initializeLocalCart() {
  return {
    type: actionTypes.INITIALIZE_LOCAL_CART
  };
}

export function addLocalCart(payload: cartTypes.ICartMenu) {
  return {
    type: actionTypes.ADD_LOCAL_CART,
    payload
  };
}

export function addLocalCartFulfilled(payload: cartTypes.ICart) {
  return {
    type: actionTypes.ADD_LOCAL_CART_FULFILLED,
    payload
  };
}

export function addLocalCartRejected(error: Error) {
  return {
    type: actionTypes.ADD_LOCAL_CART_REJECTED,
    error
  };
}

export function removeLocalCart(payload: number) {
  return {
    type: actionTypes.REMOVE_LOCAL_CART,
    payload
  };
}

export function removeLocalCartFulfilled(payload: cartTypes.ICart) {
  return {
    type: actionTypes.REMOVE_LOCAL_CART_FULFILLED,
    payload
  };
}

export function removeLocalCartRejected(error: Error) {
  return {
    type: actionTypes.REMOVE_LOCAL_CART_REJECTED,
    error
  };
}
