import FindAccountForm from "./FindAccountForm";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import * as userActionCreators from "../../redux/actionCreators/user";
import * as rootTypes from "../../types/index";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isFindIdLoading: state.user.isFindIdLoading,
  isFindPasswordLoading: state.user.isFindPasswordLoading
});

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    fetchFindId: (payload: string) => dispatch(userActionCreators.fetchFindId(payload)),
    fetchFindIdConfirm: (payload: string) =>
      dispatch(userActionCreators.fetchFindIdConfirm(payload)),
    fetchFindPassword: (payload: { email: string; id: string }) =>
      dispatch(userActionCreators.fetchFindPassword(payload))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FindAccountForm);
