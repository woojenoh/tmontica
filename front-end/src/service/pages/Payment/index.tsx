import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";

interface MatchParams {
  categoryEng: string;
}

interface IPaymentProps extends RouteComponentProps<MatchParams> {}

interface IPaymentState {}

export default class Payment extends React.PureComponent<IPaymentProps, IPaymentState> {
  state = {};

  componentDidMount() {}

  render() {
    return (
      <>
        <main className="main">
          <section className="order card">
            <div className="order__top">
              <h2 className="order__title plr15">주문상품정보</h2>
            </div>
            <div className="order__main">
              <ul className="order__menus">
                <li className="order__menu">
                  <div className="order__menu-img">
                    <img src="../../common/coffee-sample.png" alt="아메리카노" />
                  </div>
                  <div className="order__menu-description">
                    <div className="order__menu-title-wrap">
                      <h3 className="order__menu-title">아메리카노</h3>
                      <div className="order__menu-options">
                        HOT / 샷추가(1개) / 시럽추가(1개) / 사이즈업
                      </div>
                    </div>
                    <div className="order__menu-cnt-price-wrap">
                      <div className="order__menu-cnt-wrap d-inline-b">
                        <span className="order__menu-cnt">1</span>개
                      </div>
                      <div className="order__menu-price-wrap d-inline-b">
                        <span className="order__menu-price">3,000</span>원
                      </div>
                    </div>
                  </div>
                </li>
                <li className="order__menu">
                  <div className="order__menu-img">
                    <img src="../../common/coffee-sample.png" alt="아메리카노" />
                  </div>
                  <div className="order__menu-description">
                    <div className="order__menu-title-wrap">
                      <h3 className="order__menu-title">아메리카노</h3>
                      <div className="order__menu-options">
                        HOT / 샷추가(1개) / 시럽추가(1개) / 사이즈업
                      </div>
                    </div>
                    <div className="order__menu-cnt-price-wrap">
                      <div className="order__menu-cnt-wrap d-inline-b">
                        <span className="order__menu-cnt">1</span>개
                      </div>
                      <div className="order__menu-price-wrap d-inline-b">
                        <span className="order__menu-price">3,000</span>원
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </section>
          <section className="payment card">
            <div className="payment__top">
              <h2 className="payment__title plr15">결제방법</h2>
            </div>
            <div className="payment__methods">
              <div className="button button--green payment__method">현장결제</div>
              <div className="button button--green payment__method">카드</div>
            </div>
            <div className="payment__points">
              <input type="text" name="point" />
              <div>
                사용가능한 포인트: <span>10</span>P
              </div>
            </div>
          </section>

          <section className="price card">
            <div className="d-flex">
              <div className="price--name">주문금액</div>
              <div className="price--value">6,000원</div>
            </div>
            <div className="d-flex">
              <div className="price--name">할인금액</div>
              <div className="price--value">1,000원</div>
            </div>
            <hr />
            <div className="d-flex">
              <div className="price--name">최종 결제금액</div>
              <div className="price--value">5,000원</div>
            </div>
            <div className="button--group">
              <div className="button--cancle button button--green">취소</div>
              <div className="button--pay button button--green">결제하기</div>
            </div>
          </section>
        </main>
        <footer className="footer">
          <div className="footer__container" />
        </footer>
      </>
    );
  }
}
