import * as React from "react";
import { Menu } from "../../../tyeps";
import "./styles.scss";

interface IMenuItemProps {
  categoryEng: string | undefined;
  menu: Menu;
}
export class MenuItem extends React.Component<IMenuItemProps> {
  render() {
    const { id, nameKo, imgUrl } = this.props.menu;

    return (
      <li
        className="menu__item"
        data-id={id}
        onClick={() => (window.location.href = `/menus/${this.props.categoryEng}/${id}`)}
      >
        <div className="menu__content">
          <span className="menu__name">{nameKo}</span>
          <span className="menu__buy">구매</span>
          <img src={imgUrl} alt={nameKo} className="menu__img" />
        </div>
      </li>
    );
  }
}
