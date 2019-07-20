import React, { Component } from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import { MenuAPI } from "../../../API";

interface MatchParams {
  menuId: string;
}

interface IMenuProps extends RouteComponentProps<MatchParams> {}

interface IMenuState {
  menu: Object;
}

export default class Menu extends Component<IMenuProps, IMenuState> {
  state = {
    menu: {}
  };

  async getMenu() {
    const { menuId } = this.props.match.params;

    const menu = await MenuAPI.getMenuById(parseInt(menuId));

    this.setState({
      menu
    });
  }

  componentDidMount() {
    this.getMenu();
  }

  render() {
    return (
      <>
        <main className="main">
          <section className="detail">
            <div className="detail__left">
              <img src="/img/coffee-sample.png" alt="Coffee Sample" className="detail__img" />
            </div>
            <div className="detail__right">
              <h1 className="detail__title">아메리카노</h1>
              <ul className="detail__options">
                <li className="detail__option">
                  <div className="detail__hot detail__hot--active">HOT</div>
                  <div className="detail__ice">ICE</div>
                </li>
                <li className="detail__option">
                  <span className="option__title">수량</span>
                  <div className="option__counter">
                    <div className="counter__minus">-</div>
                    <input type="number" name="quantity" className="counter__number" value="1" />
                    <div className="counter__plus">+</div>
                  </div>
                </li>
                <li className="detail__option">
                  <span className="option__title">샷 추가</span>
                  <div className="option__counter">
                    <div className="counter__minus">-</div>
                    <input type="number" name="shot" className="counter__number" value="0" />
                    <div className="counter__plus">+</div>
                  </div>
                </li>
                <li className="detail__option">
                  <span className="option__title">시럽 추가</span>
                  <div className="option__counter">
                    <div className="counter__minus">-</div>
                    <input type="number" name="syrup" className="counter__number" value="0" />
                    <div className="counter__plus">+</div>
                  </div>
                </li>
                <li className="detail__option">
                  <div className="option__size">사이즈 추가</div>
                </li>
              </ul>
              <div className="detail__buttons">
                <button className="button detail__button">장바구니</button>
                <button className="button button--orange detail__button">구매하기</button>
              </div>
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
