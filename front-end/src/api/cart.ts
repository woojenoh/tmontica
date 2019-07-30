import { TCartAddReq, TAddCartRes, TCartId } from "../types/cart";
import { postWithJWT, API_URL } from "./common";
import { TCommonError } from "../types/error";

export function addCart(cartAddReqs: Array<TCartAddReq>) {
  return postWithJWT<TCartId[], TCommonError>(`${API_URL}/carts`, cartAddReqs);
}
