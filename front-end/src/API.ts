const API_URL = "http://localhost:3000/fakeapi";
// const API_URL = "https://my-json-server.typicode.com/yeolsa/tmontica-json";
// const API_URL = "http://localhost:8080/api";

function fetchJSON(reqURL: string) {
  return fetch(reqURL, {
    headers: {
      Accept: "application/json"
    }
  }).then(res => {
    return res.ok ? res.json() : new Error();
  });
}

export const MenuAPI = (() => {
  function getMenuAll() {
    return fetchJSON(`${API_URL}/menus/all`);
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
