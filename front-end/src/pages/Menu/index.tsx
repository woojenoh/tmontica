import "./styles.scss";
import { connect } from "react-redux";
import * as cartTypes from "../../types/cart";
import * as rootTypes from "../../types/index";
import * as cartActionCreators from "../../redux/actionCreators/cart";
import Menu from "../../components/Menu";
import { Dispatch } from "redux";
import { signout } from "../../redux/actionCreators/user";

const mapStateToProps = (state: rootTypes.IRootState) => ({
  isSignin: state.user.isSignin
});

const mapDispatchToProps = (dispatch: Dispatch) => {
  return {
    addLocalCart: (payload: cartTypes.ICartMenu) =>
      dispatch(cartActionCreators.addLocalCart(payload)),
    fetchAddCart: (payload: cartTypes.ICartMenu[]) => {
      dispatch(cartActionCreators.fetchAddCart(payload));
    },
    signout: () => dispatch(signout())
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Menu);
