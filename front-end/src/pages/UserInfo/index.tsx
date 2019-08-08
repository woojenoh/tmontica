import * as React from "react";
import { handleError } from "../../api/common";
import UserInfoPasswordForm from "../../components/UserInfoPasswordForm";
import UserInfoForm from "../../components/UserInfoForm";
import "./styles.scss";
import { checkPassword } from "../../api/user";
import { CommonError } from "../../api/CommonError";

export interface IUserInfoProps {}

export interface IUserInfoState {}

export default class UserInfo extends React.PureComponent<IUserInfoProps, IUserInfoState> {
  state = {
    isPasswordTrue: false,
    password: ""
  };

  handlePasswordInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      password: e.currentTarget.value
    });
  };

  handleIsPasswordTrueChange = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const result = await checkPassword({
        password: this.state.password
      });
      if (result instanceof CommonError) throw result;

      alert("인증되었습니다.");
      this.setState({
        isPasswordTrue: true
      });
    } catch (error) {
      await handleError(error);
    }
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
