import * as React from "react";
import { Link } from "react-router-dom";
import * as userTypes from "../../../redux/types/user";
import "./styles.scss";

export interface ISigninFormProps {
  fetchSignin(payload: userTypes.IUserSigninInfo): void;
}

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

  handleSigninSubmit = (e: React.FormEvent<HTMLInputElement>) => {
    const { id, password } = this.state;
    const { fetchSignin } = this.props;
    e.preventDefault();
    fetchSignin({ id: id, password: password });
  };

  render() {
    const { handleInputChange, handleSigninSubmit } = this;

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
          <input
            type="submit"
            className="button button--orange signin__button"
            value="로그인"
            onClick={e => handleSigninSubmit(e)}
          />
        </div>
      </form>
    );
  }
}

export default SigninForm;
