import * as React from "react";
import OrderSheet from "../../components/OrderSheet";
import OrderList from "../../components/OrderList";
import "./styles.scss";

export interface IOrdersProps {}

function Orders(props: IOrdersProps) {
  return (
    <main className="main">
      <section className="orders">
        <OrderSheet />
      </section>
      <section className="orders-list">
        <OrderList />
      </section>
    </main>
  );
}

export default Orders;
