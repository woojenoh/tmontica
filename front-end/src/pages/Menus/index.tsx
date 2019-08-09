import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import { getMenuAll } from "../../api/menu";
import MenuItems from "../../components/MenusItems";
import { IMenuItemsProps } from "../../components/MenusItems";
import { CommonError } from "../../api/CommonError";
import { getBannerByUsePageEng } from "../../api/banner";
import { IBanner } from "../../types/banner";
import { BannerSlider } from "../../components/BannerSlider";
import { handleError } from "../../api/common";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import { signout } from "../../redux/actionCreators/user";
import { ISignoutFunction } from "../../types/user";

interface MatchParams {
  categoryEng: string;
}

interface IMenusProps extends RouteComponentProps<MatchParams>, ISignoutFunction {}

interface IMenusState {
  menuAll: Object;
  mainTopBanners: IBanner[];
}

class Menus extends React.PureComponent<IMenusProps, IMenusState> {
  state = {
    menuAll: [],
    mainTopBanners: [] as IBanner[]
  };

  async getMenuAll() {
    try {
      const menuAll = await getMenuAll();
      if (menuAll instanceof CommonError) {
        throw menuAll;
      }

      if (!menuAll) throw new Error("메뉴 정보가 없습니다.");
      this.setState({
        menuAll
      });
    } catch (error) {
      await handleError(error);
    }
  }

  // 메인 상단 배너 요청
  async getMainTopBanner() {
    try {
      const usePageEng = "main-top";
      const mainTopBanners = await getBannerByUsePageEng(usePageEng);
      if (mainTopBanners instanceof CommonError) {
        throw mainTopBanners;
      }

      this.setState({
        mainTopBanners
      });
    } catch (error) {
      if (error instanceof CommonError) {
        // error.alertMessage();
        console.dir(error);
      } else {
        console.dir(error);
      }
    }
  }

  componentDidMount() {
    this.getMenuAll();
    this.getMainTopBanner();
  }

  render() {
    const { mainTopBanners } = this.state;

    return (
      <>
        <main className="main">
          {mainTopBanners.length > 0 ? <BannerSlider banners={mainTopBanners} /> : ""}

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
)(Menus);
