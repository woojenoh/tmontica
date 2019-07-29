import React, { Component } from "react";
import "../../../assets/scss/admin.scss";
import Header from "../../components/Header";
import * as userTypes from "../../../types/user";
import "./styles.scss";

interface Props {
  fetchSignin(payload: userTypes.IUserSigninInfo): void;
}
export interface State {
  id: string;
  password: string;
}

export default class AdminSignin extends Component<Props, State> {
  state = { id: "", password: "" };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      [e.currentTarget.name]: e.currentTarget.value
    } as { [K in keyof State]: State[K] });
  };

  handleSigninSubmit = (e: React.FormEvent<HTMLInputElement>) => {
    const { id, password } = this.state;
    const { fetchSignin } = this.props;

    e.preventDefault();

    fetchSignin({ id: id, password: password });
  };

  render() {
    const { handleInputChange, handleSigninSubmit } = this;
    return (
      <>
        <Header hide={true} />
        <main>
          <div id="login-form" className="card">
            <h3>티몽티카 관리자 로그인</h3>
            <div className="signin__logo-wrapper">
              <img src="/img/tmon-logo.png" alt="Tmon logo" className="signin__logo" />
            </div>
            <div className="inner">
              <div className="input-group">
                <input
                  type="text"
                  name="id"
                  className="form-control"
                  placeholder="아이디"
                  onChange={e => handleInputChange(e)}
                />
              </div>
              <div className="input-group">
                <input
                  type="password"
                  name="password"
                  className="form-control"
                  placeholder="비밀번호"
                  onChange={e => handleInputChange(e)}
                />
              </div>
              <input
                type="submit"
                onClick={e => handleSigninSubmit(e)}
                value="로그인"
                className="btn btn-primary btn-submit"
              />
            </div>
          </div>
        </main>
      </>
    );
  }
}
