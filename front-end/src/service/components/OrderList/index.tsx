import * as React from "react";
import OrderListItem from "../OrderListItem";
import "./styles.scss";

export interface IOrderListProps {}

export interface IOrderListState {}

class OrderList extends React.Component<IOrderListProps, IOrderListState> {
  tempData = {
    orders: [
      {
        orderId: 12,
        orderDate: "2019-07-19 11:12:40",
        status: "결제완료",
        menuNames: ["청포도에이드", "카페라떼", "소보로빵"]
      },
      {
        orderId: 11,
        orderDate: "2019-07-19 11:12:40",
        status: "준비완료",
        menuNames: ["아메리카노", "카페라떼", "소보로빵"]
      },
      {
        orderId: 10,
        orderDate: "2019-07-19 11:12:40",
        status: "픽업완료",
        menuNames: ["카페모카", "카페라떼", "소보로빵"]
      }
    ]
  };

  public render() {
    const { tempData } = this;

    return (
      <div className="orders-list__content">
        <ul className="orders-list__items">
          {tempData.orders.map(o => {
            return (
              <OrderListItem
                key={o.orderId}
                orderId={o.orderId}
                name={`${o.menuNames[0]} 외 ${o.menuNames.length - 1}개`}
                date={o.orderDate}
                status={o.status}
              />
            );
          })}
        </ul>
      </div>
    );
  }
}

export default OrderList;
