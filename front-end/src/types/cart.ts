import { AxiosError } from "axios";
import * as cartActionTypes from "../redux/actionTypes/cart";

export interface ICart {
  size: number;
  totalPrice: number;
  menus: ICartMenu[];
}

export interface ICartMenu {
  menuId: number;
  nameEng: string;
  nameKo: string;
  imgUrl: string;
  option: string;
  quantity: number;
  price: number;
  optionArray?: ICartMenuOptionArrayItem[];
  cartId?: number;
  stock?: number;
  direct?: boolean;
}

export interface ICartMenuOptionArrayItem {
  id: number;
  quantity: number;
}

export interface ICartState {
  cart: ICart | null;
  localCart: ICart | null;
}

export interface IInitializeLocalCart {
  type: typeof cartActionTypes.INITIALIZE_LOCAL_CART;
}

export interface IAddLocalCart {
  type: typeof cartActionTypes.ADD_LOCAL_CART;
  payload: ICartMenu;
}

export interface IAddLocalCartFulfilled {
  type: typeof cartActionTypes.ADD_LOCAL_CART_FULFILLED;
  payload: ICart;
}

export interface IAddLocalCartRejected {
  type: typeof cartActionTypes.ADD_LOCAL_CART_REJECTED;
  error: Error;
}

export interface IRemoveLocalCart {
  type: typeof cartActionTypes.REMOVE_LOCAL_CART;
  payload: number;
}

export interface IRemoveLocalCartFulfilled {
  type: typeof cartActionTypes.REMOVE_LOCAL_CART_FULFILLED;
  payload: ICart;
}

export interface IRemoveLocalCartRejected {
  type: typeof cartActionTypes.REMOVE_LOCAL_CART_REJECTED;
  error: Error;
}

export interface IChangeLocalCart {
  type: typeof cartActionTypes.CHANGE_LOCAL_CART;
  id: number;
  quantity: number;
}

export interface IChangeLocalCartFulfilled {
  type: typeof cartActionTypes.CHANGE_LOCAL_CART_FULFILLED;
  payload: ICart;
}

export interface IChangeLocalCartRejected {
  type: typeof cartActionTypes.CHANGE_LOCAL_CART_REJECTED;
  error: Error;
}

export interface IFetchSetCart {
  type: typeof cartActionTypes.FETCH_SET_CART;
}

export interface IFetchSetCartFulfilled {
  type: typeof cartActionTypes.FETCH_SET_CART_FULFILLED;
  payload: ICart;
}

export interface IFetchSetCartRejected {
  type: typeof cartActionTypes.FETCH_SET_CART_REJECTED;
  error: AxiosError;
}

export interface IFetchAddCart {
  type: typeof cartActionTypes.FETCH_ADD_CART;
  payload: ICartMenu;
}

export interface IFetchAddCartFulfilled {
  type: typeof cartActionTypes.FETCH_ADD_CART_FULFILLED;
  payload: ICart;
}

export interface IFetchAddCartRejected {
  type: typeof cartActionTypes.FETCH_ADD_CART_REJECTED;
  error: AxiosError;
}

export type TCartAction =
  | IInitializeLocalCart
  | IAddLocalCart
  | IAddLocalCartFulfilled
  | IAddLocalCartRejected
  | IRemoveLocalCart
  | IRemoveLocalCartFulfilled
  | IRemoveLocalCartRejected
  | IChangeLocalCart
  | IChangeLocalCartFulfilled
  | IChangeLocalCartRejected
  | IFetchSetCart
  | IFetchSetCartFulfilled
  | IFetchSetCartRejected
  | IFetchAddCart
  | IFetchAddCartFulfilled
  | IFetchAddCartRejected;
