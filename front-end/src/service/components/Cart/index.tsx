import * as React from "react";
import CartItem from "../CartItem";
import { numberCommaRegex } from "../../../utils";
import { CartObject } from "../../../types";
import "./styles.scss";

export interface ICartProps {
  isCartOpen: boolean;
  handleCartClose(): void;
}

export interface ICartState {
  cart: CartObject | null;
}

class Cart extends React.Component<ICartProps, ICartState> {
  // 더미 데이터
  isSignin = true;
  tempData = {
    size: 2,
    totalPrice: 3100,
    menus: [
      {
        cartId: 10,
        menuId: 2,
        menuNameEng: "americano",
        menuNameKo: "아메리카노",
        imgUrl: "/img/coffee-sample.png",
        option: "HOT/샷추가(1개)/사이즈업",
        quantity: 1,
        price: 1300,
        stock: 100
      },
      {
        cartId: 11,
        menuId: 3,
        menuNameEng: "caffelatte",
        menuNameKo: "카페라떼",
        imgUrl: "/img/coffee-sample.png",
        option: "HOT/샷추가(1개)/사이즈업",
        quantity: 1,
        price: 1800,
        stock: 50
      }
    ]
  };

  constructor(props: ICartProps) {
    super(props);
    this.state = {
      cart: null
    };
  }

  componentDidMount() {
    const { setCart } = this;
    setCart();
  }

  setCart = (): void => {
    const { isSignin, tempData, initializeLocalCart } = this;
    if (isSignin) {
      this.setState({
        cart: tempData
      });
    } else {
      const localCart = localStorage.getItem("LocalCart");
      if (localCart) {
        this.setState({
          cart: JSON.parse(localCart)
        });
      } else {
        initializeLocalCart();
        const newLocalCart = localStorage.getItem("LocalCart");
        if (newLocalCart) {
          this.setState({
            cart: JSON.parse(newLocalCart)
          });
        }
      }
    }
  };

  initializeLocalCart = (): void => {
    // 로컬 스토리지의 카트가 CartObject 형식과 일치하는지 확인할 방법이 있을까?
    const initCart = {
      size: 0,
      totalPrice: 0,
      menus: []
    };
    localStorage.setItem("LocalCart", JSON.stringify(initCart));
  };

  addCartItem = (): void => {};
  removeCartItem = (): void => {};
  changeCartItemQuantity = (): void => {};

  render() {
    const { cart } = this.state;
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
            {cart ? (
              cart.menus.length ? (
                cart.menus.map((m, index) => {
                  return (
                    <CartItem
                      key={index}
                      name={m.menuNameKo}
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
              <span className="cart__total-quantity">총 {cart ? cart.size : "0"}개</span>
              <span className="cart__total-price">
                {numberCommaRegex(cart ? cart.totalPrice : "0")}원
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
