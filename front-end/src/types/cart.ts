import * as cartActionTypes from "../redux/actionTypes/cart";
import cart from "../redux/reducers/cart";

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

export type TCartAction =
  | IInitializeLocalCart
  | IAddLocalCart
  | IAddLocalCartFulfilled
  | IAddLocalCartRejected
  | IRemoveLocalCart
  | IRemoveLocalCartFulfilled
  | IRemoveLocalCartRejected;
