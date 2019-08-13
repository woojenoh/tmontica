import * as React from "react";
import OrderListItem from "../OrderListItem";
import "./styles.scss";
import { getOrderByPaging } from "../../api/order";
import _ from "underscore";
import { TOrderAllRes, IOrder } from "../../types/order";
import { CommonError } from "../../api/CommonError";
import { handleError } from "../../api/common";
import InfiniteScroll from "react-infinite-scroller";

export interface IOrderListProps {
  handleOrderListItemClick(orderId: number): void;
  signout(): void;
}

export interface IOrderListState extends TOrderAllRes {
  hasMore: boolean;
  page: number;
  size: number;
}

export default class OrderList extends React.Component<IOrderListProps, IOrderListState> {
  shouldComponentUpdate(nextProps: IOrderListProps, nextState: IOrderListState) {
    if (_.isEqual(this.state.orders, nextState.orders)) {
      return false;
    } else {
      return true;
    }
  }

  state = {
    orders: null as IOrder[] | null,
    hasMore: false,
    page: 1,
    size: 8,
    totalCnt: 0
  } as IOrderListState;
  intervalId = {} as NodeJS.Timeout;

  async getOrderByPaging() {
    try {
      const data = await getOrderByPaging(this.state.page, this.state.size);

      if (!data) throw new Error("주문 내역이 존재하지 않습니다.");
      if (data instanceof CommonError) throw data;

      const { orders, totalCnt } = data;
      const combinedOrders =
        this.state.orders !== null ? [...this.state.orders, ...orders] : [...orders];

      this.setState({
        totalCnt,
        orders: combinedOrders,
        page: this.state.page + 1,
        hasMore: totalCnt > combinedOrders.length ? true : false
      });

      return Promise.resolve();
    } catch (error) {
      const result = await handleError(error);
      if (result === "signout") {
        this.props.signout();
      }
    }
  }

  async getOrderAll() {
    try {
      const data = await getOrderByPaging(1, this.state.size * this.state.page);

      if (!data) throw new Error("주문 내역이 존재하지 않습니다.");
      if (data instanceof CommonError) throw data;

      const { orders, totalCnt } = data;

      // _.sortBy(orders, "orderId");
      // orders.reverse();

      this.setState({
        orders,
        totalCnt,
        hasMore: !this.state.hasMore ? false : true
      });
    } catch (error) {
      const result = await handleError(error);
      if (result === "signout") {
        this.props.signout();
      }
    }
  }

  // 주문취소 즉시 목록에 상태 반영
  updateOrderStatus(orderId: number, status: string) {
    const orders = this.state.orders.map((o, i) => {
      if (o.orderId === orderId) {
        o.status = status;
      }
      return o;
    });
    this.setState({
      orders: [...orders]
    });
  }

  componentDidMount() {
    this.getOrderByPaging().then(
      () =>
        (this.intervalId = setInterval(() => {
          this.getOrderAll();
        }, 10000))
    );
  }

  componentWillUnmount() {
    clearInterval(this.intervalId);
  }

  render() {
    const { handleOrderListItemClick } = this.props;
    const { orders } = this.state;

    const orderListItems =
      orders && orders.length > 0
        ? _.map(orders, (o: IOrder) => {
            const name =
              o.menuNames.length > 0
                ? `${o.menuNames[0]}${
                    o.menuNames.length > 1 ? `외 ${o.menuNames.length - 1}개` : ``
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
        : "";

    return (
      <>
        <h1 className="orders-list__title">주문내역{/*orders.length*/}</h1>
        <div className="orders-list__content">
          {orders ? (
            orders.length ? (
              <ul className="orders-list__items">
                <InfiniteScroll
                  pageStart={1}
                  loadMore={this.getOrderByPaging.bind(this)}
                  hasMore={this.state.hasMore}
                >
                  {orderListItems}
                </InfiniteScroll>
              </ul>
            ) : (
              "주문내역이 존재하지 않습니다."
            )
          ) : (
            "로딩 중입니다."
          )}
        </div>
      </>
    );
  }
}
