import { TCartAddReq } from "./types/cart";
import history from "./history";
import { TOrderReq } from "./types/order";

// const API_URL = "http://localhost:3000/fakeapi";
// const API_URL = "https://my-json-server.typicode.com/yeolsa/tmontica-json";
export const API_URL = "http://tmontica-idev.tmon.co.kr/api";
// export const API_URL = "http://localhost:8080/api";

function fetchJSON(reqURL: string) {
  return fetch(reqURL, {
    headers: {
      Accept: "application/json"
    }
  }).then(res => {
    return res.ok ? res.json() : new Error();
  });
}

function postWithJWT(reqURL: string, data: any) {
  const jwt = localStorage.getItem("JWT") || "";

  return fetch(reqURL, {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: jwt
    },
    method: "POST",
    body: JSON.stringify(data)
  }).then(res => {
    return res.ok ? res.json() : new Error();
  });
}

export const MenuAPI = (() => {
  function getMenuAll() {
    return fetchJSON(`${API_URL}/menus`);
  }

  function getMenuByCateory(categoryEng: string, page = 1, size = 4) {
    return fetchJSON(`${API_URL}/menus/${categoryEng}?page=${page}&size=${size}`);
  }

  function getMenuById(menuId: number = 1) {
    return fetchJSON(`${API_URL}/menus/${menuId}`);
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
  async function order(data: TOrderReq) {
    const orderId = await postWithJWT(`${API_URL}/orders`, data);

    history.push(`/orders?orderId=${orderId}`);
  }

  return {
    order
  };
})();
