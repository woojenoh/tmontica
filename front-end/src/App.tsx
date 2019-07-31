import * as React from "react";
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
import Menu from "./pages/Menu";
import Menus from "./pages/Menus";
import MenusSub from "./pages/MenusSub";
import Signin from "./pages/Signin";
import Signup from "./pages/Signup";
import Orders from "./pages/Orders";
import Payment from "./pages/Payment";
import FindAccount from "./pages/FindAccount";

export interface IAppProps extends RouteComponentProps {
  isSignin: boolean;
  isAdmin: boolean;
}

class App extends React.Component<IAppProps> {
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
            // 로그인이 돼있는 상태에서 로그인 페이지로 이동하면 리다이렉트한다.
            isSignin && (rest.path === "/signin" || rest.path === "/find") ? (
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
    const { location } = this.props;
    const { PrivateRoute, PublicRoute } = this;

    return (
      <>
        <Header />
        <Switch>
          <PrivateRoute exact path="/payment" component={Payment} />
          <PrivateRoute exact path="/orders" component={Orders} />
          <PrivateRoute exact path="/user" component={Menus} />
          <PublicRoute exact path="/menus/:menuId([0-9]+)" component={Menu} />
          <PublicRoute exact path="/menus/:categoryEng([a-zA-Z]+)" component={MenusSub} />
          <PublicRoute exact path="/signin" component={Signin} />
          <PublicRoute exact path="/signup" component={Signup} />
          <PublicRoute exact path="/find" component={FindAccount} />
          <PublicRoute exact path="/" component={Menus} />
          <PublicRoute path="*" component={Menus} />
        </Switch>
      </>
    );
  }
}

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin,
  isAdmin: state.user.isAdmin
});

export default connect(mapStateToProps)(withRouter(App));
