import React, { PureComponent } from "react";
import { IMenuOptionCommonProps, ISelectableOptionEventProp } from "../../../types/option";

export interface IMenuRadioOptionProps extends ISelectableOptionEventProp, IMenuOptionCommonProps {}

export default class MenuRadioOption extends PureComponent<IMenuRadioOptionProps> {
  public render() {
    const { id, title, handleSelectableOption } = this.props;

    return (
      <div
        className={`detail__${title.toLowerCase()} temperature`}
        onClick={e => {
          handleSelectableOption(e, id, "temperature");
        }}
      >
        {title}
      </div>
    );
  }
}
