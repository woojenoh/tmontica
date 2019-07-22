export type TMenusItem = {
  id: number;
  nameKo: string;
  nameEng: string;
  imgUrl: string;
};

export interface ICart {
  size: number;
  totalPrice: number;
  menus: ICartMenu[];
}

export interface ICartMenu {
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

export interface ICartMenuOption {
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
