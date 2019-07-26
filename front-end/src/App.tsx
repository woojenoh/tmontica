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
          <PrivateRoute exact path="/payment" component={Payment} />
          <PrivateRoute exact path="/orders" component={Orders} />
          <PrivateRoute exact path="/user" component={Menus} />
          <Route exact path="/menus/:menuId([0-9]+)" component={Menu} />
          <Route exact path="/menus/:categoryEng([a-zA-Z]+)" component={MenusSub} />
          <Route exact path="/admin/signin" component={Menus} />
          <Route exact path="/signin" component={Signin} />
          <Route exact path="/signup" component={Signup} />
          <Route exact path="/" component={Menus} />
          <Route path="*" component={Menus} />
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
