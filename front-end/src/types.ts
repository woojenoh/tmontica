export type TMenusItem = {
  id: number;
  nameKo: string;
  nameEng: string;
  imgUrl: string;
};

export type TMenuOption = {
  id: number;
  type: string;
  name?: string;
  price: number | 0;
  quantity: number;
};

export type TMenu = {
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
