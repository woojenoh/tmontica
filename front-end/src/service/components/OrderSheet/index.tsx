import * as React from "react";
import OrderSheetItem from "../OrderSheetItem";
import "./styles.scss";
import { OrderAPI } from "../../../API";
import { TOrderDetail } from "../../../types/menu";
import _ from "underscore";
import history from "../../../history";

export interface IOrderSheetProps {
  orderId: number;
}

interface TOrder {
  orderId: number;
  payment: string;
  status: string;
  totalPrice: number;
  realPrice: number;
  usedPoint: number;
  orderDate: string;
  menus: Array<TOrderDetail>;
}

export interface IOrderSheetState {
  order: TOrder;
}

class OrderSheet extends React.Component<IOrderSheetProps, IOrderSheetState> {
  state = {
    order: {} as TOrder
  };

  async getOrder() {
    try {
      const order = await OrderAPI.getOrderById(this.props.orderId);

      if (order === "") {
        return;
      }

      this.setState({
        order
      });
    } catch (err) {
      alert(err);
      history.push("/");
    }
  }

  componentDidMount() {
    if (this.props.orderId > 0) {
      this.getOrder();
    }
  }

  componentDidUpdate(prevProps: IOrderSheetProps) {
    if (this.props.orderId !== prevProps.orderId) {
      this.getOrder();
    }
  }

  statusCode = {
    미결제: 0,
    결제완료: 1,
    준비중: 2,
    준비완료: 3,
    READY: 3,
    픽업완료: 4
  } as { [key: string]: number };

  fromStatusCode = {
    0: "미결제",
    1: "결제완료",
    2: "준비중",
    3: "준비완료",
    4: "픽업완료"
  } as { [key: number]: string };

  render() {
    const { statusCode, fromStatusCode } = this;
    const { order } = this.state;
    const { orderId } = this.props;
    const isReadyOrder = orderId > 0;
    const statusArray = [];

    if (isReadyOrder) {
      // 현재 상태와 5가지 상태를 비교해 색상 배열 생성
      for (let i = 0; i < 5; i++) {
        if (i < statusCode[order.status]) {
          statusArray.push(0);
        } else if (i === statusCode[order.status]) {
          statusArray.push(1);
        } else {
          statusArray.push(2);
        }
      }
    }

    return (
      <section className={`orders ${!Number.isInteger(order.orderId) ? "hide" : ""}`}>
        <div className={`orders__top`}>
          <h1 className="orders__top-title">주문서({order.orderId})</h1>
          <span className="orders__top-cancel">주문취소</span>
        </div>
        <div className="orders__content">
          <ul className="orders__status-container">
            {isReadyOrder
              ? statusArray.map((s, index) => {
                  if (s === 0) {
                    return (
                      <li key={index} className="orders__status orders__status--gray">
                        {fromStatusCode[index]}
                      </li>
                    );
                  } else if (s === 1) {
                    return (
                      <li key={index} className="orders__status orders__status--green">
                        {fromStatusCode[index]}
                      </li>
                    );
                  } else {
                    return (
                      <li key={index} className="orders__status">
                        {fromStatusCode[index]}
                      </li>
                    );
                  }
                })
              : ""}
          </ul>
          <ul className="orders__items">
            {_(order.menus).map((m: TOrderDetail) => {
              return (
                <OrderSheetItem
                  key={`${orderId}_${m.id}`}
                  name={m.nameKo}
                  price={m.sellPrice}
                  optionString={m.optionString}
                  quantity={m.quantity}
                />
              );
            })}
          </ul>
          <div className="orders__total">
            <div className="orders__total-price">
              주문금액 - {Number(order.totalPrice).toLocaleString()}원
            </div>
            <div className="orders__total-discount">
              할인금액 - {Number(order.usedPoint).toLocaleString()}원
            </div>
            <div className="orders__total-result">
              최종금액 - {Number(order.realPrice).toLocaleString()}원
            </div>
          </div>
        </div>
      </section>
    );
  }
}

export default OrderSheet;
