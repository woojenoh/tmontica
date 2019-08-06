import React, { PureComponent, RefObject } from "react";
import OrderSheet from "../../components/OrderSheet";
import OrderList from "../../components/OrderList";
import "./styles.scss";
import { cancleOrderById } from "../../api/order";
import { TOrder, IOrder } from "../../types/order";
import { CommonError } from "../../api/CommonError";

export interface IOrdersProps {}

class Orders extends PureComponent<IOrdersProps> {
  orderList: RefObject<OrderList>;
  state = {
    orderId: 0,
    initLoaded: false
  };

  constructor(props: IOrdersProps) {
    super(props);
    this.orderList = React.createRef();
  }

  handleOrderListItemClick(orderId: number) {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });

    this.setState({ orderId: orderId });
  }

  async handleOrderCancle(order: TOrder) {
    try {
      const data = await cancleOrderById(this.state.orderId);
      if (data instanceof CommonError) {
        throw data;
      }
      alert("주문이 취소되었습니다.");
      order.status = "주문취소";

      // 주문취소 즉시 목록에 상태 반영 함수 호출
      this.orderList.current!.updateOrderStatus(this.state.orderId, order.status);

      return Promise.resolve(order);
    } catch (error) {
      if (!error.status) {
        alert("네트워크 오류 발생");
      }
      if (error instanceof CommonError) {
        error.alertMessage();
      }
      return Promise.reject();
    }
  }

  componentDidMount() {
    const parsedUrl = new URL(window.location.href);
    const orderId = parsedUrl.searchParams.get("orderId");

    if (!orderId) {
      return;
    }
    this.setState({
      orderId: parseInt(orderId)
    });
  }

  render() {
    const { orderId } = this.state;
    return (
      <main className="main">
        <OrderSheet orderId={orderId} handleOrderCancle={this.handleOrderCancle.bind(this)} />
        <section className="orders-list">
          <OrderList
            ref={this.orderList}
            handleOrderListItemClick={this.handleOrderListItemClick.bind(this)}
          />
        </section>
      </main>
    );
  }
}

export default Orders;
