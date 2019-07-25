export interface TOrderReq {
  menus: Array<Record<string, number>>;
  usedPoint: number;
  totalPrice: number;
  payment: string;
}
