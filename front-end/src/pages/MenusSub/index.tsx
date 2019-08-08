import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import MenuItems from "../../components/MenusItems";
import { getMenuByCateory } from "../../api/menu";
import { TMenuByCategory } from "../../types/menu";
import { CommonError } from "../../api/CommonError";
import { handleError } from "../../api/common";
import { Dispatch } from "redux";
import { signout } from "../../redux/actionCreators/user";
import { connect } from "react-redux";
import { ISignoutFunction } from "../../types/user";

interface MatchParams {
  categoryEng: string;
}

interface IMenusSubProps extends RouteComponentProps<MatchParams>, ISignoutFunction {
  categoryKo: string;
}

interface IMenusSubState extends TMenuByCategory {}

class MenusSub extends React.PureComponent<IMenusSubProps, IMenusSubState> {
  state = {
    categoryKo: "",
    menus: []
  };

  async getMenuByCateory() {
    try {
      const categoryMenus = await getMenuByCateory(this.props.match.params.categoryEng);

      if (typeof categoryMenus === "undefined" || categoryMenus === null) {
        throw new CommonError({ message: "메뉴 정보를 불러오지 못했습니다." });
      }
      if (categoryMenus instanceof CommonError) {
        throw categoryMenus;
      }

      const { categoryKo, menus } = categoryMenus;
      this.setState({
        categoryKo,
        menus
      });
    } catch (error) {
      await handleError(error);
    }
  }

  componentDidMount() {
    this.getMenuByCateory();
  }

  componentDidUpdate(prevProps: IMenusSubProps) {
    if (prevProps.location.pathname !== this.props.location.pathname) {
      this.getMenuByCateory();
    }
  }

  render() {
    return (
      <>
        <main className="main">
          {/* <section className="banner">
            {<img src="" alt="Banner" className="banner__img" />}
          </section> */}

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

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    signout: () => dispatch(signout())
  };
};

export default connect(
  null,
  mapDispatchToProps
)(MenusSub);
