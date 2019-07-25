import React, { PureComponent, MouseEvent } from "react";
import "./styles.scss";
import _ from "underscore";
import { TMenuOption } from "../../../types/menu";
import MenuCounter from "../MenuCounterOption";
import MenuRadioOption from "../MenuRadioOption";
import MenuToggleOption from "../MenuToggleOption";

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

export default class MenuOption extends PureComponent<IMenuOptionProps> {
  getOptionComponent(option: TMenuOption) {
    const { id } = option;

    switch (id) {
      case 1:
        return (
          <MenuRadioOption
            key={id}
            id={id}
            title="HOT"
            handleSelectableOption={this.props.handleSelectableOption}
            option={option}
          />
        );
      case 2:
        return (
          <MenuRadioOption
            key={id}
            id={id}
            title="ICE"
            handleSelectableOption={this.props.handleSelectableOption}
            option={option}
          />
        );
      case 3:
        return (
          <MenuCounter
            key={id}
            id={id}
            option={option}
            title="시럽 추가"
            handleCountableOptionClick={this.props.handleCountableOptionClick}
          />
        );
      case 4:
        return (
          <MenuCounter
            key={id}
            id={id}
            option={option}
            title="샷 추가"
            handleCountableOptionClick={this.props.handleCountableOptionClick}
          />
        );
      case 5:
        return (
          <MenuToggleOption
            key={id}
            id={id}
            title="사이즈 추가"
            option={option}
            handleSelectableOption={this.props.handleSelectableOption}
          />
        );
    }
  }

  render() {
    const option = this.props.option;

    return (
      <li key={1} className="detail__option">
        {_.chain(option)
          .map((option, i) => this.getOptionComponent(option))
          .value()}
      </li>
    );
  }
}
