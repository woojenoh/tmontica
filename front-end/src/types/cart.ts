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
  payload: ICart;
}

export interface IRemoveLocalCart {
  type: typeof cartActionTypes.REMOVE_LOCAL_CART;
  payload: number;
}

export type TCartAction = IInitializeLocalCart | IAddLocalCart | IRemoveLocalCart;
