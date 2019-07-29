import FindAccountForm from "./FindAccountForm";
import { connect } from "react-redux";
import * as userActionCreators from "../../../redux/actionCreators/user";
import { Dispatch } from "redux";

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    fetchFindId: (payload: string) => dispatch(userActionCreators.fetchFindId(payload))
  };
};

export default connect(
  null,
  mapDispatchToProps
)(FindAccountForm);
