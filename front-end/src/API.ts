import { TCartAddReq } from "./types";

// const API_URL = "http://localhost:3000/fakeapi";
// const API_URL = "https://my-json-server.typicode.com/yeolsa/tmontica-json";
const API_URL = "http://tmontica-idev.tmon.co.kr/api";

function fetchJSON(reqURL: string) {
  return fetch(reqURL, {
    headers: {
      Accept: "application/json"
    }
  }).then(res => {
    return res.ok ? res.json() : new Error();
  });
}

function post(reqURL: string, data: any) {
  return fetch(reqURL, {
    headers: {
      method: "POST",
      body: JSON.stringify(data)
    }
  }).then(res => {
    return res.ok ? res.json() : new Error();
  });
}

export const MenuAPI = (() => {
  function getMenuAll() {
    return fetchJSON(`${API_URL}/menus`);
  }

  function getMenuByCateory(categoryEng: string) {
    return fetchJSON(`${API_URL}/menus/${categoryEng}`);
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
  function* addCart(cartAddReq: TCartAddReq) {
    try {
      yield post(`${API_URL}/carts`, cartAddReq);
    } catch (error) {}
  }

  return {
    addCart
  };
})();
