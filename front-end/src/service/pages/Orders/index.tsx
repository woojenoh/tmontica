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
    this.setState({ orderId: orderId });
  }

  render() {
    const { orderId } = this.state;
    return (
      <main className="main">
        <section className="orders">
          <OrderSheet orderId={orderId} />
        </section>
        <section className="orders-list">
          <OrderList handleOrderListItemClick={this.handleOrderListItemClick.bind(this)} />
        </section>
      </main>
    );
  }
}

export default Orders;
