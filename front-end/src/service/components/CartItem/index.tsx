import * as React from "react";
import "./styles.scss";

export interface ICartItemProps {}

function CartItem(props: ICartItemProps) {
  return (
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
  );
}

export default CartItem;
