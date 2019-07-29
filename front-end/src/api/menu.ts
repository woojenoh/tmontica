import { get, API_URL } from "./common";

export function getMenuAll() {
  return get(`${API_URL}/menus`);
}

export function getMenuByCateory(categoryEng: string, page = 1, size = 4) {
  return get(`${API_URL}/menus/${categoryEng}?page=${page}&size=${size}`);
}

export function getMenuById(menuId: number = 1) {
  return get(`${API_URL}/menus/${menuId}`);
}
