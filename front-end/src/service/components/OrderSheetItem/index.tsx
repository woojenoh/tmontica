import * as React from "react";
import "./styles.scss";

export interface IOrderSheetItemProps {}

function OrderSheetItem(props: IOrderSheetItemProps) {
  return (
    <li className="orders__item">
      <div className="orders__item-info">
        <h2 className="orders__item-name">아메리카노 - 1,000원</h2>
        <span className="orders__item-options">HOT/샷추가(1개)/시럽추가(1개)/사이즈추가</span>
      </div>
      <span className="orders__item-quantity">1개</span>
    </li>
  );
}

export default OrderSheetItem;
