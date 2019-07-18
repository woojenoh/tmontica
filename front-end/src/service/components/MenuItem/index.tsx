import * as React from "react";
import { Menu } from "../../../tyeps";

interface IMenuItemProps {
  menu: Menu;
}
export class MenuItem extends React.Component<IMenuItemProps> {
  render() {
    const { id, nameEng, nameKo, imgUrl } = this.props.menu;

    return (
      <li className="menu__item">
        <div className="menu__content">
          <span className="menu__name">{nameKo}</span>
          <span className="menu__buy">구매</span>
          <img src={imgUrl} alt="Menu Image" className="menu__img" />
        </div>
      </li>
    );
  }
}
