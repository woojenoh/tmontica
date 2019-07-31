import { TCartAddReq, TCartId, ICart } from "../types/cart";
import { post, postWithJWT, API_URL, getWithJWT, get } from "./common";
import { TCommonError } from "../types/error";
import { IUserSignupInfo, IUserSigninInfo, IJwtToken, IUserSigninActive } from "../types/user";
import { string } from "prop-types";

export function addCart(cartAddReqs: Array<TCartAddReq>) {
  return postWithJWT<TCartId[], TCommonError>(`${API_URL}/carts`, cartAddReqs);
}

export function getCart() {
  return getWithJWT<ICart, TCommonError>(`${API_URL}/carts`);
}

export function signUp(data: IUserSignupInfo) {
  return post<any, TCommonError>(`${API_URL}/users/signup`, data);
}

export function signIn(data: IUserSigninInfo) {
  return post<string, TCommonError>(`${API_URL}/users/signin`, data);
}

export function signInActive(params: IUserSigninActive) {
  return get<any, TCommonError>(`${API_URL}/users/active`, params);
}

export function findId(params: { email: string }) {
  return get<any, TCommonError>(`${API_URL}/users/findid`, params);
}

export function findIdConfirm(data: { authCode: string }) {
  return post<string, TCommonError>(`${API_URL}/users/findid/confirm`, data);
}

export function findPassword(data: { email: string; id: string }) {
  return get<any, TCommonError>(`${API_URL}/users/findpw`, data);
}
