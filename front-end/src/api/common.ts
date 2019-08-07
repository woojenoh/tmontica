import axios, { AxiosRequestConfig } from "axios";
import { CommonError } from "./CommonError";

export const API_URL = "http://tmontica-idev.tmon.co.kr/api";

// 쿼리 파라미터 맵/객체를 URL에 붙여주는 함수
export function attchParamsToURL(url: string, params?: Map<string, string> | Object | null) {
  if (typeof params !== "undefined" && params !== null && !/[?]/.test(url)) {
    if (params instanceof Map) {
      url += `?${Array.from(params.entries())
        .map(x => {
          return `${x[0]}=${x[1]}`;
        })
        .join("&")}`;
    } else {
      url += `?${Object.entries(params)
        .map(([key, val]) => `${key}=${val}`)
        .join("&")}`;
    }
  }
  return url;
}

export function withJWT(header: AxiosRequestConfig = {}) {
  return { ...header, headers: { Authorization: localStorage.getItem("jwt") || "" } };
}

export async function get<SuccessDataType>(url: string, config?: AxiosRequestConfig) {
  try {
    const res = await axios.get(url, config);
    return res.data as SuccessDataType;
  } catch (error) {
    return new CommonError({
      ...error.response,
      ...error.response!.data
    });
  }
}

export async function post<SuccessDataType>(url: string, data?: any, config?: AxiosRequestConfig) {
  try {
    const res = await axios.post(url, data, config);
    return res.data as SuccessDataType;
  } catch (error) {
    return new CommonError({
      ...error.response,
      ...error.response!.data
    });
  }
}

export async function put<SuccessDataType>(url: string, data?: any, config?: AxiosRequestConfig) {
  try {
    const res = await axios.put(url, data, config);
    return res.data as SuccessDataType;
  } catch (error) {
    return new CommonError({
      ...error.response,
      ...error.response!.data
    });
  }
}

export async function del<SuccessDataType>(url: string, config?: AxiosRequestConfig) {
  try {
    const res = await axios.delete(url, config);
    return res.data as SuccessDataType;
  } catch (error) {
    return new CommonError({
      ...error.response,
      ...error.response!.data
    });
  }
}
