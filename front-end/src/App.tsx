import * as React from "react";
import { Switch, Route } from "react-router-dom";
import Menus from "./service/pages/Menus";

class App extends React.Component {
  public render() {
    const AdminRoutes = (
      <Switch>
        <Route exact path="/admin/orders" />
        <Route exact path="/admin/menus" />
        <Route exact path="/admin/users" />
        <Route exact path="/admin/banners" />
        <Route exact path="/admin/statistics" />
        <Route exact path="/" />
      </Switch>
    );

    const PrivateRoutes = (
      <Switch>
        <Route exact path="/payment" />
        <Route exact path="/orders" />
        <Route exact path="/user" />
      </Switch>
    );

    const PublicRoutes = (
      <Switch>
        <Route exact path="/categories/:category" component={Menus} />
        <Route exact path="/signin" />
        <Route exact path="/signup" />
        <Route exact path="/" component={Menus} />
      </Switch>
    );

    return [PublicRoutes, PrivateRoutes, AdminRoutes];
  }
}

export default App;
