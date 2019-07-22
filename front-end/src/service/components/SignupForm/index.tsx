import SignupForm from "./SignupForm";
import { connect } from "react-redux";
import * as userActionCreators from "../../../redux/actionCreators/user";
import * as userTypes from "../../../redux/types/user";
import { Dispatch } from "redux";

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    fetchSignup: (payload: userTypes.IUserSignupInfo) =>
      dispatch(userActionCreators.fetchSignup(payload))
  };
};

export default connect(
  null,
  mapDispatchToProps
)(SignupForm);
