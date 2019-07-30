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
import Header from "./service/components/Header";
import Menu from "./service/pages/Menu";
import Menus from "./service/pages/Menus";
import MenusSub from "./service/pages/MenusSub";
import Signin from "./service/pages/Signin";
import Signup from "./service/pages/Signup";
import Orders from "./service/pages/Orders";
import Payment from "./service/pages/Payment";
import FindAccount from "./service/pages/FindAccount";
import AdminSignin from "./admin/pages/Signin";
import AdminMenus from "./admin/pages/AdminMenus";

export interface IAppProps extends RouteComponentProps {
  isSignin: boolean;
  isAdmin: boolean;
}

class App extends React.Component<IAppProps> {
  AdminRoute = ({ component: Component, ...rest }: RouteProps) => {
    const { isSignin, isAdmin } = this.props;

    if (Component) {
      return (
        <Route
          {...rest}
          render={props =>
            isSignin && isAdmin ? (
              <Component {...props} />
            ) : (
              <Redirect
                to={{
                  pathname: "/admin/signin",
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

  Swicher = () => {
    const { AdminRoute, PrivateRoute, PublicRoute } = this;
    return (
      <Switch>
        <AdminRoute exact path="/admin/orders" component={Menus} />
        <AdminRoute exact path="/admin/menus" component={AdminMenus} />
        <AdminRoute exact path="/admin/menus/:menuId([0-9]+)" component={AdminMenus} />
        <AdminRoute exact path="/admin/users" component={Menus} />
        <AdminRoute exact path="/admin/banners" component={Menus} />
        <AdminRoute exact path="/admin/statistics" component={Menus} />
        <AdminRoute exact path="/admin" component={AdminMenus} />
        <PrivateRoute exact path="/payment" component={Payment} />
        <PrivateRoute exact path="/orders" component={Orders} />
        <PrivateRoute exact path="/user" component={Menus} />
        <PublicRoute exact path="/menus/:menuId([0-9]+)" component={Menu} />
        <PublicRoute exact path="/menus/:categoryEng([a-zA-Z]+)" component={MenusSub} />
        <PublicRoute exact path="/admin/signin" component={AdminSignin} />
        <PublicRoute exact path="/signin" component={Signin} />
        <PublicRoute exact path="/signup" component={Signup} />
        <PublicRoute exact path="/find" component={FindAccount} />
        <PublicRoute exact path="/" component={Menus} />
        <PublicRoute path="*" component={Menus} />
      </Switch>
    );
  };

  render() {
    const { location } = this.props;

    return (
      <>
        {!/^\/admin/.test(location.pathname) ? (
          <>
            <Header />
            <this.Swicher />
          </>
        ) : (
          <div id="admin">
            <this.Swicher />
          </div>
        )}
      </>
    );
  }
}

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin,
  isAdmin: state.user.isAdmin
});

export default connect(mapStateToProps)(withRouter(App));
