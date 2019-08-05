import { API_URL, withJWT, attchParamsToURL, post, get } from "./common";
import { TOrderReq, TAddOrderRes, TOrder, TOrderAllRes } from "../types/order";

export function order(data: TOrderReq) {
  return post<TAddOrderRes>(`${API_URL}/orders`, data, withJWT());
}

export function getOrderById(orderId: number) {
  return get<TOrder>(attchParamsToURL(`${API_URL}/orders/${orderId}`), withJWT());
}

export function getOrderAll() {
  return get<TOrderAllRes>(attchParamsToURL(`${API_URL}/orders`), withJWT());
}
