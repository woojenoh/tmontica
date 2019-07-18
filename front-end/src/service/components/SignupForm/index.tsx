import * as React from "react";
import "./styles.scss";

export interface ISignupFormProps {}

export interface ISignupFormState {}

class SignupForm extends React.Component<ISignupFormProps, ISignupFormState> {
  render() {
    return (
      <form className="signup__form">
        <input
          type="text"
          name="id"
          className="input signup__input"
          placeholder="아이디"
          required
        />
        <input
          type="password"
          name="password"
          className="input signup__input"
          placeholder="비밀번호"
          required
        />
        <input
          type="password"
          name="password-confirm"
          className="input signup__input"
          placeholder="비밀번호 확인"
          required
        />
        <input
          type="text"
          name="name"
          className="input signup__input"
          placeholder="이름"
          required
        />
        <input
          type="text"
          name="email"
          className="input signup__input"
          placeholder="이메일"
          required
        />
        <div className="signup__birthday">
          <span className="signup__birthday-label">생일</span>
          <input
            type="date"
            name="birthday"
            className="input signup__input signup__birthday-input"
            required
          />
        </div>
        <div className="signup__button-container">
          <button className="button signup__button">로그인</button>
          <button type="submit" className="button button--orange signup__button">
            회원가입
          </button>
        </div>
      </form>
    );
  }
}

export default SignupForm;
