import { TCartAddReq, TAddCartRes } from "../types/cart";
import { postWithJWT, API_URL } from "./common";
import { TCommonError } from "../types/error";

export function addCart<T>(cartAddReqs: Array<TCartAddReq>) {
  return postWithJWT<T>(`${API_URL}/carts`, cartAddReqs);
}
