import Header from "./Header";
import { connect } from "react-redux";
import * as rootTypes from "../../../redux/types/index";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

export default connect(mapStateToProps)(Header);
