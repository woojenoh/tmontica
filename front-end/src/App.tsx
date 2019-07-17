import * as React from "react";
import { Switch, Route } from "react-router-dom";

class App extends React.Component {
  public render() {
    const AdminRoutes = (
      <Switch>
        <Route exact path="/admin/orders" />
        <Route exact path="/admin/menus" />
        <Route exact path="/admin/users" />
        <Route exact path="/admin/banners" />
        <Route exact path="/admin/statistics" />
        <Route path="/" />
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
        <Route exact path="/categories/:category" />
        <Route exact path="/signin" />
        <Route exact path="/signup" />
        <Route path="/" />
      </Switch>
    );

    return [PublicRoutes, PrivateRoutes, AdminRoutes];
  }
}

export default App;
