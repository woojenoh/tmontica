import * as React from "react";
import "./styles.scss";
import Header from "../../components/Header";
import { RouteComponentProps } from "react-router-dom";
import { MenuItems } from "../../components/MenuItems";
import { IMenuItemsProps } from "../../components/MenuItems";
import { Menu } from "../../../tyeps";

interface MatchParams {
  categoryEng: string;
}

interface IMenusProps extends RouteComponentProps<MatchParams> {}

interface IMenusState {
  all: Array<any>;
  categoryKo: string;
  categoryEng: string;
  menus: Array<Menu>;
}

const api = "https://my-json-server.typicode.com/yeolsa/tmontica-json";

export default class Menus extends React.Component<IMenusProps> {
  state = {
    all: [],
    categoryKo: "",
    categoryEng: "",
    menus: []
  };

  getMenus(categoryEng?: string) {
    return fetch(`${api}/${categoryEng}`, {
      headers: {
        Accept: "application/json"
      }
    }).then(res => res.json());
  }

  componentDidMount() {
    const { categoryEng } = this.props.match.params;
    const requestUrl = categoryEng ? categoryEng : "menuAll";

    this.getMenus(requestUrl).then(res => {
      if (requestUrl === "menuAll") {
        this.setState({
          all: res
        });
      } else {
        this.setState({
          categoryKo: "음료",
          categoryEng,
          menus: res.menus
        });
      }
    });
  }

  render() {
    return (
      <>
        <Header />
        <main className="main">
          <section className="banner">
            {<img src="" alt="Banner" className="banner__img" />}
          </section>
          {this.state.all ? (
            this.state.all.map((menu: IMenuItemsProps, i: number) => (
              <MenuItems
                key={i}
                categoryKo={menu.categoryKo}
                categoryEng={menu.categoryEng}
                menus={menu.menus}
              />
            ))
          ) : (
            <MenuItems
              categoryKo={this.state.categoryKo}
              categoryEng={this.state.categoryEng}
              menus={this.state.menus}
            />
          )}
        </main>
        <footer className="footer">
          <div className="footer__container" />
        </footer>
      </>
    );
  }
}
