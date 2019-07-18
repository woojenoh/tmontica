import * as React from "react";
import "./styles.scss";
import Header from "../../components/Header";
import { Link } from "react-router-dom";
import { identifier } from "@babel/types";
// import MenuItems from "../../components/MenuItems";

export interface IMenusProps {}

type Menu = {
  id: number;
  nameKo: string;
  nameEng: string;
  imgUrl: string;
};

interface IMenuItemsProps {
  categoryKo: string;
  categoryEng: string;
  menus: Array<Menu>;
}

interface IMenuItemProps {
  menu: Menu;
}

class MenuItems extends React.Component<IMenuItemsProps> {
  render() {
    const { categoryKo, categoryEng, menus } = this.props;
    return (
      <section className="menu">
        <div className="menu__top">
          <h1 className="menu__title">{categoryKo}</h1>
          <Link to={`menus/${categoryEng}`}>
            <span className="menu__more">+</span>
          </Link>
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
    const { id, nameEng, nameKo, imgUrl } = this.props.menu;

    return (
      <li className="menu__item">
        <div className="menu__content">
          <span className="menu__name">{nameKo}</span>
          <span className="menu__buy">구매</span>
          <img src={imgUrl} alt="Menu Image" className="menu__img" />
        </div>
      </li>
    );
  }
}

export default class Menus extends React.Component<IMenusProps> {
  public render() {
    const data = [
      {
        categoryKo: "이달의 메뉴",
        categoryEng: "monthly",
        menus: [
          {
            id: 1,
            nameKo: "아메리카노",
            nameEng: "americano",
            imgUrl: "/img/coffee-sample.png"
          },
          {
            id: 2,
            nameKo: "아메리카노",
            nameEng: "americano",
            imgUrl: "/img/coffee-sample.png"
          },
          {
            id: 3,
            nameKo: "갈릭퐁당브래드",
            nameEng: "americano",
            imgUrl: "/img/coffee-sample.png"
          },
          {
            id: 4,
            nameKo: "갈릭퐁당브래드",
            nameEng: "americano",
            imgUrl: "/img/coffee-sample.png"
          }
        ]
      },
      {
        categoryKo: "커피/에스프레소",
        categoryEng: "coffee",
        menus: []
      },
      {
        categoryKo: "디저트",
        categoryEng: "dessert",
        menus: []
      }
    ];

    return (
      <>
        <Header />
        <main className="main">
          <section className="banner">
            {<img src="" alt="Banner" className="banner__img" />}
          </section>
          <MenuItems
            categoryKo={data[0].categoryKo}
            categoryEng={data[0].categoryEng}
            menus={data[0].menus}
          />
          <MenuItems
            categoryKo={data[1].categoryKo}
            categoryEng={data[1].categoryEng}
            menus={data[1].menus}
          />
          <MenuItems
            categoryKo={data[2].categoryKo}
            categoryEng={data[1].categoryEng}
            menus={data[2].menus}
          />
        </main>
        <footer className="footer">
          <div className="footer__container" />
        </footer>
      </>
    );
  }
}
