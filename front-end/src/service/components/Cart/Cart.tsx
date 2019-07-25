import * as React from "react";
import CartItem from "../CartItem";
import { numberCommaRegex } from "../../../utils";
import * as cartTypes from "../../../types/cart";
import "./styles.scss";

export interface ICartProps {
  isCartOpen: boolean;
  isSignin: boolean;
  localCart: cartTypes.ICart | null;
  cart: cartTypes.ICart | null;
  handleCartClose(): void;
  addLocalCart(payload: cartTypes.ICartMenu): void;
  fetchSetCart(): void;
}

export interface ICartState {
  cart: cartTypes.ICart | null;
}

class Cart extends React.Component<ICartProps, ICartState> {
  componentDidMount() {
    const { isSignin, addLocalCart, fetchSetCart } = this.props;
    if (isSignin) {
      fetchSetCart();
    } else {
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
        nameKo: "카페라떼",
        imgUrl: "/img/coffee-sample.png",
        option: "HOT/샷추가(1개)/사이즈업",
        quantity: 2,
        price: 2500,
        stock: 100
      });
    }
  }

  render() {
    const { isCartOpen, handleCartClose, isSignin, localCart, cart } = this.props;

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
              cart ? (
                cart.size ? (
                  cart.menus.map((m, index) => {
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
              )
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
                총 {isSignin ? (cart ? cart.size : "0") : localCart ? localCart.size : "0"}개
              </span>
              <span className="cart__total-price">
                {isSignin
                  ? cart
                    ? numberCommaRegex(cart.totalPrice)
                    : "0"
                  : localCart
                  ? numberCommaRegex(localCart.totalPrice)
                  : "0"}
                원
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
