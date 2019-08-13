import * as React from "react";
import "./styles.scss";
import { RouteComponentProps } from "react-router-dom";
import MenuItems from "../../components/MenusItems";
import { getMenuByCateory } from "../../api/menu";
import { TMenuByCategory, TMenusItem } from "../../types/menu";
import { CommonError } from "../../api/CommonError";
import { handleError } from "../../api/common";
import { Dispatch } from "redux";
import { signout } from "../../redux/actionCreators/user";
import { connect } from "react-redux";
import { ISignoutFunction } from "../../types/user";
import InfiniteScroll from "react-infinite-scroller";

interface MatchParams {
  categoryEng: string;
}

interface IMenusSubProps extends RouteComponentProps<MatchParams>, ISignoutFunction {
  categoryKo: string;
}

interface IMenusSubState extends TMenuByCategory {
  page: number;
  hasMore: boolean;
}

class MenusSub extends React.PureComponent<IMenusSubProps, IMenusSubState> {
  state = {
    categoryKo: "",
    menus: null as TMenusItem[] | null,
    hasMore: false,
    page: 1
  } as IMenusSubState;

  async getMenuByCateory() {
    try {
      const size = 8;
      const categoryMenus = await getMenuByCateory(
        this.props.match.params.categoryEng,
        this.state.page,
        size
      );

      if (typeof categoryMenus === "undefined" || categoryMenus === null) {
        throw new CommonError({ message: "메뉴 정보를 불러오지 못했습니다." });
      }
      if (categoryMenus instanceof CommonError) {
        throw categoryMenus;
      }

      const { categoryKo, menus } = categoryMenus;

      this.setState({
        categoryKo,
        menus: this.state.menus !== null ? [...this.state.menus, ...menus] : [...menus],
        page: this.state.page + 1,
        hasMore: menus.length === size ? true : false
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
    const { menus } = this.state;

    return (
      <>
        <main className="main">
          {menus ? (
            <InfiniteScroll
              pageStart={1}
              loadMore={this.getMenuByCateory.bind(this)}
              hasMore={this.state.hasMore}
            >
              <MenuItems
                key={this.state.page}
                categoryKo={this.state.categoryKo}
                menus={this.state.menus}
              />
            </InfiniteScroll>
          ) : (
            "로딩 중입니다."
          )}
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
)(MenusSub);
