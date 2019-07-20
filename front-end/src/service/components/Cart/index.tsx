import * as React from "react";
import CartItem from "../CartItem";
import { numberCommaRegex } from "../../../utils";
import "./styles.scss";

export interface ICartProps {
  isCartOpen: boolean;
  handleCartClose(): void;
}

export interface ICartState {}

export interface CartObject {
  size: number;
  totalPrice: number;
  menus: {
    cartId?: number;
    menuId: number;
    menuNameEng: string;
    menuNameKo: string;
    imgUrl: string;
    option: string | OptionObject;
    quantity: number;
    price: number;
    stock?: number;
  }[];
}

export interface OptionObject {
  Temperature?: {
    name: string;
    price: number;
  };
  Shot?: {
    name: string;
    price: number;
    amount: number;
  };
  Syrup?: {
    name: string;
    price: number;
    amount: number;
  };
  Size?: {
    name: string;
    price: number;
  };
}

export default class Cart extends React.Component<ICartProps, ICartState> {
  tempData: CartObject = {
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

  // 비로그인 환경 테스트를 위한 임시 변수
  isSignin = false;

  setLocalStorage = (): void => {
    const localObject: CartObject = {
      size: 1,
      totalPrice: 6300,
      menus: [
        {
          menuId: 1,
          menuNameEng: "americano",
          menuNameKo: "아메리카노",
          imgUrl: "/img/coffee-sample.png",
          option: {
            Temperature: { name: "ICE", price: 0 },
            Shot: { name: "AddShot", price: 300, amount: 1 },
            Syrup: { name: "AddSyrup", price: 300, amount: 1 },
            Size: { name: "SizeUp", price: 500 }
          },
          quantity: 3,
          price: 2100
        }
      ]
    };
    localStorage.setItem("LocalCart", JSON.stringify(localObject));
  };

  getLocalStorage = (): CartObject | null => {
    const localCart = localStorage.getItem("LocalCart");
    if (localCart) {
      return JSON.parse(localCart);
    } else {
      return null;
    }
  };

  // 객체 된 옵션을 정해진 스트링 형식으로 바꾸는 함수.
  optionToString = (option: OptionObject): string => {
    let stringArray = [];
    option.Temperature && stringArray.push(`${option.Temperature.name}`);
    option.Shot && stringArray.push(`샷추가(${option.Shot.amount}개)`);
    option.Syrup && stringArray.push(`시럽추가(${option.Syrup.amount}개)`);
    option.Size && stringArray.push(`사이즈업`);
    if (stringArray.length === 0) {
      return "옵션이 없습니다.";
    } else {
      return stringArray.join("/");
    }
  };

  render() {
    const { isCartOpen, handleCartClose } = this.props;
    const { tempData, isSignin, setLocalStorage, getLocalStorage, optionToString } = this;
    setLocalStorage();
    const localCart = getLocalStorage();

    return (
      <>
        {isSignin ? (
          // 로그인된 유저일 경우 DB에서 불러온 장바구니 정보를 보여준다.
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
                {tempData ? (
                  tempData.menus.map((m, index) => {
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
                  <li className="cart__empty">로딩 중입니다.</li>
                )}
              </ul>
              <div className="cart__bottom">
                <div className="cart__total">
                  <span className="cart__total-quantity">총 {tempData.size}개</span>
                  <span className="cart__total-price">
                    {numberCommaRegex(tempData.totalPrice)}원
                  </span>
                </div>
                <button className="button cart__button">결제 및 주문하기</button>
              </div>
            </div>
          </section>
        ) : (
          // 비회원 유저일 경우 로컬 스토리지에서 불러온 장바구니 정보를 보여준다.
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
                {localCart ? (
                  localCart.menus.map((m, index) => {
                    return (
                      <CartItem
                        key={index}
                        name={m.menuNameKo}
                        price={m.price}
                        option={typeof m.option !== "string" ? optionToString(m.option) : ""}
                        quantity={m.quantity}
                        imgUrl={m.imgUrl}
                      />
                    );
                  })
                ) : (
                  <li className="cart__empty">장바구니가 비었습니다.</li>
                )}
              </ul>
              <div className="cart__bottom">
                <div className="cart__total">
                  <span className="cart__total-quantity">
                    총 {localCart ? localCart.size : "0"}개
                  </span>
                  <span className="cart__total-price">
                    {localCart ? numberCommaRegex(localCart.totalPrice) : "0"}원
                  </span>
                </div>
                <button className="button cart__button">결제 및 주문하기</button>
              </div>
            </div>
          </section>
        )}
      </>
    );
  }
}
