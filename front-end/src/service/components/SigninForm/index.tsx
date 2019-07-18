import * as React from "react";
import "./styles.scss";

export interface ISigninFormProps {}

export interface ISigninFormState {}

class SigninForm extends React.Component<ISigninFormProps, ISigninFormState> {
  public render() {
    return (
      <form className="signin__form">
        <input type="text" name="id" className="input signin__input" placeholder="아이디" />
        <input
          type="password"
          name="password"
          className="input signin__input"
          placeholder="비밀번호"
        />
        <span className="signin__find">아이디/비밀번호 찾기</span>
        <div className="signin__button-container">
          <button className="button signin__button">회원가입</button>
          <button type="submit" className="button button--orange signin__button">
            로그인
          </button>
        </div>
      </form>
    );
  }
}

export default SigninForm;
