import Nav from "./Nav";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import * as userActionCreators from "../../../redux/actionCreators/user";
import * as rootTypes from "../../../types/index";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    signout: () => dispatch(userActionCreators.signout())
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Nav);
