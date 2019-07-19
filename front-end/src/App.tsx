import * as React from "react";
import { Switch, Route, Redirect, withRouter, RouteComponentProps } from "react-router-dom";
import Header from "./service/components/Header";
import Menu from "./service/pages/Menu";
import Menus from "./service/pages/Menus";
import MenusSub from "./service/pages/MenusSub";

class App extends React.Component<RouteComponentProps> {
  // 임시로 만든 로그인 정보
  state = {
    isSignin: true,
    isAdmin: true
  };

  AdminRoute = ({ component: Component, ...rest }: any) => {
    const { isSignin, isAdmin } = this.state;

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

  PrivateRoute = ({ component: Component, ...rest }: any) => {
    const { isSignin } = this.state;

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

  render() {
    const { location } = this.props;
    const { AdminRoute, PrivateRoute } = this;

    return (
      <>
        {!/^\/admin/.test(location.pathname) ? <Header /> : ""}
        <Switch>
          <AdminRoute exact path="/admin/orders" component={Menus} />
          <AdminRoute exact path="/admin/menus" component={Menus} />
          <AdminRoute exact path="/admin/users" component={Menus} />
          <AdminRoute exact path="/admin/banners" component={Menus} />
          <AdminRoute exact path="/admin/statistics" component={Menus} />
          <AdminRoute exact path="/admin" component={Menus} />
          <PrivateRoute exact path="/payment" component={Menus} />
          <PrivateRoute exact path="/orders" component={Menus} />
          <PrivateRoute exact path="/user" component={Menus} />
          <Route exact path="/menus/:categoryEng/:menuId" component={Menu} />
          <Route exact path="/menus/:categoryEng" component={MenusSub} />
          <Route exact path="/admin/signin" component={Menus} />
          <Route exact path="/signin" component={Menus} />
          <Route exact path="/signup" component={Menus} />
          <Route exact path="/" component={Menus} />
          <Route path="*" component={Menus} />
        </Switch>
      </>
    );
  }
}

export default withRouter(App);
