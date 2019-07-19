import * as React from "react";
import CartItem from "../CartItem";
import { numberCommaRegex } from "../../../utils";
import "./styles.scss";

export interface ICartProps {
  isCartOpen: boolean;
  handleCartClose(): void;
}

export interface ICartState {}

export default class Cart extends React.Component<ICartProps, ICartState> {
  tempData = {
    size: 2,
    totalPrice: 2500,
    menus: [
      {
        cardId: 10,
        menuId: 2,
        menuNameEng: "americano",
        menuNameKo: "아메리카노",
        option: "HOT/샷추가(1개)/사이즈업",
        quantity: 1,
        price: 1000,
        stock: 100
      },
      {
        cardId: 11,
        menuId: 3,
        menuNameEng: "caffelatte",
        menuNameKo: "카페라떼",
        option: "HOT/샷추가(1개)/사이즈업",
        quantity: 1,
        price: 1500,
        stock: 50
      }
    ]
  };

  render() {
    const { isCartOpen, handleCartClose } = this.props;
    const { tempData } = this;

    return (
      <section className={isCartOpen ? "cart" : "cart cart--close"}>
        <div
          className={isCartOpen ? "cart__dim" : "cart__dim cart__dim--close"}
          onClick={() => handleCartClose()}
        />
        <div className={isCartOpen ? "cart__content" : "cart__content cart__content--close"}>
          <h1 className="cart__title">장바구니</h1>
          <span className="cart__close" onClick={() => handleCartClose()}>
            &times;
          </span>
          <ul className="cart__top">
            <span className="cart__edit">선택</span>
            <span className="cart__delete">삭제</span>
          </ul>
          <ul className="cart__items">
            {tempData.menus.map((m, index) => {
              return (
                <CartItem
                  key={index}
                  name={m.menuNameKo}
                  price={m.price}
                  option={m.option}
                  quantity={m.quantity}
                />
              );
            })}
          </ul>
          <div className="cart__bottom">
            <div className="cart__total">
              <span className="cart__total-quantity">총 {tempData.size}개</span>
              <span className="cart__total-price">{numberCommaRegex(tempData.totalPrice)}원</span>
            </div>
            <button className="button cart__button">결제 및 주문하기</button>
          </div>
        </div>
      </section>
    );
  }
}
