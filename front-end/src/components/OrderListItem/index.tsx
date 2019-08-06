import * as React from "react";
import "./styles.scss";

export interface IOrderListItemProps {
  orderId: number;
  name: string;
  date: string;
  status: string;
  handleOrderListItemClick(orderId: number): void;
}

function OrderListItem(props: IOrderListItemProps) {
  const { orderId, name, date, status, handleOrderListItemClick } = props;

  return (
    <li
      onClick={e => {
        handleOrderListItemClick(orderId);
      }}
      className={
        /준비완료|READY/.test(status)
          ? "orders-list__item orders-list__item--ready"
          : status === "픽업완료"
          ? "orders-list__item orders-list__item--pickuped"
          : status === "주문취소"
          ? "orders-list__item orders-list__item--cancle"
          : "orders-list__item"
      }
    >
      <div className="orders-list__item-left">
        <span className="orders-list__item-number">{orderId.toString().padStart(5, "0")}</span>
        <span className="orders-list__item-names">{name}</span>
      </div>
      <div className="orders-list__item-right">
        <span className="orders-list__item-date">{date}</span>
        <span className="orders-list__item-status">{status}</span>
      </div>
    </li>
  );
}

export default OrderListItem;
