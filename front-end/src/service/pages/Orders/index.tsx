import * as React from "react";
import OrderSheet from "../../components/OrderSheet";
import OrderList from "../../components/OrderList";
import "./styles.scss";

export interface IOrdersProps {}

function Orders(props: IOrdersProps) {
  return (
    <main className="main">
      <section className="orders">
        <div className="orders__top">
          <h1 className="orders__top-title">주문서(00001)</h1>
          <span className="orders__top-cancel">주문취소</span>
        </div>
        <OrderSheet />
      </section>
      <section className="orders-list">
        <h1 className="orders-list__title">주문내역(3)</h1>
        <OrderList />
      </section>
    </main>
  );
}

export default Orders;
