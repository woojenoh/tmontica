import * as React from "react";
import "./styles.scss";

export interface IOrderListItemProps {}

function OrderListItem(props: IOrderListItemProps) {
  return (
    <li className="orders-list__item">
      <div className="orders-list__item-left">
        <span className="orders-list__item-number">00001</span>
        <span className="orders-list__item-title">아메리카노 외 1개</span>
      </div>
      <div className="orders-list__item-right">
        <span className="orders-list__item-date">2019-07-09 11:12:40</span>
        <span className="orders-list__item-status">미결제</span>
      </div>
    </li>
  );
}

export default OrderListItem;
