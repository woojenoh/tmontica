import { TOrderDetail } from "./menu";

export interface TOrderReq {
  menus: Array<{ cartId: number }>;
  usedPoint: number;
  totalPrice: number;
  payment: string;
}

export interface TAddOrderRes {
  orderId: number;
}

export interface TOrder {
  orderId: number;
  payment: string;
  status: string;
  totalPrice: number;
  realPrice: number;
  usedPoint: number;
  orderDate: string;
  menus: Array<TOrderDetail>;
}

export interface TOrderAllRes {
  orders: Array<IOrder>;
  totalCnt: number;
}

export interface IOrder {
  orderId: number;
  orderDate: string;
  status: string;
  menuNames: Array<string>;
}
