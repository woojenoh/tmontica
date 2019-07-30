import React, { Component, MouseEvent } from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import { addCart } from "../../../api/cart";
import { getMenuById } from "../../../api/menu";
import _ from "underscore";
import { TCartAddReq, TCartId } from "../../../types/cart";
import { TMenuOption, TMenu, TMenuOptionMap } from "../../../types/menu";
import {
  createCartAddReq,
  goToSignin,
  optionToString,
  convertOptionMapToArray
} from "../../../utils";
import history from "../../../history";
import { ICartMenu } from "../../../types/cart";
import { CDN } from "../../../constants";
import MenuOption from "../MenuOptionList";

const getOptionById = (options: Array<TMenuOption>, id: number) => {
  return _.chain(options)
    .filter(option => option.id === id)
    .first()
    .value();
};

interface MatchParams {
  menuId: string;
}

interface IMenuProps extends RouteComponentProps<MatchParams> {
  isSignin: boolean;
  fetchAddCart(payload: ICartMenu[]): void;
  addLocalCart(payload: ICartMenu): void;
}

interface IMenuState {
  menu: TMenu;
  totalPrice: number;
  quantity: number;
  option: TMenuOptionMap;
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
            return cur.price > 0 ? cur.price * cur.quantity + prev : prev;
          }, 0)
        : 0;

    return (sellPrice + optionPrice) * (quantity || this.state.quantity);
  }

  // 바로구매
  handleDirectOrder() {
    if (!this.props.isSignin) {
      goToSignin();
      return;
    }

    if (window.confirm("구매하시겠습니까?")) {
      const cartAddReq = createCartAddReq({
        menuId: this.state.menu.id,
        quantity: this.state.quantity,
        option: this.state.option,
        direct: true
      });
      this.orderDirect(cartAddReq);
    }
  }

  getOrderPreparedCart({ direct, cartId }: { direct: boolean; cartId?: number }) {
    const { menu, quantity, totalPrice, option } = this.state;

    const optionString = optionToString(option);

    const orderPreparedCart = {
      menuId: menu.id,
      nameEng: menu.nameEng,
      nameKo: menu.nameKo,
      imgUrl: menu.imgUrl,
      quantity,
      price: totalPrice / quantity,
      option: optionString,
      optionArray: convertOptionMapToArray(option),
      direct
    } as ICartMenu;
    if (typeof cartId !== "undefined") {
      orderPreparedCart["cartId"] = cartId;
    }
    return orderPreparedCart;
  }

  // 바로구매
  async orderDirect(cartAddReq: TCartAddReq) {
    try {
      const data = await addCart<TCartId[]>([cartAddReq]);

      if (cartAddReq.direct) {
        localStorage.setItem("isDirect", "Y");
      } else {
        localStorage.setItem("isDirect", "N");
      }
      const orderPreparedCart = this.getOrderPreparedCart({
        direct: true,
        cartId: data[0].cartId
      });

      localStorage.setItem("orderCart", JSON.stringify([orderPreparedCart]));

      history.push("/payment");
    } catch (err) {
      alert(err);
      history.push("/");
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
    } else if (option.quantity > 0) {
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
    thisOption.quantity = 1;
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
    try {
      const { menuId } = this.props.match.params;

      const menu = await getMenuById<TMenu>(parseInt(menuId));
      this.setState(
        {
          menu
        },
        this.updateTotalPrice
      );
      if (menu.stock <= 0) {
        return Promise.reject("품절된 상품입니다.");
      }

      return Promise.resolve();
    } catch (err) {
      alert(err);
    }
  }

  componentDidMount() {
    this.getMenu().then(
      () => {
        // HOT/ICE 옵션 디폴트로 하나 선택
        const defaultSelectedTemperatureOption = Array.from(
          document.querySelectorAll(".temperature")
        )[0] as HTMLElement;
        if (typeof defaultSelectedTemperatureOption !== "undefined") {
          defaultSelectedTemperatureOption.click();
        }
      },
      errMsg => {
        alert(errMsg);
        history.push("/");
      }
    );
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
    const { menu } = this.state;

    return (
      <>
        <main className="main">
          <section className="detail">
            <div className="detail__left">
              <img src={`${CDN}${menu.imgUrl}`} alt={menu.nameKo} className="detail__img" />
            </div>
            <div className="detail__right">
              <div className="detail__right-container">
                <h1 className="detail__title">{menu.nameKo}</h1>
                <div className="detail__sell-price">
                  <span className="price-style">{Number(menu.sellPrice).toLocaleString()}</span>원
                </div>
                <ul className="detail__options">
                  {this.renderDetailOptions("Temperature", menu.option)}
                  <li key={9999} className="detail__option">
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
                        readOnly
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
                  <button
                    className="button detail__button"
                    onClick={e => {
                      this.props.isSignin
                        ? this.props.fetchAddCart([
                            this.getOrderPreparedCart({
                              direct: false
                            })
                          ])
                        : this.props.addLocalCart(
                            this.getOrderPreparedCart({
                              direct: false
                            })
                          );
                    }}
                  >
                    장바구니
                  </button>
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
