export interface TOrderReq {
  menus: Array<{ cartId: number }>;
  usedPoint: number;
  totalPrice: number;
  payment: string;
}
