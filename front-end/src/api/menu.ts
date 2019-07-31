import { get, API_URL } from "./common";
import { TCommonError } from "../types/error";
import { TMenu, TMenuByCategory } from "../types/menu";

export function getMenuAll() {
  return get<Object, TCommonError>(`${API_URL}/menus`);
}

export function getMenuByCateory(categoryEng: string, page = 1, size = 4) {
  return get<TMenuByCategory, TCommonError>(
    `${API_URL}/menus/${categoryEng}?page=${page}&size=${size}`
  );
}

export function getMenuById(menuId: number = 1) {
  return get<TMenu, TCommonError>(`${API_URL}/menus/${menuId}`);
}
