import { get, API_URL } from "./common";

export function getMenuAll<T>() {
  return get<T>(`${API_URL}/menus`);
}

export function getMenuByCateory<T>(categoryEng: string, page = 1, size = 4) {
  return get<T>(`${API_URL}/menus/${categoryEng}?page=${page}&size=${size}`);
}

export function getMenuById<T>(menuId: number = 1) {
  return get<T>(`${API_URL}/menus/${menuId}`);
}
