import React, { PureComponent } from "react";
import OrderSheet from "../../components/OrderSheet";
import OrderList from "../../components/OrderList";
import "./styles.scss";

export interface IOrdersProps {}

class Orders extends PureComponent<IOrdersProps> {
  handleOrderListItemClick() {}

  render() {
    return (
      <main className="main">
        <section className="orders">
          <OrderSheet orderId={0} />
        </section>
        <section className="orders-list">
          <OrderList handleOrderListItemClick={this.handleOrderListItemClick.bind(this)} />
        </section>
      </main>
    );
  }
}

export default Orders;
