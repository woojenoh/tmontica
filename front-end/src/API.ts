import { TCartAddReq } from "./types/cart";
import { TOrderReq } from "./types/order";
import history from "./history";

// const API_URL = "http://localhost:3000/fakeapi";
// const API_URL = "https://my-json-server.typicode.com/yeolsa/tmontica-json";
export const API_URL = "http://tmontica-idev.tmon.co.kr/api";
// export const API_URL = "http://localhost:8080/api";

async function fetchTMON(url: string, options: RequestInit) {
  const res = await fetch(url, options);

  const json = await res.json();
  if (res.ok) {
    return json;
  }

  const err = json as {
    timestamp: string;
    status: number;
    error: string;
    message: string;
    path: string;
  };

  throw new Error(err.message);
}

function get(reqURL: string, params?: Map<string, string> | null, jwt?: string) {
  if (typeof params !== "undefined" && params !== null && !/[?]/.test(reqURL)) {
    reqURL += `?${Array.from(params.entries())
      .map(x => {
        return `${x[0]}=${x[1]}`;
      })
      .join("&")}`;
  }

  return fetchTMON(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt || ""
    },
    method: "GET"
  });
}

function getWithJWT(reqURL: string, params?: Map<string, string> | null) {
  const jwt = localStorage.getItem("JWT") || "";

  return get(reqURL, params, jwt);
}

function postWithJWT(reqURL: string, data: any) {
  const jwt = localStorage.getItem("JWT") || "";

  return fetchTMON(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt
    },
    method: "POST",
    body: JSON.stringify(data)
  });
}

export const MenuAPI = (() => {
  function getMenuAll() {
    return get(`${API_URL}/menus`);
  }

  function getMenuByCateory(categoryEng: string, page = 1, size = 4) {
    return get(`${API_URL}/menus/${categoryEng}?page=${page}&size=${size}`);
  }

  function getMenuById(menuId: number = 1) {
    return get(`${API_URL}/menus/${menuId}`);
  }

  return {
    getMenuAll,
    getMenuByCateory,
    getMenuById
  };
})();

export const CartAPI = (() => {
  function addCart(cartAddReqs: Array<TCartAddReq>) {
    return postWithJWT(`${API_URL}/carts`, cartAddReqs);
  }

  return {
    addCart
  };
})();

export const OrderAPI = (() => {
  function order(data: TOrderReq) {
    return postWithJWT(`${API_URL}/orders`, data);
  }

  function getOrderById(orderId: number) {
    return getWithJWT(`${API_URL}/orders/${orderId}`);
  }

  function getOrderAll() {
    return getWithJWT(`${API_URL}/orders`);
  }

  return {
    order,
    getOrderAll,
    getOrderById
  };
})();
