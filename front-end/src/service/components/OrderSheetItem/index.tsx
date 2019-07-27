import * as React from "react";
import "./styles.scss";

export interface IOrderSheetItemProps {
  name: string;
  price: number;
  optionString: string;
  quantity: number;
}

function OrderSheetItem(props: IOrderSheetItemProps) {
  const { name, price, optionString, quantity } = props;

  return (
    <li className="orders__item">
      <img src="/img/coffee-sample.png" alt="Coffee Sample" className="orders__item-img" />
      <div className="orders__item-info">
        <h2 className="orders__item-name">{`${name}${
          Number.isInteger(price) ? ` - ${Number(price).toLocaleString()}` : ""
        }`}</h2>
        <span className="orders__item-options">
          {optionString ? optionString : "옵션이 없습니다."}
        </span>
      </div>
      <div className="orders__item-quantity-wrapper">
        <span className="orders__item-quantity">{quantity}개</span>
      </div>
    </li>
  );
}

export default OrderSheetItem;
