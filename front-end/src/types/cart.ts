import * as cartActionTypes from "../redux/actionTypes/cart";

export type TCartId = {
  cartId: number;
};

export type TAddCartRes = Array<TCartId>;

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
  quantity: number;
  price: number;
  option: string;
  optionArray?: ICartMenuOption[];
  cartId?: number;
  stock?: number;
  direct?: boolean;
}

export interface ICartReq {
  direct: boolean;
  menuId: number;
  option: ICartMenuOption[];
  quantity: number;
}

export interface ICartMenuOption {
  id: number;
  quantity: number;
}

export interface ICartState {
  cart: ICart | null;
  localCart: ICart | null;
}

export interface IInitializeCartFunction {
  initializeCart(): void;
}

export interface IInitializeLocalCart {
  type: typeof cartActionTypes.INITIALIZE_LOCAL_CART;
}

export interface IInitializeCart {
  type: typeof cartActionTypes.INITIALIZE_CART;
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
  payload: {
    id: number;
    quantity: number;
  };
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
  error: string;
}

export interface IFetchAddCart {
  type: typeof cartActionTypes.FETCH_ADD_CART;
  payload: ICartMenu[];
}

export interface IFetchAddCartFulfilled {
  type: typeof cartActionTypes.FETCH_ADD_CART_FULFILLED;
  payload: ICart;
}

export interface IFetchAddCartRejected {
  type: typeof cartActionTypes.FETCH_ADD_CART_REJECTED;
  error: string;
}

export interface IFetchRemoveCart {
  type: typeof cartActionTypes.FETCH_REMOVE_CART;
  payload: number;
}

export interface IFetchRemoveCartFulfilled {
  type: typeof cartActionTypes.FETCH_REMOVE_CART_FULFILLED;
  payload: ICart;
}

export interface IFetchRemoveCartRejected {
  type: typeof cartActionTypes.FETCH_REMOVE_CART_REJECTED;
  error: string;
}

export interface IFetchChangeCart {
  type: typeof cartActionTypes.FETCH_CHANGE_CART;
  payload: {
    id: number;
    quantity: number;
  };
}

export interface IFetchChangeCartFulfilled {
  type: typeof cartActionTypes.FETCH_CHANGE_CART_FULFILLED;
  payload: ICart;
}

export interface IFetchChangeCartRejected {
  type: typeof cartActionTypes.FETCH_CHANGE_CART_REJECTED;
  error: string;
}

export interface ISetOrderCart {
  type: typeof cartActionTypes.SET_ORDER_CART;
  payload: ICartMenu[];
}

export type TCartAction =
  | IInitializeLocalCart
  | IInitializeCart
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
  | IFetchAddCartRejected
  | IFetchRemoveCart
  | IFetchRemoveCartFulfilled
  | IFetchRemoveCartRejected
  | IFetchChangeCart
  | IFetchChangeCartFulfilled
  | IFetchChangeCartRejected
  | ISetOrderCart;

// 카트 추가 요청 타입
export type TCartAddReq = {
  menuId: number;
  quantity: number;
  direct: boolean;
  option: Array<ICartMenuOption>;
};
