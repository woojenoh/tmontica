import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import MenuItems from "../../components/MenuItems";
import { IMenuItemsProps } from "../../components/MenuItems";
import { Menu } from "../../../types";

interface MatchParams {
  categoryEng: string;
}

interface IMenusSubProps extends RouteComponentProps<MatchParams> {
  categoryKo: string;
}

interface IMenusSubState {
  menus: Array<Menu>;
}

const api = "https://my-json-server.typicode.com/yeolsa/tmontica-json";

export default class MenusSub extends React.Component<IMenusSubProps, IMenusSubState> {
  state = {
    menus: []
  };

  getMenus(categoryEng: string) {
    return fetch(`${api}/${categoryEng}`, {
      headers: {
        Accept: "application/json"
      }
    }).then(res => (res.ok ? res.json() : new Error()));
  }

  componentDidMount() {
    this.getMenus(this.props.match.params.categoryEng).then(ret => {
      this.setState({
        menus: ret.menus
      });
    });
  }

  render() {
    const { categoryKo } = this.props;

    return (
      <>
        <main className="main">
          <section className="banner">
            {<img src="" alt="Banner" className="banner__img" />}
          </section>

          {this.state.menus ? (
            <MenuItems categoryKo={categoryKo} menus={this.state.menus} />
          ) : (
            "Loading..."
          )}
        </main>
        <footer className="footer">
          <div className="footer__container" />
        </footer>
      </>
    );
  }
}
