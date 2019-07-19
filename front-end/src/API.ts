export const API_URL = "http://localhost:8080/api";

export const MenuAPI = (() => {
  function getMenuAll() {
    return fetch(`${API_URL}/menus`, {
      headers: {
        Accept: "application/json"
      }
    }).then(res => (res.ok ? res.json() : new Error()));
  }

  function getMenuById(categoryEng: string = "coffee", menuId: number = 1) {
    return fetch(`${API_URL}/${categoryEng}/${menuId}`, {
      headers: {
        Accept: "application/json"
      }
    }).then(res => (res.ok ? res.json() : new Error()));
  }

  return {
    getMenuAll,
    getMenuById
  };
})();
