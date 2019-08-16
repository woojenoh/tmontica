import * as actionTypes from "../actionTypes/cart";
import * as cartTypes from "../../types/cart";

const localCart = localStorage.getItem("localCart");

const INITIAL_STATE = {
  cart: null,
  localCart: localCart ? JSON.parse(localCart) : null
} as cartTypes.ICartState;

export default function(state = INITIAL_STATE, action: cartTypes.TCartAction) {
  switch (action.type) {
    case actionTypes.INITIALIZE_LOCAL_CART: {
      const initCart = {
        size: 0,
        totalPrice: 0,
        menus: []
      } as cartTypes.ICart;
      return {
        ...state,
        localCart: initCart
      };
    }
    case actionTypes.INITIALIZE_CART: {
      const initCart = {
        size: 0,
        totalPrice: 0,
        menus: []
      } as cartTypes.ICart;
      return {
        ...state,
        cart: initCart
      };
    }
    // ADD_LOCAL_CART
    case actionTypes.ADD_LOCAL_CART:
      return state;
    case actionTypes.ADD_LOCAL_CART_FULFILLED:
      return {
        ...state,
        localCart: action.payload
      };
    case actionTypes.ADD_LOCAL_CART_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // REMOVE_LOCAL_CART
    case actionTypes.REMOVE_LOCAL_CART:
      return state;
    case actionTypes.REMOVE_LOCAL_CART_FULFILLED:
      return {
        ...state,
        localCart: action.payload
      };
    case actionTypes.REMOVE_LOCAL_CART_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // CHANGE_LOCAL_CART
    case actionTypes.CHANGE_LOCAL_CART:
      return state;
    case actionTypes.CHANGE_LOCAL_CART_FULFILLED:
      return {
        ...state,
        localCart: action.payload
      };
    case actionTypes.CHANGE_LOCAL_CART_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_SET_CART
    case actionTypes.FETCH_SET_CART:
      return state;
    case actionTypes.FETCH_SET_CART_FULFILLED:
      return {
        ...state,
        cart: action.payload
      };
    case actionTypes.FETCH_SET_CART_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_ADD_CART
    case actionTypes.FETCH_ADD_CART:
      return state;
    case actionTypes.FETCH_ADD_CART_FULFILLED:
      return {
        ...state,
        cart: action.payload
      };
    case actionTypes.FETCH_ADD_CART_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_REMOVE_CART
    case actionTypes.FETCH_REMOVE_CART:
      return state;
    case actionTypes.FETCH_REMOVE_CART_FULFILLED:
      return {
        ...state,
        cart: action.payload
      };
    case actionTypes.FETCH_REMOVE_CART_REJECTED:
      return {
        ...state,
        error: action.error
      };
    // FETCH_CHANGE_CART
    case actionTypes.FETCH_CHANGE_CART:
      return state;
    case actionTypes.FETCH_CHANGE_CART_FULFILLED:
      return {
        ...state,
        cart: action.payload
      };
    case actionTypes.FETCH_CHANGE_CART_REJECTED:
      return {
        ...state,
        error: action.error
      };
    case actionTypes.SET_ORDER_CART:
      return state;
    default:
      return state;
  }
}
