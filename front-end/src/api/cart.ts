import { TCartAddReq, TCartId, ICart } from "../types/cart";
import { API_URL, del, withJWT, post, get, put } from "./common";

export function addCart(cartAddReqs: Array<TCartAddReq>) {
  return post<TCartId[]>(`${API_URL}/carts`, cartAddReqs, withJWT());
}

export function getCart() {
  return get<ICart>(`${API_URL}/carts`);
}

export function changeCart(cartId: number, quantity: number) {
  return put<void>(
    `${API_URL}/carts/${cartId}`,
    {
      quantity
    },
    withJWT()
  );
}

export function removeCart(cartId: number) {
  return del<void>(`${API_URL}/carts/${cartId}`, withJWT());
}
