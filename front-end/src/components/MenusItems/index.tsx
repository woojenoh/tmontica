import * as React from "react";
import { Link } from "react-router-dom";
import MenuItem from "../MenusItem";
import { TMenusItem } from "../../types/menu";
import { RouteComponentProps, withRouter } from "react-router-dom";
import "./styles.scss";

export interface IMenuItemsProps extends RouteComponentProps {
  categoryKo: string;
  categoryEng?: string;
  menus: Array<TMenusItem>;
}

class MenuItems extends React.Component<IMenuItemsProps> {
  render() {
    const { categoryKo, categoryEng, menus } = this.props;
    return (
      <section className="menu">
        <div className="menu__top">
          <h1 className="menu__title">{categoryKo}</h1>
          {categoryEng ? (
            <Link to={`/menus/${categoryEng}`} className="menu__link">
              <span className="menu__more">+</span>
            </Link>
          ) : (
            ""
          )}
        </div>
        <ul className="menu__items">
          {menus
            ? menus.map(menu => {
                return <MenuItem key={menu.id} categoryEng={categoryEng} menu={menu} />;
              })
            : ""}
        </ul>
      </section>
    );
  }
}

export default withRouter(MenuItems);
