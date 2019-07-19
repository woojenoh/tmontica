import * as React from "react";
import OrderSheetItem from "../OrderSheetItem";
import "./styles.scss";

export interface IOrderSheetProps {}

export interface IOrderSheetState {}

class OrderSheet extends React.Component<IOrderSheetProps, IOrderSheetState> {
  public render() {
    return (
      <div className="orders__content">
        <ul className="orders__status-container">
          <li className="orders__status orders__status--green">미결제</li>
          <li className="orders__status">결제완료</li>
          <li className="orders__status">준비중</li>
          <li className="orders__status">준비완료</li>
          <li className="orders__status">픽업완료</li>
        </ul>
        <ul className="orders__items">
          <OrderSheetItem />
          <OrderSheetItem />
        </ul>
        <div className="orders__total">
          <div className="orders__total-price">주문금액 - 4,000원</div>
          <div className="orders__total-discount">할인금액 - 1,000원</div>
          <div className="orders__total-result">최종금액 - 3,000원</div>
        </div>
      </div>
    );
  }
}

export default OrderSheet;
