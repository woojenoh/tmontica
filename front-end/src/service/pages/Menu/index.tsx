import React, { Component } from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import { MenuAPI } from "../../../API";
import _ from "underscore";
import { number } from "prop-types";

type TMenuOption = {
  id: number;
  type: string;
  name: string;
  price: number;
  quantity?: number;
};

type TMenu = {
  id: number;
  nameEng: string;
  nameKo: string;
  description: string;
  imgUrl: string;
  sellPrice: number;
  discountRate: number;
  category: string;
  stock: number;
  monthlyMenu: boolean;
  options: Array<TMenuOption>;
};
interface MatchParams {
  menuId: string;
}

interface IMenuProps extends RouteComponentProps<MatchParams> {}

interface IMenuState {
  menu: TMenu;
  totalPrice: number;
  quantity: number;
  selectedOptions: Array<TMenuOption>;
}

class MenuOption extends Component {
  render() {
    return "";
  }
}

function getOptionComponent(id: number) {
  switch (id) {
    case 1:
      return <div className="detail__hot detail__hot--active">HOT</div>;
    case 2:
      return <div className="detail__ice">ICE</div>;
    case 3:
      return <div className="option__size">사이즈 추가</div>;
    case 4:
      return (
        <>
          <span className="option__title">샷 추가</span>
          <div className="option__counter">
            <div className="counter__minus">-</div>
            <input type="number" name="shot" className="counter__number" value="0" />
            <div className="counter__plus">+</div>
          </div>
        </>
      );
    case 5:
      return (
        <>
          <span className="option__title">시럽 추가</span>
          <div className="option__counter">
            <div className="counter__minus">-</div>
            <input type="number" name="syrup" className="counter__number" value="0" />
            <div className="counter__plus">+</div>
          </div>
        </>
      );
  }
}

export default class Menu extends Component<IMenuProps, IMenuState> {
  state = {
    menu: {} as TMenu,
    totalPrice: 0,
    quantity: 1,
    selectedOptions: []
  };

  async getMenu() {
    const { menuId } = this.props.match.params;

    const menu = await MenuAPI.getMenuById(parseInt(menuId));

    this.setState({
      menu,
      totalPrice: menu.sellPrice
    });
  }

  componentDidMount() {
    this.getMenu();
  }

  getOptionFilteredBy(filterType: string, options: Array<TMenuOption>) {
    return _.chain(options)
      .filter((option: TMenuOption) => option.type === filterType)
      .map(option => getOptionComponent(option.id))
      .value();
  }

  render() {
    const menu = this.state.menu;

    const temperatureOption = this.getOptionFilteredBy("Temperature", menu.options);
    const sizeOption = this.getOptionFilteredBy("Size", menu.options);
    const shotOption = this.getOptionFilteredBy("Shot", menu.options);
    const syrupOption = this.getOptionFilteredBy("Syrup", menu.options);

    return (
      <>
        <main className="main">
          <section className="detail">
            <div className="detail__left">
              <img src={menu.imgUrl} alt={menu.nameKo} className="detail__img" />
            </div>
            <div className="detail__right">
              <h1 className="detail__title">{menu.nameKo}</h1>
              <div className="detail__sell-price">
                <span className="price-style">{Number(menu.sellPrice).toLocaleString()}</span>원
              </div>
              <ul className="detail__options">
                {temperatureOption.length > 0 ? (
                  <li className="detail__option">{temperatureOption}</li>
                ) : (
                  ""
                )}
                <li className="detail__option">
                  <span className="option__title">수량</span>
                  <div className="option__counter">
                    <div className="counter__minus">-</div>
                    <input type="number" name="quantity" className="counter__number" value="1" />
                    <div className="counter__plus">+</div>
                  </div>
                </li>
                {shotOption.length > 0 ? <li className="detail__option">{shotOption}</li> : ""}
                {syrupOption.length > 0 ? <li className="detail__option">{syrupOption}</li> : ""}
                {sizeOption.length > 0 ? <li className="detail__option">{sizeOption}</li> : ""}
              </ul>
              <div className="detail__prices">
                <span>총 상품금액</span>
                <span className="total-price-view">
                  <span className="total-price">
                    {Number(this.state.totalPrice).toLocaleString()}
                  </span>
                  원
                </span>
              </div>
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
