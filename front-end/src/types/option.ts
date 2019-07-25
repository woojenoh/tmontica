import { MouseEvent } from "react";
import { TMenuOption } from "./menu";

export interface ISelectableOptionEventProp {
  handleSelectableOption(
    event: MouseEvent<HTMLDivElement>,
    id: number,
    commonClassName?: string
  ): void;
}

export interface ICountableOptionEventProp {
  handleCountableOptionClick(isPlus: boolean, option: TMenuOption): void;
}

export interface IMenuOptionCommonProps {
  id: number;
  title: string;
  option: TMenuOption;
}
