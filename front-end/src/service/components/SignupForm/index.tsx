import * as React from "react";
import { Link } from "react-router-dom";
import "./styles.scss";

export interface ISignupFormProps {}

export interface ISignupFormState {
  id: string;
  password: string;
  passwordConfirm: string;
  name: string;
  email: string;
  birthday: string;
}

class SignupForm extends React.Component<ISignupFormProps, ISignupFormState> {
  state = {
    id: "",
    password: "",
    passwordConfirm: "",
    name: "",
    email: "",
    birthday: ""
  };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      [e.currentTarget.name]: e.currentTarget.value
    } as { [K in keyof ISignupFormState]: ISignupFormState[K] });
  };

  render() {
    const { handleInputChange } = this;

    return (
      <form className="signup__form">
        <input
          type="text"
          name="id"
          className="input signup__input"
          placeholder="아이디"
          onChange={e => handleInputChange(e)}
          required
        />
        <input
          type="password"
          name="password"
          className="input signup__input"
          placeholder="비밀번호"
          onChange={e => handleInputChange(e)}
          required
        />
        <input
          type="password"
          name="passwordConfirm"
          className="input signup__input"
          placeholder="비밀번호 확인"
          onChange={e => handleInputChange(e)}
          required
        />
        <input
          type="text"
          name="name"
          className="input signup__input"
          placeholder="이름"
          onChange={e => handleInputChange(e)}
          required
        />
        <input
          type="text"
          name="email"
          className="input signup__input"
          placeholder="이메일"
          onChange={e => handleInputChange(e)}
          required
        />
        <div className="signup__birthday">
          <span className="signup__birthday-label">생일</span>
          <input
            type="date"
            name="birthday"
            className="input signup__input signup__birthday-input"
            onChange={e => handleInputChange(e)}
            required
          />
        </div>
        <div className="signup__button-container">
          <Link to="/signin" className="button signup__button">
            로그인
          </Link>
          <button type="submit" className="button button--orange signup__button">
            회원가입
          </button>
        </div>
      </form>
    );
  }
}

export default SignupForm;
