import { TMessageError, TExceptionError, TCommonError } from "../types/error";

export const API_URL = "http://tmontica-idev.tmon.co.kr/api";

function transfromData(data: any) {
  if (typeof data === "string") {
    try {
      data = JSON.parse(data);
    } catch (e) {
      /* Ignore */
    }
  }
  return data;
}

async function fetchTMON<SuccessDataType, ErrorType extends TCommonError>(
  url: string,
  options: RequestInit
) {
  const res = await fetch(url, options);

  const text = await res.text();
  const data = transfromData(text);

  if (res.ok) {
    return data as SuccessDataType;
  }

  const originErr = data as ErrorType;
  const err = Object.assign(originErr, { status: res.status });
  throw err;
}

function error(message: string | undefined): never {
  throw new Error(message);
}

export function get<T, E extends TCommonError>(
  reqURL: string,
  params?: Map<string, string> | Object | null,
  jwt?: string
) {
  if (typeof params !== "undefined" && params !== null && !/[?]/.test(reqURL)) {
    if (params instanceof Map) {
      reqURL += `?${Array.from(params.entries())
        .map(x => {
          return `${x[0]}=${x[1]}`;
        })
        .join("&")}`;
    } else {
      reqURL += `?${Object.entries(params)
        .map(([key, val]) => `${key}=${val}`)
        .join("&")}`;
    }
  }

  return fetchTMON<T, E>(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt || ""
    },
    method: "GET"
  });
}

export function del<T, E extends TCommonError>(
  reqURL: string,
  params?: Map<string, string> | Object | null,
  jwt?: string
) {
  if (typeof params !== "undefined" && params !== null && !/[?]/.test(reqURL)) {
    if (params instanceof Map) {
      reqURL += `?${Array.from(params.entries())
        .map(x => {
          return `${x[0]}=${x[1]}`;
        })
        .join("&")}`;
    } else {
      reqURL += `?${Object.entries(params)
        .map(([key, val]) => `${key}=${val}`)
        .join("&")}`;
    }
  }

  return fetchTMON<T, E>(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt || ""
    },
    method: "DELETE"
  });
}

export function getWithJWT<T, E extends TCommonError>(
  reqURL: string,
  params?: Map<string, string> | null
) {
  const jwt = localStorage.getItem("jwt") || "";

  return get<T, E>(reqURL, params, jwt);
}

export function post<T, E extends TCommonError>(reqURL: string, data: any, jwt?: string) {
  return fetchTMON<T, E>(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt || ""
    },
    method: "POST",
    body: JSON.stringify(data)
  });
}

export function put<T, E extends TCommonError>(reqURL: string, data: any, jwt?: string) {
  return fetchTMON<T, E>(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt || ""
    },
    method: "PUT",
    body: JSON.stringify(data)
  });
}

function withJWT(this: void, f: Function) {
  const jwt = localStorage.getItem("jwt") || "";

  return f.call(f, jwt);
}

export function postWithJWT<T, E extends TCommonError>(this: void, reqURL: string, data: any) {
  return withJWT(post.bind(this, reqURL, data));
}

export function putWithJWT<T, E extends TCommonError>(this: void, reqURL: string, data: any) {
  return withJWT(put.bind(this, reqURL, data));
}

export function delWithJWT<T, E extends TCommonError>(this: void, reqURL: string) {
  return withJWT(del.bind(this, reqURL, ""));
}
