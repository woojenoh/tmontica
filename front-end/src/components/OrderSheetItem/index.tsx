import * as React from "react";
import "./styles.scss";
import { BASE_URL } from "../../constants";

export interface IOrderSheetItemProps {
  name: string;
  price: number;
  option: string;
  quantity: number;
  imgUrl: string;
}

function OrderSheetItem(props: IOrderSheetItemProps) {
  const { name, price, option, quantity, imgUrl } = props;

  return (
    <li className="orders__item">
      <img src={`${BASE_URL}${imgUrl}`} alt="Coffee Sample" className="orders__item-img" />
      <div className="orders__item-info">
        <h2 className="orders__item-name">{`${name}${
          Number.isInteger(price) ? ` - ${Number(price).toLocaleString()}` : ""
        }`}</h2>
        <span className="orders__item-options">{option ? option : "옵션이 없습니다."}</span>
      </div>
      <div className="orders__item-quantity-wrapper">
        <span className="orders__item-quantity">{quantity}개</span>
      </div>
    </li>
  );
}

export default OrderSheetItem;
