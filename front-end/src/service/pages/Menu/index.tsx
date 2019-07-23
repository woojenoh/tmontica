import React, { Component, PureComponent, MouseEvent } from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import { MenuAPI } from "../../../API";
import _ from "underscore";
import { mapProps } from "recompose";

const getOptionById = (options: Array<TMenuOption>, id: number) => {
  return _.filter(options, option => option.id === id);
};

type TMenuOption = {
  id: number;
  type?: string;
  name?: string;
  price: number | 0;
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
  option: Array<TMenuOption>;
};
interface MatchParams {
  menuId: string;
}

interface IMenuProps extends RouteComponentProps<MatchParams> {}

interface IMenuState {
  menu: TMenu;
  totalPrice: number;
  quantity: number;
  option: Array<TMenuOption>;
}

interface IMenuOptionProps {
  option: Array<TMenuOption>;
  handleSelectableOption(
    event: MouseEvent<HTMLDivElement>,
    id: number,
    commonClassName?: string
  ): void;
}

interface IMenuOptionState {
  id: number;
  name: string;
  price: number;
  amount: number;
}

interface ISelectableOptionProps {
  id: number;
  isSelected: boolean;
  handleSelectableOption: Function;
}

// class SelectableOption extends PureComponent<ISelectableOptionProps> {
//   render() {
//     const { handleSelectableOption } = this.props;

//     return (
//       <div className="detail__hot" onClick={e => handleSelectableOption(e)}>
//         HOT
//       </div>
//     );
//   }
// }

class MenuOption extends PureComponent<IMenuOptionProps, IMenuOptionState> {
  getOptionComponent(id: number, price: number) {
    switch (id) {
      case 1:
        return (
          <div
            className="detail__hot temperature"
            onClick={e => {
              this.props.handleSelectableOption(e, id, "temperature");
            }}
          >
            HOT
          </div>
        );
      case 2:
        return (
          <div
            className="detail__ice temperature"
            onClick={e => {
              this.props.handleSelectableOption(e, id, "temperature");
            }}
          >
            ICE
          </div>
        );
      case 3:
        return (
          <div
            className="option__size"
            onClick={e => {
              this.props.handleSelectableOption(e, id);
            }}
          >
            사이즈 추가
          </div>
        );
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

  render() {
    const option = this.props.option;

    return (
      <li className="detail__option">
        {_.chain(option)
          .map(option => this.getOptionComponent(option.id, option.price))
          .value()}
      </li>
    );
  }
}

export default class Menu extends Component<IMenuProps, IMenuState> {
  state = {
    menu: {} as TMenu,
    totalPrice: 0,
    quantity: 1,
    option: []
  };

  getTotalPrice(quantity: number) {
    const { sellPrice } = this.state.menu;

    const optionPrice =
      this.state.option.length > 0
        ? this.state.option.reduce((prev: number, cur: TMenuOption) => {
            return cur.quantity !== undefined ? cur.price * cur.quantity + prev : prev;
          }, 0)
        : 0;

    return sellPrice * quantity + optionPrice;
  }

  // getTotalPrice() {
  //   const price = this.calcTotalPrice();
  //   this.setState({
  //     totalPrice: price
  //   });
  // }

  handleQuantity(isPlus: boolean) {
    let { quantity } = this.state;
    if (isPlus) {
      quantity = quantity + 1;
    } else if (quantity > 1) {
      quantity = quantity - 1;
    } else {
      return;
    }

    let totalPrice = this.getTotalPrice(quantity);
    this.setState({
      totalPrice,
      quantity
    });
  }

  handleSelectableOption(e: MouseEvent, id: number, commonClassName?: string) {
    if (e.currentTarget.classList.contains("active") && typeof commonClassName === "undefined") {
      e.currentTarget.classList.remove("active");
      return;
    }

    const detailOptionEle = e.currentTarget.closest(`.detail__option`);
    if (detailOptionEle === null) {
      return;
    }
    const activatedEle = detailOptionEle.querySelector(`.${commonClassName}.active`);

    if (activatedEle !== null) {
      activatedEle.classList.remove("active");
    }
    e.currentTarget.classList.add("active");
    debugger;

    // this.getOptionById(id);
  }

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

  getOptionFilteredBy(filterType: string, option: Array<TMenuOption>) {
    return _.chain(option)
      .filter((option: TMenuOption) => option.type === filterType)
      .map(option => option)
      .value();
  }

  // 옵션 리스트를 그린다.
  renderDetailOptions(typeName: string, option: Array<TMenuOption>) {
    const filteredOptions = this.getOptionFilteredBy(typeName, option);

    return filteredOptions.length > 0 ? (
      <MenuOption option={filteredOptions} handleSelectableOption={this.handleSelectableOption} />
    ) : (
      ""
    );
  }

  render() {
    const menu = this.state.menu;

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
                {this.renderDetailOptions("Temperature", menu.option)}
                <li className="detail__option">
                  <span className="option__title">수량</span>
                  <div className="option__counter">
                    <div className="counter__minus" onClick={e => this.handleQuantity(false)}>
                      -
                    </div>
                    <input
                      type="number"
                      name="quantity"
                      className="counter__number"
                      value={this.state.quantity}
                    />
                    <div className="counter__plus" onClick={e => this.handleQuantity(true)}>
                      +
                    </div>
                  </div>
                </li>
                {this.renderDetailOptions("Shot", menu.option)}
                {this.renderDetailOptions("Syrup", menu.option)}
                {this.renderDetailOptions("Size", menu.option)}
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
