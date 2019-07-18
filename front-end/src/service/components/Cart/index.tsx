import * as React from "react";
import "./styles.scss";

export interface ICartProps {
  isCartOpen: boolean;
  handleCartClose(): void;
}

function Cart(props: ICartProps) {
  const { isCartOpen, handleCartClose } = props;

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
          <li className="cart__item">
            <img src="/img/coffee-sample.png" alt="Coffee Sample" className="cart__item-img" />
            <div className="cart__item-info">
              <span className="cart__item-name">아메리카노 - 1,000원</span>
              <span className="cart__item-option">HOT/샷추가(1개)/시럽추가(1개)/사이즈추가</span>
            </div>
            <div className="cart__item-quantity">
              <input type="number" className="cart__item-quantity-input" value="1" />
            </div>
          </li>
          <li className="cart__item">
            <img src="/img/bread-sample.jpg" alt="Bread Sample" className="cart__item-img" />
            <div className="cart__item-info">
              <span className="cart__item-name">갈릭퐁당브레드 - 3,000원</span>
              <span className="cart__item-option">옵션이 없습니다.</span>
            </div>
            <div className="cart__item-quantity">
              <input type="number" className="cart__item-quantity-input" value="1" />
            </div>
          </li>
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

export default Cart;
