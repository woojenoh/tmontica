import SigninForm from "./SigninForm";
import { connect } from "react-redux";
import * as userActionCreators from "../../../redux/actionCreators/user";
import * as userTypes from "../../../types/user";
import { Dispatch } from "redux";

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    fetchSignin: (payload: userTypes.IUserSigninInfo) =>
      dispatch(userActionCreators.fetchSignin(payload))
  };
};

export default connect(
  null,
  mapDispatchToProps
)(SigninForm);
