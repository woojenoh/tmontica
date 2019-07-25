import * as React from "react";
import { numberCommaRegex } from "../../../utils";
import "./styles.scss";

export interface ICartItemProps {
  id: number | undefined;
  name: string;
  price: number;
  option: string | Object;
  quantity: number;
  imgUrl: string;
  isSignin: boolean;
  removeLocalCart(payload: number): void;
  changeLocalCart(id: number, quantity: number): void;
  fetchRemoveCart(payload: number): void;
}

export interface ICartItemState {
  quantity: number;
}

class CartItem extends React.Component<ICartItemProps, ICartItemState> {
  state = {
    quantity: this.props.quantity
  };

  buildSelectOptions = () => {
    var arr = [];
    for (let i = 1; i <= 30; i++) {
      arr.push(
        <option key={i} value={i}>
          {i}개
        </option>
      );
    }
    return arr;
  };

  handleQuantityChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { id, isSignin, changeLocalCart } = this.props;
    if (isSignin) {
    } else {
      if (id) {
        changeLocalCart(id, Number(e.currentTarget.value));
      }
    }
    this.setState({
      quantity: Number(e.currentTarget.value)
    });
  };

  render() {
    const { quantity } = this.state;
    const {
      id,
      name,
      price,
      option,
      imgUrl,
      removeLocalCart,
      isSignin,
      fetchRemoveCart
    } = this.props;
    const { buildSelectOptions, handleQuantityChange } = this;

    return (
      <li className="cart__item">
        <img src={imgUrl} alt="Coffee Sample" className="cart__item-img" />
        <div className="cart__item-info">
          <span className="cart__item-name">
            {name} - {numberCommaRegex(price)}원
            <span
              className="cart__item-delete"
              onClick={() => (isSignin ? id && fetchRemoveCart(id) : id && removeLocalCart(id))}
            >
              &times;
            </span>
          </span>
          <span className="cart__item-option">{option}</span>
        </div>
        <div className="cart__item-quantity">
          <select
            className="cart__item-quantity-select"
            name="quantity"
            value={quantity}
            onChange={e => handleQuantityChange(e)}
          >
            {buildSelectOptions()}
          </select>
        </div>
      </li>
    );
  }
}

export default CartItem;
