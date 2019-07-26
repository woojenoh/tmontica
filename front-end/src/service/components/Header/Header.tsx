import * as React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faShoppingCart, faBars, faReceipt } from "@fortawesome/free-solid-svg-icons";
import { withRouter, RouteComponentProps, Link } from "react-router-dom";
import Nav from "../Nav";
import Cart from "../Cart";
import "./styles.scss";

export interface IHeaderProps extends RouteComponentProps {
  isSignin: boolean;
}

export interface IHeaderState {
  isNavOpen: boolean;
  isCartOpen: boolean;
}

class Header extends React.Component<IHeaderProps, IHeaderState> {
  componentDidUpdate(prevProps: RouteComponentProps) {
    if (prevProps.location.pathname !== this.props.location.pathname) {
      const body = document.querySelector("body");
      if (body) {
        body.style.overflow = "auto";
      }
      this.setState({
        isNavOpen: false
      });
    }
  }

  state = {
    isNavOpen: false,
    isCartOpen: false
  };

  handleNavOpen = (): void => {
    const body = document.querySelector("body");
    if (body) {
      body.style.overflow = "hidden";
    }
    this.setState({
      isNavOpen: true
    });
  };

  handleNavClose = (): void => {
    const body = document.querySelector("body");
    if (body) {
      body.style.overflow = "auto";
    }
    this.setState({
      isNavOpen: false
    });
  };

  handleCartOpen = (): void => {
    const body = document.querySelector("body");
    if (body) {
      body.style.overflow = "hidden";
    }
    this.setState({
      isCartOpen: true
    });
  };

  handleCartClose = (): void => {
    const body = document.querySelector("body");
    if (body) {
      body.style.overflow = "auto";
    }
    this.setState({
      isCartOpen: false
    });
  };

  render() {
    const { isNavOpen, isCartOpen } = this.state;
    const { isSignin } = this.props;
    const { handleNavOpen, handleNavClose, handleCartOpen, handleCartClose } = this;

    return (
      <>
        <header className="header">
          <div className="header__container">
            <Link to="/">
              <img src="/img/tmon-logo.png" alt="Tmontica Logo" className="header__logo" />
            </Link>
            <ul className="header__items">
              <Link to={isSignin ? "/orders" : "/signin"} className="header__item">
                <FontAwesomeIcon icon={isSignin ? faReceipt : faUser} size="2x" />
              </Link>
              <li className="header__item" onClick={() => handleCartOpen()}>
                <FontAwesomeIcon icon={faShoppingCart} size="2x" />
              </li>
              <li className="header__item" onClick={() => handleNavOpen()}>
                <FontAwesomeIcon icon={faBars} size="2x" />
              </li>
            </ul>
          </div>
        </header>
        <Nav isNavOpen={isNavOpen} handleNavClose={handleNavClose} />
        <Cart isCartOpen={isCartOpen} handleCartClose={handleCartClose} />
      </>
    );
  }
}

export default withRouter(Header);
