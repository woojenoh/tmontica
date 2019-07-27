import * as React from "react";
import "./styles.scss";
import history from "../../../history";
import { RouteComponentProps } from "react-router-dom";
import { ICartMenu } from "../../../types/cart";
import { OrderAPI } from "../../../API";

interface MatchParams {
  categoryEng: string;
}

interface IPaymentProps extends RouteComponentProps<MatchParams> {}

interface IPaymentState {
  totalPrice: number;
  usedPoint: number;
  usablePoint: number;
  orderCarts: Array<ICartMenu>;
}

const OrderMenu = ({
  nameKo,
  imgUrl,
  quantity,
  price,
  option
}: {
  nameKo: string;
  imgUrl: string;
  quantity: number;
  price: number;
  option: string;
}) => {
  return (
    <li className="order__menu">
      <div className="order__menu-img">
        <img src={imgUrl} alt={nameKo} />
      </div>
      <div className="order__menu-description">
        <div className="order__menu-title-wrap">
          <h3 className="order__menu-title">{nameKo}</h3>
          <div className="order__menu-options">{option}</div>
        </div>
        <div className="order__menu-cnt-price-wrap">
          <div className="order__menu-cnt-wrap d-inline-b">
            <span className="order__menu-cnt">{quantity}</span>개
          </div>
          <div className="order__menu-price-wrap d-inline-b">
            <span className="order__menu-price">{Number(price).toLocaleString()}</span>원
          </div>
        </div>
      </div>
    </li>
  );
};

export default class Payment extends React.PureComponent<IPaymentProps, IPaymentState> {
  state = {
    totalPrice: 0,
    usedPoint: 0,
    usablePoint: 0,
    orderCarts: []
  };

  async order() {
    try {
      const orderId = await OrderAPI.order({
        menus: this.state.orderCarts.map((c: ICartMenu) => {
          return { cartId: typeof c.cartId === "undefined" ? 0 : c.cartId };
        }),
        usedPoint: this.state.usedPoint,
        totalPrice: this.state.totalPrice,
        payment: "현장결제"
      });
      history.push(`/orders?orderId=${orderId}`);
    } catch (err) {
      alert(err);
      history.push("/");
    }
  }

  componentDidMount() {
    const orderCarts = JSON.parse(localStorage.getItem("orderCarts") || "[]");

    if (!Array.isArray(orderCarts) || (Array.isArray(orderCarts) && orderCarts.length === 0)) {
      alert("주문 정보가 존재하지 않습니다.");
      history.push("/");
      return;
    }

    this.setState({
      orderCarts
    });
  }

  render() {
    const { orderCarts } = this.state;

    const orderPrice = orderCarts.reduce((prev, cur: ICartMenu) => prev + cur.price, 0);

    return (
      <>
        <main className="main">
          <div className="order__container">
            <section className="order card">
              <div className="order__top">
                <h2 className="order__title plr15">주문상품정보</h2>
              </div>
              <div className="order__main">
                <ul className="order__menus">
                  {orderCarts.map((oc: ICartMenu) => {
                    return (
                      <OrderMenu
                        key={oc.cartId}
                        nameKo={oc.nameKo}
                        quantity={oc.quantity}
                        imgUrl={oc.imgUrl}
                        price={oc.price}
                        option={oc.option}
                      />
                    );
                  })}
                </ul>
              </div>
            </section>
            <section className="payment card">
              <div className="payment__top">
                <h2 className="payment__title plr15">결제방법</h2>
              </div>
              <div className="payment__methods button--group">
                <div className="button button--green payment__method active">현장결제</div>
                <div className="button button--green payment__method">카드</div>
              </div>
              <div className="payment__points">
                <input
                  type="text"
                  name="point"
                  value={this.state.usedPoint}
                  onChange={e => {
                    let willUsedPoint = parseInt(e.currentTarget.value) || 0;

                    if (orderPrice - willUsedPoint < 0) {
                      alert("주문금액 보다 많이 입력할 수 없습니다.");
                      this.setState(
                        {
                          usedPoint: 0
                        },
                        () => {
                          this.setState({
                            totalPrice: orderPrice - this.state.usedPoint
                          });
                        }
                      );
                      return;
                    }
                    this.setState(
                      {
                        usedPoint: willUsedPoint
                      },
                      () => {
                        this.setState({
                          totalPrice: orderPrice - this.state.usedPoint
                        });
                      }
                    );
                  }}
                />
                <div>
                  사용가능한 포인트: <span>0</span>P
                </div>
              </div>
            </section>

            <section className="price card">
              <div className="d-flex">
                <div className="price--name">주문금액</div>
                <div className="price--value">{orderPrice.toLocaleString()}원</div>
              </div>
              <div className="d-flex">
                <div className="price--name">할인금액</div>
                <div className="price--value">{this.state.usedPoint.toLocaleString()}원</div>
              </div>
              <hr />
              <div className="d-flex">
                <div className="price--name">최종 결제금액</div>
                <div className="price--value">
                  {(this.state.totalPrice && this.state.totalPrice.toLocaleString()) ||
                    orderPrice.toLocaleString()}
                  원
                </div>
              </div>
              <div className="button--group">
                <div className="button--cancle button button--green">취소</div>
                <div
                  className="button--pay button button--green"
                  onClick={() => {
                    if (window.confirm("결제하시겠습니까?")) {
                      // TODO: 결제하기 API 호출
                      this.order();
                      // 내 주문 페이지로 이동
                    }
                  }}
                >
                  결제하기
                </div>
              </div>
            </section>
          </div>
        </main>
        <footer className="footer">
          <div className="footer__container" />
        </footer>
      </>
    );
  }
}
