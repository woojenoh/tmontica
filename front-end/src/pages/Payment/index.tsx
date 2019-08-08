import * as React from "react";
import "./styles.scss";
import history from "../../history";
import { RouteComponentProps } from "react-router-dom";
import { ICartMenu } from "../../types/cart";
import { order } from "../../api/order";
import { BASE_URL } from "../../constants";
import { CommonError } from "../../api/CommonError";
import { handleError } from "../../api/common";
import { signout } from "../../redux/actionCreators/user";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import { ISignOut } from "../../types/user";
import { BaseSyntheticEvent } from "react";

interface MatchParams {
  categoryEng: string;
}

interface IPaymentProps extends RouteComponentProps<MatchParams>, ISignOut {}

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
        <img src={`${BASE_URL}${imgUrl}`} alt={nameKo} />
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
            <span className="order__menu-price">{Number(price * quantity).toLocaleString()}</span>원
          </div>
        </div>
      </div>
    </li>
  );
};

class Payment extends React.PureComponent<IPaymentProps, IPaymentState> {
  state = {
    totalPrice: 0,
    usedPoint: 0,
    usablePoint: 0,
    orderCarts: []
  };
  orderPrice: number = 0;

  async order() {
    try {
      const data = await order({
        menus: this.state.orderCarts.map((c: ICartMenu) => {
          return { cartId: typeof c.cartId === "undefined" ? 0 : c.cartId };
        }),
        usedPoint: this.state.usedPoint,
        totalPrice: this.state.totalPrice,
        payment: "현장결제"
      });
      if (data instanceof CommonError) {
        throw data;
      }
      const { orderId } = data;
      history.push(`/orders?orderId=${orderId}`);
    } catch (error) {
      const result = await handleError(error);
      if (result === "signout") {
        this.props.signout();
      }
      history.push("/");
    }
  }

  componentDidMount() {
    const orderCarts = JSON.parse(localStorage.getItem("orderCart") || "[]");

    if (!Array.isArray(orderCarts) || (Array.isArray(orderCarts) && orderCarts.length === 0)) {
      alert("주문 정보가 존재하지 않습니다.");
      history.push("/");
      return;
    }

    this.setState({
      orderCarts,
      totalPrice: this.getOrderPrice(orderCarts)
    });
  }

  getOrderPrice(orderCarts: Array<ICartMenu>) {
    return orderCarts.reduce((prev: number, cur: ICartMenu) => cur.price * cur.quantity + prev, 0);
  }

  handlePay = () => {
    if (window.confirm("결제하시겠습니까?")) {
      // TODO: 결제하기 API 호출
      this.order();
      // 내 주문 페이지로 이동
    }
  };

  handleCancle = (e: BaseSyntheticEvent) => {
    e.preventDefault();

    if (window.confirm("취소하시겠습니까?")) {
      localStorage.removeItem("orderCart");
      history.goBack();
    }
  };

  handlePoint = (e: BaseSyntheticEvent) => {
    let willUsedPoint = parseInt(e.currentTarget.value) || 0;

    this.orderPrice = this.getOrderPrice(this.state.orderCarts);

    if (this.orderPrice - willUsedPoint < 0) {
      alert("주문금액 보다 많이 입력할 수 없습니다.");
      this.setState(
        {
          usedPoint: 0
        },
        () => {
          this.setState({
            totalPrice: this.orderPrice - this.state.usedPoint
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
          totalPrice: this.orderPrice - this.state.usedPoint
        });
      }
    );
  };

  render() {
    const { orderCarts } = this.state;

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
                  onChange={this.handlePoint}
                />
                <div>
                  사용가능한 포인트: <span>0</span>P
                </div>
              </div>
            </section>

            <section className="price card">
              <div className="d-flex price-row">
                <div className="price--name">주문금액</div>
                <div className="price--value">{this.orderPrice.toLocaleString()}원</div>
              </div>
              <div className="d-flex price-row">
                <div className="price--name">할인금액</div>
                <div className="price--value">{this.state.usedPoint.toLocaleString()}원</div>
              </div>
              <div className="d-flex price-row">
                <div className="price--name">최종 결제금액</div>
                <div className="price--value">
                  {(this.state.totalPrice && this.state.totalPrice.toLocaleString()) ||
                    this.orderPrice.toLocaleString()}
                  원
                </div>
              </div>
              <div className="button--group">
                <div className="button--cancle button button--green" onClick={this.handleCancle}>
                  취소
                </div>
                <div className="button--pay button button--green" onClick={this.handlePay}>
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

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    signout: () => dispatch(signout())
  };
};

export default connect(
  null,
  mapDispatchToProps
)(Payment);
