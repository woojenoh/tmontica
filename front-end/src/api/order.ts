import { API_URL, withJWT, post, get, del } from "./common";
import { TOrderReq, TAddOrderRes, TOrder, TOrderAllRes } from "../types/order";

export function order(data: TOrderReq) {
  return post<TAddOrderRes>(`${API_URL}/orders`, data, withJWT());
}

export function getOrderById(orderId: number) {
  return get<TOrder>(`${API_URL}/orders/${orderId}`, withJWT());
}

export function getOrderAll() {
  return get<TOrderAllRes>(`${API_URL}/orders`, withJWT());
}

export function getOrderByPaging(page: number = 1, size: number = 8) {
  return get<TOrderAllRes>(
    `${API_URL}/orders`,
    withJWT({
      params: { page, size }
    })
  );
}

export function cancleOrderById(orderId: number) {
  return del<void>(`${API_URL}/orders/${orderId}`, withJWT());
}
