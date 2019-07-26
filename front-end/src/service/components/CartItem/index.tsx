import CartItem from "./CartItem";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import * as cartActionCreators from "../../../redux/actionCreators/cart";
import * as rootTypes from "../../../types/index";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    removeLocalCart: (payload: number) => dispatch(cartActionCreators.removeLocalCart(payload)),
    changeLocalCart: (id: number, quantity: number) =>
      dispatch(cartActionCreators.changeLocalCart(id, quantity))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CartItem);
