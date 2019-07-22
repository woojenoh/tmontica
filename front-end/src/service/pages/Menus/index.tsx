import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import { MenuAPI } from "../../../API";
import MenuItems from "../../components/MenusItems";
import { IMenuItemsProps } from "../../components/MenusItems";

interface MatchParams {
  categoryEng: string;
}

interface IMenusProps extends RouteComponentProps<MatchParams> {}

interface IMenusState {
  menuAll: Object;
}

export default class Menus extends React.Component<IMenusProps, IMenusState> {
  state = {
    menuAll: []
  };

  async getMenuAll() {
    const menuAll = await MenuAPI.getMenuAll();
    this.setState({
      menuAll
    });
  }

  componentDidMount() {
    this.getMenuAll();
  }

  render() {
    return (
      <>
        <main className="main">
          <section className="banner">
            {<img src="" alt="Banner" className="banner__img" />}
          </section>

          {this.state.menuAll
            ? Array.from(this.state.menuAll).map((menu: IMenuItemsProps, i: number) => (
                <MenuItems
                  key={i}
                  categoryKo={menu.categoryKo}
                  categoryEng={menu.categoryEng}
                  menus={menu.menus}
                />
              ))
            : "Loading..."}
        </main>
        <footer className="footer">
          <div className="footer__container" />
        </footer>
      </>
    );
  }
}
