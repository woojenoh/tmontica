import * as React from "react";
import { numberCommaRegex } from "../../../utils";
import "./styles.scss";

export interface IOrderSheetItemProps {
  name: string;
  price: number;
  options: string;
  quantity: number;
}

function OrderSheetItem(props: IOrderSheetItemProps) {
  const { name, price, options, quantity } = props;

  return (
    <li className="orders__item">
      <div className="orders__item-info">
        <h2 className="orders__item-name">{`${name} - ${numberCommaRegex(price)}원`}</h2>
        <span className="orders__item-options">{options ? options : "옵션이 없습니다."}</span>
      </div>
      <span className="orders__item-quantity">{quantity}개</span>
    </li>
  );
}

export default OrderSheetItem;
