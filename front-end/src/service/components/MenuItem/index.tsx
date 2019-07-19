import * as React from "react";
import { Menu } from "../../../types";
import "./styles.scss";
import { RouteComponentProps, withRouter } from "react-router";

interface IMenuItemProps extends RouteComponentProps {
  categoryEng: string | undefined;
  menu: Menu;
}
class MenuItem extends React.Component<IMenuItemProps> {
  render() {
    const { id, nameKo, imgUrl } = this.props.menu;

    return (
      <li
        className="menu__item"
        data-id={id}
        onClick={() => this.props.history.push(`/menus/${this.props.categoryEng}/${id}`)}
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

export default withRouter(MenuItem);
