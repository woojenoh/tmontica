import * as React from "react";
import OrderSheetItem from "../OrderSheetItem";
import { numberCommaRegex } from "../../../utils";
import "./styles.scss";

export interface IOrderSheetProps {}

export interface IOrderSheetState {}

class OrderSheet extends React.Component<IOrderSheetProps, IOrderSheetState> {
  tempData = {
    orderId: 10,
    payment: "현장결제",
    status: "결제완료",
    totalPrice: 3000,
    realPrice: 2000,
    usedPoint: 1000,
    orderDate: "2019-07-19 11:12:40",
    menus: [
      {
        menuId: 1,
        NameEng: "americano",
        NameKo: "아메리카노",
        option: "HOT/샷추가(1개)/사이즈추가",
        quantity: 1,
        price: 1000
      },
      {
        menuId: 2,
        NameEng: "garlic bread",
        NameKo: "갈릭퐁당브레드",
        option: "",
        quantity: 1,
        price: 3000
      }
    ]
  };

  statusCode = {
    미결제: 0,
    결제완료: 1,
    준비중: 2,
    준비완료: 3,
    픽업완료: 4
  } as { [key: string]: number };

  fromStatusCode = {
    0: "미결제",
    1: "결제완료",
    2: "준비중",
    3: "준비완료",
    4: "픽업완료"
  } as { [key: number]: string };

  public render() {
    const { tempData, statusCode, fromStatusCode } = this;

    // 현재 상태와 5가지 상태를 비교해 색상 배열 생성
    const statusArray = [];
    for (let i = 0; i < 5; i++) {
      if (i < statusCode[tempData.status]) {
        statusArray.push(0);
      } else if (i === statusCode[tempData.status]) {
        statusArray.push(1);
      } else {
        statusArray.push(2);
      }
    }

    return (
      <>
        <div className="orders__top">
          <h1 className="orders__top-title">주문서({tempData.orderId})</h1>
          <span className="orders__top-cancel">주문취소</span>
        </div>
        <div className="orders__content">
          <ul className="orders__status-container">
            {statusArray.map((s, index) => {
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
            })}
          </ul>
          <ul className="orders__items">
            {tempData.menus.map(m => {
              return (
                <OrderSheetItem
                  key={m.menuId}
                  name={m.NameKo}
                  price={m.price}
                  options={m.option}
                  quantity={m.quantity}
                />
              );
            })}
          </ul>
          <div className="orders__total">
            <div className="orders__total-price">
              주문금액 - {numberCommaRegex(tempData.totalPrice)}원
            </div>
            <div className="orders__total-discount">
              할인금액 - {numberCommaRegex(tempData.usedPoint)}원
            </div>
            <div className="orders__total-result">
              최종금액 - {numberCommaRegex(tempData.realPrice)}원
            </div>
          </div>
        </div>
      </>
    );
  }
}

export default OrderSheet;
