import * as React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faShoppingCart, faBars } from "@fortawesome/free-solid-svg-icons";
import "./styles.scss";

export interface IHeaderProps {}

export interface IHeaderState {}

class Header extends React.Component<IHeaderProps, IHeaderState> {
  public render() {
    return (
      <header className="header">
        <div className="header__container">
          <img
            src={require("../../../assets/img/tmon-logo.png")}
            alt="Tmontica Logo"
            className="header__logo"
          />
          <ul className="header__items">
            <li className="header__item">
              <FontAwesomeIcon icon={faUser} size="2x" />
            </li>
            <li className="header__item">
              <FontAwesomeIcon icon={faShoppingCart} size="2x" />
            </li>
            <li className="header__item">
              <FontAwesomeIcon icon={faBars} size="2x" />
            </li>
          </ul>
        </div>
      </header>
    );
  }
}

export default Header;
