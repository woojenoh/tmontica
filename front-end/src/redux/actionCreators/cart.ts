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

export function changeLocalCart(payload: { id: number; quantity: number }) {
  return {
    type: actionTypes.CHANGE_LOCAL_CART,
    payload
  };
}

export function changeLocalCartFulfilled(payload: cartTypes.ICart) {
  return {
    type: actionTypes.CHANGE_LOCAL_CART_FULFILLED,
    payload
  };
}

export function changeLocalCartRejected(error: Error) {
  return {
    type: actionTypes.CHANGE_LOCAL_CART_REJECTED,
    error
  };
}

export function fetchSetCart() {
  return {
    type: actionTypes.FETCH_SET_CART
  };
}

export function fetchSetCartFulfilled(payload: cartTypes.ICart) {
  return {
    type: actionTypes.FETCH_SET_CART_FULFILLED,
    payload
  };
}

export function fetchSetCartRejected(error: string) {
  return {
    type: actionTypes.FETCH_SET_CART_REJECTED,
    error
  };
}

export function fetchAddCart(payload: cartTypes.ICartMenu[]) {
  return {
    type: actionTypes.FETCH_ADD_CART,
    payload
  };
}

export function fetchAddCartFulfilled(payload: cartTypes.ICart) {
  return {
    type: actionTypes.FETCH_ADD_CART_FULFILLED,
    payload
  };
}

export function fetchAddCartRejected(error: string) {
  return {
    type: actionTypes.FETCH_ADD_CART_REJECTED,
    error
  };
}

export function fetchRemoveCart(payload: number) {
  return {
    type: actionTypes.FETCH_REMOVE_CART,
    payload
  };
}

export function fetchRemoveCartFulfilled(payload: cartTypes.ICart) {
  return {
    type: actionTypes.FETCH_REMOVE_CART_FULFILLED,
    payload
  };
}

export function fetchRemoveCartRejected(error: string) {
  return {
    type: actionTypes.FETCH_REMOVE_CART_REJECTED,
    error
  };
}

export function fetchChangeCart(payload: { id: number; quantity: number }) {
  return {
    type: actionTypes.FETCH_CHANGE_CART,
    payload
  };
}

export function fetchChangeCartFulfilled(payload: cartTypes.ICart) {
  return {
    type: actionTypes.FETCH_CHANGE_CART_FULFILLED,
    payload
  };
}

export function fetchChangeCartRejected(error: string) {
  return {
    type: actionTypes.FETCH_CHANGE_CART_REJECTED,
    error
  };
}

export function setOrderCart(payload: cartTypes.ICartMenu[]) {
  return {
    type: actionTypes.SET_ORDER_CART,
    payload
  };
}
