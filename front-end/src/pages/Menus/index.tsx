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

interface MatchParams {
  categoryEng: string;
}

interface IMenusProps extends RouteComponentProps<MatchParams> {}

interface IMenusState {
  menuAll: Object;
  mainTopBanners: IBanner[];
}

export default class Menus extends React.Component<IMenusProps, IMenusState> {
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
      if (!error.status) {
        alert("네트워크 오류 발생");
        return;
      }

      error.alertMessage();
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
          <BannerSlider banners={mainTopBanners} />

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
