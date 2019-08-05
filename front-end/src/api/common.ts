import { TCommonError } from "../types/error";
import axios, { AxiosRequestConfig, AxiosError, AxiosResponse } from "axios";
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
  const res = await axios.get(url, config);

  if (res.status === 200) {
    return res.data as SuccessDataType;
  } else {
    return new CommonError({ status: res.status, message: res.data.message });
  }
}

export async function post<SuccessDataType>(url: string, data?: any, config?: AxiosRequestConfig) {
  const res = await axios.post(url, data, config);

  if (res.status === 200) {
    return res.data as SuccessDataType;
  } else {
    return new CommonError({ status: res.status, message: res.data.message });
  }
}

export async function put<SuccessDataType>(url: string, data?: any, config?: AxiosRequestConfig) {
  const res = await axios.put(url, data, config);

  if (res.status === 200) {
    return res.data as SuccessDataType;
  } else {
    return new CommonError({ status: res.status, message: res.data.message });
  }
}

export async function del<SuccessDataType>(url: string, config?: AxiosRequestConfig) {
  const res = await axios.delete(url, config);

  if (res.status === 200) {
    return res.data as SuccessDataType;
  } else {
    return new CommonError({ status: res.status, message: res.data.message });
  }
}
