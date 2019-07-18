import * as React from "react";
import { Link } from "react-router-dom";
import { MenuItem } from "../MenuItem";
import { Menu } from "../../../tyeps";

export interface IMenuItemsProps {
  categoryKo: string;
  categoryEng: string;
  menus: Array<Menu>;
}

export class MenuItems extends React.Component<IMenuItemsProps> {
  render() {
    const { categoryKo, categoryEng, menus } = this.props;
    return (
      <section className="menu">
        <div className="menu__top">
          <h1 className="menu__title">{categoryKo}</h1>
          <Link to={`/menus/${categoryEng}`}>
            <span className="menu__more">+</span>
          </Link>
        </div>
        <ul className="menu__items">
          {menus.map(menu => {
            return <MenuItem menu={menu} />;
          })}
        </ul>
      </section>
    );
  }
}
