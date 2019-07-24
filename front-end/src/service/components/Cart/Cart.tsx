import * as React from "react";
import _ from "underscore";
import CartItem from "../CartItem";
import { numberCommaRegex } from "../../../utils";
import * as cartTypes from "../../../types/cart";
import "./styles.scss";

export interface ICartProps {
  isCartOpen: boolean;
  isSignin: boolean;
  localCart: cartTypes.ICart | null;
  handleCartClose(): void;
  addLocalCart(payload: cartTypes.ICartMenu): void;
}

export interface ICartState {
  cart: cartTypes.ICart | null;
}

class Cart extends React.Component<ICartProps, ICartState> {
  componentDidMount() {
    // 테스트를 위해 작성.
    const { addLocalCart } = this.props;
    addLocalCart({
      cartId: 10,
      menuId: 2,
      nameEng: "americano",
      nameKo: "아메리카노",
      imgUrl: "/img/coffee-sample.png",
      option: "HOT/샷추가(1개)/사이즈업",
      quantity: 1,
      price: 1500,
      stock: 100
    });
    addLocalCart({
      cartId: 10,
      menuId: 2,
      nameEng: "americano",
      nameKo: "아메리치노",
      imgUrl: "/img/coffee-sample.png",
      option: "HOT/샷추가(1개)/사이즈업",
      quantity: 2,
      price: 2000,
      stock: 100
    });
    addLocalCart({
      cartId: 10,
      menuId: 2,
      nameEng: "americano",
      nameKo: "카페라떼",
      imgUrl: "/img/coffee-sample.png",
      option: "HOT/샷추가(1개)/사이즈업",
      quantity: 3,
      price: 2500,
      stock: 100
    });
  }

  render() {
    const { isCartOpen, handleCartClose, isSignin, localCart } = this.props;

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
          {/* <ul className="cart__top">
            <span className="cart__edit">선택</span>
            <span className="cart__delete">삭제</span>
          </ul> */}
          <ul className="cart__items">
            {isSignin ? (
              ""
            ) : localCart ? (
              localCart.size ? (
                localCart.menus.map((m, index) => {
                  return (
                    <CartItem
                      key={index}
                      id={index}
                      name={m.nameKo}
                      price={m.price}
                      option={m.option}
                      quantity={m.quantity}
                      imgUrl={m.imgUrl}
                    />
                  );
                })
              ) : (
                <li className="cart__empty">장바구니가 비었습니다.</li>
              )
            ) : (
              <li className="cart__empty">로딩 중입니다.</li>
            )}
          </ul>
          <div className="cart__bottom">
            <div className="cart__total">
              <span className="cart__total-quantity">
                총 {isSignin ? "" : localCart ? localCart.size : "0"}개
              </span>
              <span className="cart__total-price">
                {isSignin ? "" : localCart ? numberCommaRegex(localCart.totalPrice) : "0"}원
              </span>
            </div>
            <button className="button cart__button">결제 및 주문하기</button>
          </div>
        </div>
      </section>
    );
  }
}

export default Cart;
