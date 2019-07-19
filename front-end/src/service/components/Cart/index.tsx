import * as React from "react";
import CartItem from "../CartItem";
import "./styles.scss";

export interface ICartProps {
  isCartOpen: boolean;
  handleCartClose(): void;
}

export interface ICartState {}

export default class Cart extends React.Component<ICartProps, ICartState> {
  render() {
    const { isCartOpen, handleCartClose } = this.props;

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
            <CartItem />
          </ul>
          <div className="cart__bottom">
            <div className="cart__total">
              <span className="cart__total-quantity">총 2개</span>
              <span className="cart__total-price">4,000원</span>
            </div>
            <button className="button cart__button">결제 및 주문하기</button>
          </div>
        </div>
      </section>
    );
  }
}
