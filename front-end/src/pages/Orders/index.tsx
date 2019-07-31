import React, { PureComponent } from "react";
import OrderSheet from "../../components/OrderSheet";
import OrderList from "../../components/OrderList";
import "./styles.scss";

export interface IOrdersProps {}

class Orders extends PureComponent<IOrdersProps> {
  state = {
    orderId: 0
  };

  handleOrderListItemClick(orderId: number) {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
    this.setState({ orderId: orderId });
  }

  render() {
    const { orderId } = this.state;
    return (
      <main className="main">
        <OrderSheet orderId={orderId} />
        <section className="orders-list">
          <OrderList handleOrderListItemClick={this.handleOrderListItemClick.bind(this)} />
        </section>
      </main>
    );
  }
}

export default Orders;
