import React, { lazy, Suspense } from "react";
import {
  Switch,
  Route,
  Redirect,
  withRouter,
  RouteComponentProps,
  RouteProps
} from "react-router-dom";
import { connect } from "react-redux";
import * as rootTypes from "./types/index";
import Header from "./components/Header";
import "./assets/scss/service.scss";
import "./assets/scss/reset.scss";

export interface IAppProps extends RouteComponentProps {
  isSignin: boolean;
}

class App extends React.PureComponent<IAppProps> {
  PrivateRoute = ({ component: Component, ...rest }: RouteProps) => {
    const { isSignin } = this.props;

    if (Component) {
      return (
        <Route
          {...rest}
          render={props =>
            isSignin ? (
              <Component {...props} />
            ) : (
              <Redirect
                to={{
                  pathname: "/signin",
                  state: { from: props.location }
                }}
              />
            )
          }
        />
      );
    } else {
      return null;
    }
  };

  PublicRoute = ({ component: Component, ...rest }: RouteProps) => {
    const { isSignin } = this.props;

    if (Component) {
      return (
        <Route
          {...rest}
          render={props =>
            // 로그인이 돼있는 상태에서 로그인 관련 페이지로 이동하면 리다이렉트한다.
            isSignin &&
            (rest.path === "/signin" || rest.path === "/signup" || rest.path === "/find") ? (
              <Redirect
                to={{
                  pathname: "/",
                  state: { from: props.location }
                }}
              />
            ) : (
              <Component {...props} />
            )
          }
        />
      );
    } else {
      return null;
    }
  };

  render() {
    const { PrivateRoute, PublicRoute } = this;
    const Menus = lazy(() => import("./pages/Menus"));
    const FindAccount = lazy(() => import("./pages/FindAccount"));
    const Signup = lazy(() => import("./pages/Signup"));
    const Signin = lazy(() => import("./pages/Signin"));
    const MenusSub = lazy(() => import("./pages/MenusSub"));
    const Menu = lazy(() => import("./pages/Menu"));
    const UserInfo = lazy(() => import("./pages/UserInfo"));
    const Orders = lazy(() => import("./pages/Orders"));
    const Payment = lazy(() => import("./pages/Payment"));

    return (
      <Suspense fallback={<div>로딩 중입니다.</div>}>
        <Header />
        <Switch>
          <PrivateRoute exact path="/payment" component={Payment} />
          <PrivateRoute exact path="/orders" component={Orders} />
          <PrivateRoute exact path="/user" component={UserInfo} />
          <PublicRoute exact path="/menus/:menuId([0-9]+)" component={Menu} />
          <PublicRoute exact path="/menus/:categoryEng([a-zA-Z]+)" component={MenusSub} />
          <PublicRoute exact path="/signin" component={Signin} />
          <PublicRoute exact path="/signup" component={Signup} />
          <PublicRoute exact path="/find" component={FindAccount} />
          <PublicRoute exact path="/" component={Menus} />
          <PublicRoute path="*" component={Menus} />
        </Switch>
      </Suspense>
    );
  }
}

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

export default connect(mapStateToProps)(withRouter(App));
