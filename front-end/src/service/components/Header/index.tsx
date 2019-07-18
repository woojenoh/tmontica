import * as React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faShoppingCart, faBars } from "@fortawesome/free-solid-svg-icons";
import Nav from "../Nav";
import "./styles.scss";

export interface IHeaderProps {}

export interface IHeaderState {
  isNavOpen: boolean;
}

class Header extends React.Component<IHeaderProps, IHeaderState> {
  state = {
    isNavOpen: false
  };

  handleNavOpen = (): void => {
    this.setState({
      isNavOpen: true
    });
  };

  handleNavClose = (): void => {
    this.setState({
      isNavOpen: false
    });
  };

  render() {
    const { isNavOpen } = this.state;
    const { handleNavOpen, handleNavClose } = this;

    return (
      <>
        <header className="header">
          <div className="header__container">
            <img src="/img/tmon-logo.png" alt="Tmontica Logo" className="header__logo" />
            <ul className="header__items">
              <li className="header__item">
                <FontAwesomeIcon icon={faUser} size="2x" />
              </li>
              <li className="header__item">
                <FontAwesomeIcon icon={faShoppingCart} size="2x" />
              </li>
              <li className="header__item" onClick={() => handleNavOpen()}>
                <FontAwesomeIcon icon={faBars} size="2x" />
              </li>
            </ul>
          </div>
        </header>
        <Nav isNavOpen={isNavOpen} handleNavClose={handleNavClose} />
      </>
    );
  }
}

export default Header;
