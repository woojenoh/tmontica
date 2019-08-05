import * as React from "react";
import "./styles.scss";

export interface IUserInfoPasswordFormProps {
  handleIsPasswordTrueChange(e: React.FormEvent<HTMLFormElement>): void;
}

export interface IUserInfoPasswordFormState {}

export default class UserInfoPasswordForm extends React.Component<
  IUserInfoPasswordFormProps,
  IUserInfoPasswordFormState
> {
  render() {
    const { handleIsPasswordTrueChange } = this.props;

    return (
      <form className="user-info-password__form" onSubmit={e => handleIsPasswordTrueChange(e)}>
        <input
          type="password"
          name="password"
          className="input user-info-password__input"
          placeholder="비밀번호"
          required
        />
        <input
          type="submit"
          className="button button--orange user-info-password__button"
          value="비밀번호 인증"
        />
      </form>
    );
  }
}
