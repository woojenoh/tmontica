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
  isIdOk: boolean;
  isPasswordOk: boolean;
  isPasswordSame: boolean;
}

export interface InputState {
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
    birthday: "",
    isIdOk: false,
    isPasswordOk: false,
    isPasswordSame: false
  };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState(
      {
        [e.currentTarget.name]: e.currentTarget.value
      } as { [K in keyof InputState]: InputState[K] },
      () => {
        const { id, password, passwordConfirm } = this.state;

        // 아이디 정규식 검사
        if (/^[a-z0-9]{6,19}$/.test(id)) {
          this.setState({
            isIdOk: true
          });
        } else {
          this.setState({
            isIdOk: false
          });
        }

        // 패스워드 정규식, 일치 검사
        if (/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,19}/.test(password)) {
          this.setState({
            isPasswordOk: true
          });
          if (password === passwordConfirm) {
            this.setState({
              isPasswordSame: true
            });
          } else {
            this.setState({
              isPasswordSame: false
            });
          }
        } else {
          this.setState({
            isPasswordOk: false
          });
        }
      }
    );
  };

  render() {
    const { isIdOk, isPasswordOk, isPasswordSame } = this.state;
    const { handleInputChange } = this;

    return (
      <form className="signup__form">
        <input
          type="text"
          name="id"
          className="input signup__input"
          placeholder="아이디"
          onChange={e => handleInputChange(e)}
          autoComplete="off"
          required
        />
        <span className={isIdOk ? "signup__label signup__label--green" : "signup__label"}>
          {isIdOk ? "사용 가능한 아이디입니다." : "6자 이상의 영문, 숫자만 사용 가능합니다."}
        </span>
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
        <span
          className={
            isPasswordOk && isPasswordSame ? "signup__label signup__label--green" : "signup__label"
          }
        >
          {isPasswordOk
            ? isPasswordSame
              ? "사용 가능한 비밀번호입니다."
              : "비밀번호가 일치하지 않습니다."
            : "6자 이상의 영문, 숫자, 특수문자를 사용하세요."}
        </span>
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
