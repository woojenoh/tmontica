import { postWithJWT, API_URL, getWithJWT } from "./common";
import { TOrderReq, TAddOrderRes, TOrder, TOrderAllRes } from "../types/order";
import { TCommonError } from "../types/error";

export function order(data: TOrderReq) {
  return postWithJWT<TAddOrderRes, TCommonError>(`${API_URL}/orders`, data);
}

export function getOrderById(orderId: number) {
  return getWithJWT<TOrder, TCommonError>(`${API_URL}/orders/${orderId}`);
}

export function getOrderAll() {
  return getWithJWT<TOrderAllRes, TCommonError>(`${API_URL}/orders`);
}
