import * as React from "react";
import axios, { AxiosError } from "axios";
import { API_URL } from "../../api/common";
import UserInfoPasswordForm from "../../components/UserInfoPasswordForm";
import UserInfoForm from "../../components/UserInfoForm";
import "./styles.scss";

export interface IUserInfoProps {}

export interface IUserInfoState {}

export default class UserInfo extends React.Component<IUserInfoProps, IUserInfoState> {
  state = {
    isPasswordTrue: false,
    password: ""
  };

  handlePasswordInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      password: e.currentTarget.value
    });
  };

  handleIsPasswordTrueChange = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    axios
      .post(
        `${API_URL}/users/checkpw`,
        {
          password: this.state.password
        },
        {
          headers: {
            Authorization: localStorage.getItem("jwt")
          }
        }
      )
      .then(() => {
        alert("인증되었습니다.");
        this.setState({
          isPasswordTrue: true
        });
      })
      .catch((err: AxiosError) => {
        if (err.response) {
          alert(err.response.data.message);
        } else {
          alert("네트워크 오류 발생.");
        }
      });
  };

  render() {
    const { isPasswordTrue, password } = this.state;
    const { handlePasswordInputChange, handleIsPasswordTrueChange } = this;

    return (
      <main className="main">
        <section className="user-info">
          <div className="user-info__logo-wrapper">
            <img src="/img/tmon-logo.png" alt="Tmon logo" className="user-info__logo" />
          </div>
          <h1 className="user-info__title">USER INFO</h1>
          {isPasswordTrue ? (
            <UserInfoForm />
          ) : (
            <UserInfoPasswordForm
              password={password}
              handlePasswordInputChange={handlePasswordInputChange}
              handleIsPasswordTrueChange={handleIsPasswordTrueChange}
            />
          )}
        </section>
      </main>
    );
  }
}
