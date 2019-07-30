import { postWithJWT, API_URL, getWithJWT } from "./common";
import { TOrderReq } from "../types/order";

export function order<T>(data: TOrderReq) {
  return postWithJWT<T>(`${API_URL}/orders`, data);
}

export function getOrderById<T>(orderId: number) {
  return getWithJWT<T>(`${API_URL}/orders/${orderId}`);
}

export function getOrderAll<T>() {
  return getWithJWT<T>(`${API_URL}/orders`);
}
