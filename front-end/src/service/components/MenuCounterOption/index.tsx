import React, { Fragment, PureComponent } from "react";
import { ICountableOptionEventProp, IMenuOptionCommonProps } from "../../../types/option";

export interface IMenuCounterProps extends ICountableOptionEventProp, IMenuOptionCommonProps {}

export default class MenuCounter extends PureComponent<IMenuCounterProps> {
  render() {
    const { option, title, handleCountableOptionClick } = this.props;

    return (
      <Fragment>
        <span className="option__title">{title}</span>
        <div className="option__counter">
          <div className="counter__minus" onClick={e => handleCountableOptionClick(false, option)}>
            -
          </div>
          <input
            type="number"
            name={option.name}
            className="counter__number"
            value={option.quantity}
            readOnly
          />
          <div className="counter__plus" onClick={e => handleCountableOptionClick(true, option)}>
            +
          </div>
        </div>
      </Fragment>
    );
  }
}
