import React, { PureComponent } from "react";
import { IMenuOptionCommonProps, ISelectableOptionEventProp } from "../../../types/option";

export interface IMenuToggleOptionProps
  extends ISelectableOptionEventProp,
    IMenuOptionCommonProps {}

export default class MenuToggleOption extends PureComponent<IMenuToggleOptionProps> {
  public render() {
    const { id, title, handleSelectableOption } = this.props;

    return (
      <div
        className="option__size"
        onClick={e => {
          handleSelectableOption(e, id);
        }}
      >
        {title}
      </div>
    );
  }
}
