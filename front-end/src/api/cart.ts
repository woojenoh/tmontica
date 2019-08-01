import { TCartAddReq, TCartId, ICart } from "../types/cart";
import { postWithJWT, putWithJWT, API_URL, getWithJWT, delWithJWT } from "./common";
import { TCommonError } from "../types/error";

export function addCart(cartAddReqs: Array<TCartAddReq>) {
  return postWithJWT<TCartId[], TCommonError>(`${API_URL}/carts`, cartAddReqs);
}

export function getCart() {
  return getWithJWT<ICart, TCommonError>(`${API_URL}/carts`);
}

export function changeCart(cartId: number, quantity: number) {
  return putWithJWT<void, TCommonError>(`${API_URL}/carts/${cartId}`, {
    quantity
  });
}

export function removeCart(cartId: number) {
  return delWithJWT<void, TCommonError>(`${API_URL}/carts/${cartId}`);
}
