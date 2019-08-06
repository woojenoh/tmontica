import * as React from "react";
import { TMenusItem } from "../../types/menu";
import "./styles.scss";
import { RouteComponentProps, withRouter } from "react-router";
import { BASE_URL } from "../../constants";

interface IMenuItemProps extends RouteComponentProps {
  categoryEng: string | undefined;
  menu: TMenusItem;
}
class MenuItem extends React.Component<IMenuItemProps> {
  render() {
    const { id, nameKo, imgUrl, stock } = this.props.menu;
    return stock > 0 ? (
      <li
        className="menu__item"
        data-id={id}
        onClick={() => this.props.history.push(`/menus/${id}`)}
      >
        <div className="menu__content">
          <span className="menu__name">{nameKo}</span>
          <span className="menu__buy">구매</span>
          <img src={`${BASE_URL}${imgUrl}`} alt={nameKo} className="menu__img" />
        </div>
      </li>
    ) : (
      ""
    );
  }
}

export default withRouter(MenuItem);
