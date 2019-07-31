import * as React from "react";
import { Link, withRouter, RouteComponentProps } from "react-router-dom";
import * as userTypes from "../../types/user";
import "./styles.scss";

export interface ISigninFormProps extends RouteComponentProps {
  fetchSignin(payload: userTypes.IUserSigninInfo): void;
  fetchSigninActive(payload: { id: string; token: string }): void;
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

  componentDidMount() {
    const { fetchSigninActive } = this.props;
    const search = this.props.location.search;
    const params = new URLSearchParams(search);
    const id = params.get("id");
    const token = params.get("token");
    if (id && token) {
      fetchSigninActive({ id: id, token: token });
    }
  }

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
        <Link to="/find" className="signin__find">
          아이디/비밀번호 찾기
        </Link>
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

export default withRouter(SigninForm);
