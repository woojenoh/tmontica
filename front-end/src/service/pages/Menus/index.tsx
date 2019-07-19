import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import MenuItems from "../../components/MenuItems";
import { IMenuItemsProps } from "../../components/MenuItems";

interface MatchParams {
  categoryEng: string;
}

interface IMenusProps extends RouteComponentProps<MatchParams> {}

interface IMenusState {
  menuAll: Object;
}

const api = "https://my-json-server.typicode.com/yeolsa/tmontica-json";

export default class Menus extends React.Component<IMenusProps, IMenusState> {
  state = {
    menuAll: []
  };

  getMenuAll() {
    return fetch(`${api}/menuAll`, {
      headers: {
        Accept: "application/json"
      }
    }).then(res => (res.ok ? res.json() : new Error()));
  }

  componentDidMount() {
    this.getMenuAll().then(menuAll => {
      this.setState({
        menuAll
      });
    });
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
