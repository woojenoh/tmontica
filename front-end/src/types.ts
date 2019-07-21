export type Menu = {
  id: number;
  nameKo: string;
  nameEng: string;
  imgUrl: string;
};

export interface CartType {
  size: number;
  totalPrice: number;
  menus: CartMenuType[];
}

export interface CartMenuType {
  cartId?: number;
  stock?: number;
  menuId: number;
  menuNameEng: string;
  menuNameKo: string;
  imgUrl: string;
  option: string;
  quantity: number;
  price: number;
}

export interface CartMenuOptionType {
  Temperature?: {
    name: string;
    price: number;
  };
  Shot?: {
    name: string;
    price: number;
    amount: number;
  };
  Syrup?: {
    name: string;
    price: number;
    amount: number;
  };
  Size?: {
    name: string;
    price: number;
  };
}
