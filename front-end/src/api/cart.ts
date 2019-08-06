import { TCartAddReq } from "../types/cart";
import { postWithJWT, API_URL } from "./common";

export function addCart(cartAddReqs: Array<TCartAddReq>) {
  return postWithJWT(`${API_URL}/carts`, cartAddReqs);
}
