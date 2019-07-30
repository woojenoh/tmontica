import { string, number } from "prop-types";

export interface TMenuByCategory {
  categoryKo: string;
  menus: Array<TMenusItem>;
}

// 메뉴 추가시
export interface TMenuAddRequest {
  nameKo: string;
  nameEng: string;
  description: string;
  monthlyMenu: boolean;
  categoryKo: string;
  categoryEng: string;
  productPrice: number;
  sellPrice: number;
  discountRate: number;
  optionIds: Array<number>;
  usable: boolean;
  startDate: string;
  endDate: string;
}

export type TMenusItem = {
  id: number;
  nameKo: string;
  nameEng: string;
  imgUrl: string;
  stock: number;
};

export interface TBasicMenuOption {
  id: number;
  quantity: number;
}

export interface TMenuOption extends TBasicMenuOption {
  type: string;
  name: string;
  price: number | 0;
}

export interface TMenu {
  id: number;
  nameEng: string;
  nameKo: string;
  description: string;
  imgUrl: string;
  sellPrice: number;
  discountRate: number;
  category: string;
  stock: number;
  monthlyMenu: boolean;
  option: Array<TMenuOption>;
  getOptionById(id: number): TMenuOption;
}

export interface TOrderDetail {
  id: number;
  nameEng: string;
  nameKo: string;
  description: string;
  imgUrl: string;
  sellPrice: number;
  discountRate: number;
  category: string;
  stock: number;
  monthlyMenu: boolean;
  option: string;
  getOptionById(id: number): TMenuOption;
  quantity: number;
}

export type TBasicMenuOptionArray = Array<TBasicMenuOption>;
export type TMenuOptionMap = Map<string, TMenuOption>;
