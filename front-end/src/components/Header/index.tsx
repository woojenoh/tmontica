import Header from "./Header";
import { connect } from "react-redux";
import * as rootTypes from "../../types/index";
import "../../assets/scss/service.scss";
import "../../assets/scss/reset.scss";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

export default connect(mapStateToProps)(Header);
