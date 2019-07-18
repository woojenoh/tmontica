import * as React from "react";
import "./styles.scss";
import { Link } from "react-router-dom";
import { identifier } from "@babel/types";
// import MenuItems from "../../components/MenuItems";

export interface IMenusProps {}

// 임시 헤더
const Header = () => (
  <header className="header">
    <div className="header__container">
      <img
        src={require("../../../assets/img/tmon-logo.png")}
        alt="Tmontica Logo"
        className="header__logo"
      />
      <ul className="header__items">
        <li className="header__item">
          <div className="header__user">user</div>
        </li>
        <li className="header__item">
          <div className="header__cart">cart</div>
        </li>
        <li className="header__item">
          <div className="header__hamburg">hamburg</div>
        </li>
      </ul>
    </div>
  </header>
);

type Menu = {
  id: number;
  name: string;
  img: string;
  category?: string;
};

interface IMenuItemsProps {
  category: string;
  menus: Array<Menu>;
}

interface IMenuItemProps {
  menu: Menu;
}

class MenuItems extends React.Component<IMenuItemsProps> {
  render() {
    const { category, menus } = this.props;
    return (
      <section className="menu">
        <div className="menu__top">
          <h1 className="menu__title">{category}</h1>
          <span className="menu__more">+</span>
        </div>
        <ul className="menu__items">
          {menus.map(menu => {
            return <MenuItem menu={menu} />;
          })}
        </ul>
      </section>
    );
  }
}

class MenuItem extends React.Component<IMenuItemProps> {
  render() {
    const { menu } = this.props;

    return (
      <li className="menu__item">
        <div className="menu__content">
          <span className="menu__name">아메리카노</span>
          <span className="menu__buy">구매</span>
          <img src={""} alt="Menu Image" className="menu__img" />
        </div>
      </li>
    );
  }
}

export default class Menus extends React.Component<IMenusProps> {
  public render() {
    const data = [
      {
        category: "이달의 메뉴",
        menus: [
          {
            id: 1,
            name: "아메리카노",
            img: "../../../assets/img/coffee-sample.png"
          },
          {
            id: 2,
            name: "아메리카노",
            img: "../../../assets/img/coffee-sample.png"
          },
          {
            id: 3,
            name: "갈릭퐁당브래드",
            img: "../../../assets/img/coffee-sample.png"
          },
          {
            id: 4,
            name: "갈릭퐁당브래드",
            img: "../../../assets/img/coffee-sample.png"
          }
        ]
      },
      {
        category: "음료",
        menus: []
      },
      {
        category: "이달의 메뉴",
        menus: []
      }
    ];

    return (
      <div>
        <Header />
        <main className="main">
          <section className="banner">
            {<img src="" alt="Banner" className="banner__img" />}
          </section>
          <MenuItems category={data[0].category} menus={data[0].menus} />
          <MenuItems category={data[1].category} menus={data[1].menus} />
          <MenuItems category={data[2].category} menus={data[2].menus} />
        </main>
        <footer className="footer">
          <div className="footer__container" />
        </footer>
      </div>
    );
  }
}
