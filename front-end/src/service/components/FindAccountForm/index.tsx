import FindAccountForm from "./FindAccountForm";
import { connect } from "react-redux";
import * as userActionCreators from "../../../redux/actionCreators/user";
import { Dispatch } from "redux";

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    fetchFindId: (payload: string) => dispatch(userActionCreators.fetchFindId(payload)),
    fetchFindPassword: (payload: { email: string; id: string }) =>
      dispatch(userActionCreators.fetchFindPassword(payload))
  };
};

export default connect(
  null,
  mapDispatchToProps
)(FindAccountForm);
