import { get, API_URL } from "./common";
import { TMenu, TMenuByCategory } from "../types/menu";

export function getMenuAll() {
  return get<Object>(`${API_URL}/menus`);
}

export function getMenuByCateory(categoryEng: string, page = 1, size = 8) {
  return get<TMenuByCategory>(`${API_URL}/menus/${categoryEng}?page=${page}&size=${size}`);
}

export function getMenuById(menuId: number = 1) {
  return get<TMenu>(`${API_URL}/menus/${menuId}`);
}
