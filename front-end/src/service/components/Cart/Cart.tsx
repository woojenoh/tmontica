import * as React from "react";
import history from "../../../history";
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
  initializeLocalCart(): void;
  addLocalCart(payload: cartTypes.ICartMenu): void;
  fetchSetCart(): void;
  fetchAddCart(payload: cartTypes.ICartMenu[]): void;
  setOrderCart(payload: cartTypes.ICartMenu[]): void;
}

export interface ICartState {
  cart: cartTypes.ICart | null;
}

class Cart extends React.Component<ICartProps, ICartState> {
  componentDidMount() {
    const { isSignin, initializeLocalCart, fetchSetCart } = this.props;
    if (isSignin) {
      fetchSetCart();
    } else {
      const localCart = localStorage.getItem("localCart");
      // 만약 로컬카트가 생성된게 없으면 생성한다.
      if (!localCart) {
        initializeLocalCart();
      }
    }
  }

  render() {
    const { isCartOpen, handleCartClose, isSignin, localCart, cart, setOrderCart } = this.props;

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
                  cart.menus.map(m => {
                    return (
                      <CartItem
                        key={m.cartId}
                        id={m.cartId}
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
            <button
              className="button cart__button"
              onClick={() =>
                isSignin
                  ? cart
                    ? cart.size
                      ? window.confirm("주문하시겠습니까?")
                        ? setOrderCart(cart.menus)
                        : ""
                      : alert("장바구니가 비었습니다.")
                    : alert("문제가 발생했습니다.")
                  : (alert("로그인 후 주문하세요."), history.push("/signin"))
              }
            >
              결제 및 주문하기
            </button>
          </div>
        </div>
      </section>
    );
  }
}

export default Cart;
