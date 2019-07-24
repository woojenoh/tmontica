import React, { Component, PureComponent, MouseEvent } from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import { MenuAPI, CartAPI } from "../../../API";
import _ from "underscore";
import { TMenuOption, TMenu, TMenuOptionMap } from "../../../types";
import { createCartAddReq } from "../../../utils";

const getOptionById = (options: Array<TMenuOption>, id: number) => {
  return _.chain(options)
    .filter(option => option.id === id)
    .first()
    .value();
};

interface MatchParams {
  menuId: string;
}

interface IMenuProps extends RouteComponentProps<MatchParams> {}

interface IMenuState {
  menu: TMenu;
  totalPrice: number;
  quantity: number;
  option: TMenuOptionMap;
}

interface IMenuOptionProps {
  typeName: string;
  option: Array<TMenuOption>;
  handleSelectableOption(
    event: MouseEvent<HTMLDivElement>,
    id: number,
    commonClassName?: string
  ): void;
  handleCountableOptionClick(isPlus: boolean, option: TMenuOption): void;
}

class MenuOption extends PureComponent<IMenuOptionProps> {
  getOptionComponent(option: TMenuOption, key: number) {
    const { id } = option;

    switch (id) {
      case 1:
        return (
          <div
            key={key}
            className={`detail__hot temperature`}
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
            key={key}
            className={`detail__ice temperature`}
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
            key={key}
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
              <div
                className="counter__minus"
                onClick={e => this.props.handleCountableOptionClick(false, option)}
              >
                -
              </div>
              <input
                type="number"
                name="shot"
                className="counter__number"
                value={option.quantity}
              />
              <div
                className="counter__plus"
                onClick={e => this.props.handleCountableOptionClick(true, option)}
              >
                +
              </div>
            </div>
          </>
        );
      case 5:
        return (
          <>
            <span className="option__title">시럽 추가</span>
            <div className="option__counter">
              <div
                className="counter__minus"
                onClick={e => this.props.handleCountableOptionClick(false, option)}
              >
                -
              </div>
              <input
                type="number"
                name="syrup"
                className="counter__number"
                value={option.quantity}
              />
              <div
                className="counter__plus"
                onClick={e => this.props.handleCountableOptionClick(true, option)}
              >
                +
              </div>
            </div>
          </>
        );
    }
  }

  render() {
    const option = this.props.option;

    return (
      <li key={this.props.typeName} className="detail__option">
        {_.chain(option)
          .map((option, i) => this.getOptionComponent(option, i))
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
    option: new Map<string, TMenuOption>()
  };

  getTotalPrice(quantity?: number) {
    const { sellPrice } = this.state.menu;
    const option = this.state.option;

    const optionPrice =
      option.size > 0
        ? Array.from(option.values()).reduce((prev: number, cur: TMenuOption) => {
            return cur.price > 0 ? cur.price * (cur.quantity || 1) + prev : prev;
          }, 0)
        : 0;

    return (sellPrice + optionPrice) * (quantity || this.state.quantity);
  }

  // 바로구매
  handleDirectOrder() {
    if (window.confirm("구매하시겠습니까?")) {
      const cartAddReq = createCartAddReq({
        menuId: this.state.menu.id,
        quantity: this.state.quantity,
        option: this.state.option,
        direct: true
      });
      try {
        CartAPI.addCart(cartAddReq).next();
      } catch (error) {}
    }
  }

  handleQuantity(isPlus: boolean) {
    let { quantity } = this.state;
    if (isPlus) {
      quantity = quantity + 1;
    } else if (quantity > 1) {
      quantity = quantity - 1;
    } else {
      return;
    }

    this.setState(
      {
        quantity
      },
      this.updateTotalPrice
    );
  }

  // 수량 조절가능한 옵션 클릭
  handleCountableOptionClick(isPlus: boolean, option: TMenuOption) {
    const stateOption = this.state.option;

    if (isPlus) {
      option.quantity = option.quantity + 1;
    } else if (option.quantity > 1) {
      option.quantity = option.quantity - 1;
    } else {
      return;
    }
    stateOption.set(option.type, option);
    this.updateOptionAndTotalPrice(stateOption);
  }

  updateOptionAndTotalPrice(option: TMenuOptionMap) {
    this.setState(
      {
        option
      },
      this.updateTotalPrice
    );
  }

  // 선택지가 있는 옵션(HOT/ICE, 사이즈 추가)
  handleSelectableOption(e: MouseEvent, id: number, commonClassName?: string) {
    const thisOption: TMenuOption | undefined = getOptionById(this.state.menu.option, id);
    const stateOption = this.state.option;
    if (typeof thisOption === "undefined") {
      return;
    }

    // 단일 선택 옵션(예: SizeUp)인 경우는 선택 해제가 가능
    if (e.currentTarget.classList.contains("active") && typeof commonClassName === "undefined") {
      e.currentTarget.classList.remove("active");

      // 옵션을 제거하고 종료
      stateOption.delete(thisOption.type);

      this.setState(
        {
          option: stateOption
        },
        this.updateTotalPrice
      );
      return;
    }
    // 아래는 여러 선택지가 있는 옵션
    // 옵션을 현재 선택한 옵션으로 대체
    stateOption.set(thisOption.type, thisOption);
    this.updateOptionAndTotalPrice(stateOption);

    const detailOptionEle = e.currentTarget.closest(`.detail__option`);
    if (detailOptionEle === null) {
      return;
    }
    const activatedEle = detailOptionEle.querySelector(`.${commonClassName}.active`);

    if (activatedEle !== null) {
      activatedEle.classList.remove("active");
    }
    e.currentTarget.classList.add("active");
  }

  async getMenu() {
    const { menuId } = this.props.match.params;

    const menu = await MenuAPI.getMenuById(parseInt(menuId));

    this.setState(
      {
        menu
      },
      this.updateTotalPrice
    );
    return Promise.resolve();
  }

  componentDidMount() {
    this.getMenu().then(() => {
      // HOT/ICE 옵션 디폴트로 하나 선택
      const defaultSelectedTemperatureOption = Array.from(
        document.querySelectorAll(".temperature")
      )[0] as HTMLElement;
      if (typeof defaultSelectedTemperatureOption !== "undefined") {
        defaultSelectedTemperatureOption.click();
      }
    });
  }

  updateTotalPrice() {
    this.setState({
      totalPrice: this.getTotalPrice() // 가격 재계산
    });
  }

  componentDidUpdate() {}

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
      <MenuOption
        typeName={typeName}
        option={filteredOptions}
        handleSelectableOption={this.handleSelectableOption.bind(this)}
        handleCountableOptionClick={this.handleCountableOptionClick.bind(this)}
      />
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
                <li key="quantity" className="detail__option">
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
                <button
                  className="button button--orange detail__button"
                  onClick={e => {
                    e.preventDefault();
                    this.handleDirectOrder();
                  }}
                >
                  구매하기
                </button>
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
