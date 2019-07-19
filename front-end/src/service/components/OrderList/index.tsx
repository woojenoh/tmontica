import * as React from "react";
import OrderListItem from "../OrderListItem";
import "./styles.scss";

export interface IOrderListProps {}

export interface IOrderListState {}

class OrderList extends React.Component<IOrderListProps, IOrderListState> {
  public render() {
    return (
      <div className="orders-list__content">
        <ul className="orders-list__items">
          <OrderListItem />
          <OrderListItem />
          <OrderListItem />
        </ul>
      </div>
    );
  }
}

export default OrderList;
