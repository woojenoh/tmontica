import * as React from "react";
import { NavLink } from "react-router-dom";
import "./styles.scss";

export interface INavigationProps {
  isNavOpen: boolean;
  handleNavClose(): void;
}

function Nav(props: INavigationProps) {
  const { isNavOpen, handleNavClose } = props;

  return (
    <section className={isNavOpen ? "nav" : "nav nav--close"}>
      <div
        className={isNavOpen ? "nav__dim" : "nav__dim nav__dim--close"}
        onClick={() => handleNavClose()}
      />
      <div className={isNavOpen ? "nav__content" : "nav__content nav__content--close"}>
        <h1 className="nav__title">노우제님 안녕하세요!</h1>
        <span className="nav__close" onClick={() => handleNavClose()}>
          &times;
        </span>
        <div className="nav__top">
          <div className="nav__top-point">1,000P</div>
          <div className="nav__top-logout">로그아웃</div>
        </div>
        <ul className="nav__items">
          <li className="nav__item">
            <h2 className="nav__item-title">MY</h2>
            <NavLink to="/user" className="nav__item-link">
              내 정보
            </NavLink>
            <NavLink to="/orders" className="nav__item-link">
              내 주문
            </NavLink>
          </li>
          <li className="nav__item">
            <h2 className="nav__item-title">MENU</h2>
            <NavLink to="/categories/beverage" className="nav__item-link">
              음료
            </NavLink>
            <NavLink to="/categories/dessert" className="nav__item-link">
              디저트
            </NavLink>
          </li>
        </ul>
      </div>
    </section>
  );
}

export default Nav;
