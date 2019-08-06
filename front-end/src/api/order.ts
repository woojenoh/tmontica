import { postWithJWT, API_URL, getWithJWT } from "./common";
import { TOrderReq } from "../types/order";

export function order(data: TOrderReq) {
  return postWithJWT(`${API_URL}/orders`, data);
}

export function getOrderById(orderId: number) {
  return getWithJWT(`${API_URL}/orders/${orderId}`);
}

export function getOrderAll() {
  return getWithJWT(`${API_URL}/orders`);
}
