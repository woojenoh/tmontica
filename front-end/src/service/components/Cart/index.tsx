import Cart from "./Cart";
import { connect } from "react-redux";
import { Dispatch } from "redux";
import * as cartActionCreators from "../../../redux/actionCreators/cart";
import * as rootTypes from "../../../types/index";
import * as cartTypes from "../../../types/cart";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin,
  localCart: state.cart.localCart
});

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    addLocalCart: (payload: cartTypes.ICartMenu) =>
      dispatch(cartActionCreators.addLocalCart(payload))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Cart);
