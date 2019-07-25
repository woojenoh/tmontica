import "./styles.scss";
import { connect } from "react-redux";
import * as rootTypes from "../../../types/index";
import Menu from "../../components/Menu";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

export default connect(mapStateToProps)(Menu);
