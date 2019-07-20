import * as React from "react";
import { numberCommaRegex } from "../../../utils";
import "./styles.scss";

export interface ICartItemProps {
  name: string;
  price: number;
  option: string | Object;
  quantity: number;
  imgUrl: string;
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
    // DB에 수량 갱신도 필요.
    // 갱신하고 장바구니 DB 다시 불러오는 것도 필요.
    // 장바구니 DB 다시 불러오지 않으려면 Redux에 있는 Cart 데이터 수정하는 함수 필요할듯?
    this.setState({
      quantity: Number(e.currentTarget.value)
      // option의 value가 string으로 들어가서 Number로 변환해줘야 하는듯.
    });
  };

  render() {
    const { quantity } = this.state;
    const { name, price, option, imgUrl } = this.props;
    const { buildSelectOptions, handleQuantityChange } = this;

    return (
      <li className="cart__item">
        <img src={imgUrl} alt="Coffee Sample" className="cart__item-img" />
        <div className="cart__item-info">
          <span className="cart__item-name">
            {name} - {numberCommaRegex(price)}원
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
