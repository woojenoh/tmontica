import * as React from "react";
import { Link } from "react-router-dom";
import * as userTypes from "../../types/user";
import { numberCommaRegex } from "../../utils";
import "./styles.scss";

export interface INavigationProps {
  isNavOpen: boolean;
  isSignin: boolean;
  user: userTypes.IUser | null;
  handleNavClose(): void;
  signout(): void;
}

function Nav(props: INavigationProps) {
  const { isNavOpen, handleNavClose, isSignin, signout, user } = props;

  return (
    <section className={isNavOpen ? "nav" : "nav nav--close"}>
      <div
        className={isNavOpen ? "nav__dim" : "nav__dim nav__dim--close"}
        onClick={() => handleNavClose()}
      />
      <div className={isNavOpen ? "nav__content" : "nav__content nav__content--close"}>
        <h1 className="nav__title">
          {isSignin ? (
            user && `${user.name}님 안녕하세요!`
          ) : (
            <>
              <Link to="/signin">로그인</Link>
            </>
          )}
        </h1>
        <span className="nav__close" onClick={() => handleNavClose()}>
          &times;
        </span>
        {isSignin ? (
          <div className="nav__top">
            <div className="nav__top-point">{user && numberCommaRegex(user.point)}P</div>
            <div className="nav__top-logout" onClick={() => signout()}>
              로그아웃
            </div>
          </div>
        ) : (
          ""
        )}
        <ul className="nav__items">
          {isSignin ? (
            <li className="nav__item">
              <h2 className="nav__item-title">MY</h2>
              <Link to="/user" className="nav__item-link">
                내 정보
              </Link>
              <Link to="/orders" className="nav__item-link">
                내 주문
              </Link>
            </li>
          ) : (
            ""
          )}
          <li className="nav__item">
            <h2 className="nav__item-title">MENU</h2>
            <Link to="/categories/beverage" className="nav__item-link">
              음료
            </Link>
            <Link to="/categories/dessert" className="nav__item-link">
              디저트
            </Link>
          </li>
        </ul>
      </div>
    </section>
  );
}

export default Nav;
