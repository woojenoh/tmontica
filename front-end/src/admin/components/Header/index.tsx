import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "./styles.scss";

interface Props {}
interface State {}

export default class Header extends Component<Props, State> {
  state = {};

  render() {
    return (
      <header className="bg-dark text-light">
        <h1>티몽티카 관리자</h1>
      </header>
    );
  }
}
