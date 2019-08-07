import UserInfoForm from "./UserInfoForm";
import { connect } from "react-redux";
import * as rootTypes from "../../types/index";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  user: state.user.user
});

export default connect(mapStateToProps)(UserInfoForm);
