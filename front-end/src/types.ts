export type TMenusItem = {
  id: number;
  nameKo: string;
  nameEng: string;
  imgUrl: string;
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

export type TBasicMenuOptionArray = Array<TBasicMenuOption>;
export type TMenuOptionMap = Map<string, TMenuOption>;

// 카트 추가 요청 타입
export type TCartAddReq = {
  menuId: number;
  quantity: number;
  direct: boolean;
  option: TBasicMenuOptionArray;
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
  nameEng: string;
  nameKo: string;
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
