import * as React from "react";
import { Link } from "react-router-dom";
import "./styles.scss";

export interface ISigninFormProps {}

export interface ISigninFormState {
  id: string;
  password: string;
}

class SigninForm extends React.Component<ISigninFormProps, ISigninFormState> {
  state = {
    id: "",
    password: ""
  };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      [e.currentTarget.name]: e.currentTarget.value
    } as { [K in keyof ISigninFormState]: ISigninFormState[K] });
  };

  render() {
    const { handleInputChange } = this;

    return (
      <form className="signin__form">
        <input
          type="text"
          name="id"
          className="input signin__input"
          placeholder="아이디"
          onChange={e => handleInputChange(e)}
          required
        />
        <input
          type="password"
          name="password"
          className="input signin__input"
          placeholder="비밀번호"
          onChange={e => handleInputChange(e)}
          required
        />
        <span className="signin__find">아이디/비밀번호 찾기</span>
        <div className="signin__button-container">
          <Link to="/signup" className="button signin__button">
            회원가입
          </Link>
          <button type="submit" className="button button--orange signin__button">
            로그인
          </button>
        </div>
      </form>
    );
  }
}

export default SigninForm;
