import * as React from "react";
import UserInfoPasswordForm from "../../components/UserInfoPasswordForm";
import UserInfoForm from "../../components/UserInfoForm";
import "./styles.scss";

export interface IUserInfoProps {}

export interface IUserInfoState {}

export default class UserInfo extends React.Component<IUserInfoProps, IUserInfoState> {
  state = {
    isPasswordTrue: false
  };

  handleIsPasswordTrueChange = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    this.setState({
      isPasswordTrue: true
    });
  };

  render() {
    const { isPasswordTrue } = this.state;
    const { handleIsPasswordTrueChange } = this;

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
            <UserInfoPasswordForm handleIsPasswordTrueChange={handleIsPasswordTrueChange} />
          )}
        </section>
      </main>
    );
  }
}
