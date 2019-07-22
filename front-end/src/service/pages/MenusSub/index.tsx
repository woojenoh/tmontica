import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import MenuItems from "../../components/MenusItems";
import { MenuAPI } from "../../../API";
import { TMenusItem } from "../../../types";

interface MatchParams {
  categoryEng: string;
}

interface IMenusSubProps extends RouteComponentProps<MatchParams> {
  categoryKo: string;
}

interface IMenusSubState {
  categoryKo: string;
  menus: Array<TMenusItem>;
}

export default class MenusSub extends React.Component<IMenusSubProps, IMenusSubState> {
  state = {
    categoryKo: "",
    menus: []
  };

  async getMenuByCateory(categoryEng?: string) {
    const categoryMenus = await MenuAPI.getMenuByCateory(this.props.match.params.categoryEng);
    const { categoryKo, menus } = categoryMenus;
    this.setState({
      categoryKo,
      menus
    });
  }

  componentDidMount() {
    this.getMenuByCateory();
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
            <MenuItems categoryKo={this.state.categoryKo} menus={this.state.menus} />
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
