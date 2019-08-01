import SignupForm from "./SignupForm";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import * as userActionCreators from "../../redux/actionCreators/user";
import * as userTypes from "../../types/user";
import * as rootTypes from "../../types";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignupLoading: state.user.isSignupLoading
});

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    fetchSignup: (payload: userTypes.IUserSignupInfo) =>
      dispatch(userActionCreators.fetchSignup(payload))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SignupForm);
