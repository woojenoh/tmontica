import CartItem from "./CartItem";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import * as cartActionCreators from "../../../redux/actionCreators/cart";

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    removeLocalCart: (payload: number) => dispatch(cartActionCreators.removeLocalCart(payload))
  };
};

export default connect(
  null,
  mapDispatchToProps
)(CartItem);
