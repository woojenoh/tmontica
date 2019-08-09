import Header from "./Header";
import { connect } from "react-redux";
import * as rootTypes from "../../types/index";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

export default connect(mapStateToProps)(Header);
