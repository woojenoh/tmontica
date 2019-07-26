import * as React from "react";
import OrderListItem from "../OrderListItem";
import "./styles.scss";
import { OrderAPI } from "../../../API";
import _ from "underscore";

export interface IOrderListProps {
  handleOrderListItemClick(orderId: number): void;
}

interface IOrder {
  orderId: number;
  orderDate: string;
  status: string;
  menuNames: Array<string>;
}

export interface IOrderListState {
  orders: Array<IOrder>;
}

class OrderList extends React.Component<IOrderListProps, IOrderListState> {
  state = {
    orders: []
  };

  async getOrderAll() {
    const data = await OrderAPI.getOrderAll();
    const { orders } = data;
    _.sortBy(orders, "orderId");
    orders.reverse();

    this.setState({
      orders
    });
  }

  componentDidMount() {
    this.getOrderAll();
  }

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
      },
      {
        orderId: 9,
        orderDate: "2019-07-19 11:12:40",
        status: "주문취소",
        menuNames: ["카페모카", "카페라떼", "소보로빵"]
      }
    ]
  };

  render() {
    const { handleOrderListItemClick } = this.props;
    const { orders } = this.state;
    return (
      <>
        <h1 className="orders-list__title">주문내역({orders.length})</h1>
        <div className="orders-list__content">
          <ul className="orders-list__items">
            {orders.length > 0
              ? _.map(orders, (o: IOrder) => {
                  const name =
                    o.menuNames.length > 0
                      ? `${o.menuNames[0]}${
                          o.menuNames.length > 1 ? `외 ${o.menuNames.length - 1}개}` : ``
                        }`
                      : "";

                  const dateString = new Date(o.orderDate).toLocaleDateString();
                  return (
                    <OrderListItem
                      key={o.orderId}
                      orderId={o.orderId}
                      name={name}
                      date={dateString}
                      status={o.status}
                      handleOrderListItemClick={handleOrderListItemClick}
                    />
                  );
                })
              : ""}
          </ul>
        </div>
      </>
    );
  }
}

export default OrderList;
