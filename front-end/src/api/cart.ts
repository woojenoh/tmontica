import { TCartAddReq, TCartId, ICart } from "../types/cart";
import { postWithJWT, API_URL, getWithJWT } from "./common";
import { TCommonError } from "../types/error";

export function addCart(cartAddReqs: Array<TCartAddReq>) {
  return postWithJWT<TCartId[], TCommonError>(`${API_URL}/carts`, cartAddReqs);
}

export function getCart() {
  return getWithJWT<ICart, TCommonError>(`${API_URL}/carts`);
}
