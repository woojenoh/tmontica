import * as React from "react";
import "./styles.scss";

export interface IUserInfoFormProps {}

export interface IUserInfoFormState {}

class UserInfoForm extends React.Component<IUserInfoFormProps, IUserInfoFormState> {
  render() {
    return (
      <form className="user-info__form">
        <input
          type="text"
          name="id"
          className="input user-info__input"
          value="123qwe"
          required
          disabled
        />
        <input
          type="text"
          name="name"
          className="input user-info__input"
          value="티모"
          required
          disabled
        />
        <input
          type="text"
          name="email"
          className="input user-info__input"
          value="woojenoh@gmail.com"
          required
          disabled
        />
        <input
          type="text"
          name="birthDate"
          className="input user-info__input"
          value="1994-02-12"
          required
          disabled
        />
        <input
          type="password"
          name="password"
          className="input user-info__input"
          placeholder="변경 비밀번호"
          required
        />
        <input
          type="password"
          name="password"
          className="input user-info__input"
          placeholder="변경 비밀번호 확인"
          required
        />
        <input
          type="submit"
          className="button button--orange user-info__button"
          value="회원정보 저장"
        />
      </form>
    );
  }
}

export default UserInfoForm;
