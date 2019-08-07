import * as React from "react";
import axios, { AxiosError } from "axios";
import history from "../../history";
import * as userTypes from "../../types/user";
import { API_URL } from "../../api/common";
import "./styles.scss";

export interface IUserInfoFormProps {
  user: userTypes.IUser | null;
}

export interface IUserInfoFormState {
  password: string;
  passwordCheck: string;
}

class UserInfoForm extends React.Component<IUserInfoFormProps, IUserInfoFormState> {
  state = {
    password: "",
    passwordCheck: ""
  };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      [e.currentTarget.name]: e.currentTarget.value
    } as { [K in keyof IUserInfoFormState]: IUserInfoFormState[K] });
  };

  handleInputSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    const { password, passwordCheck } = this.state;
    e.preventDefault();

    if (password === passwordCheck) {
      axios
        .put(
          `${API_URL}/users`,
          {
            password: password,
            passwordCheck: passwordCheck
          },
          {
            headers: {
              Authorization: localStorage.getItem("jwt")
            }
          }
        )
        .then(() => {
          alert("회원정보가 정상적으로 수정되었습니다.");
          history.push("/");
        })
        .catch((err: AxiosError) => {
          if (err.response) {
            alert(err.response.data.message);
          } else {
            alert("네트워크 오류 발생.");
          }
        });
    } else {
      alert("변경 비밀번호와 변경 비밀번호 확인은 일치해야 합니다.");
    }
  };

  render() {
    const { password, passwordCheck } = this.state;
    const { user } = this.props;
    const { handleInputChange, handleInputSubmit } = this;

    return (
      <>
        {user ? (
          <form className="user-info__form" onSubmit={e => handleInputSubmit(e)}>
            <input
              type="text"
              name="id"
              className="input user-info__input"
              value={user.id}
              required
              disabled
            />
            <input
              type="text"
              name="name"
              className="input user-info__input"
              value={user.name}
              required
              disabled
            />
            <input
              type="text"
              name="email"
              className="input user-info__input"
              value={user.email}
              required
              disabled
            />
            <input
              type="text"
              name="birthDate"
              className="input user-info__input"
              value={user.birthDate}
              required
              disabled
            />
            <input
              type="password"
              name="password"
              className="input user-info__input"
              placeholder="변경 비밀번호"
              value={password}
              onChange={e => handleInputChange(e)}
              required
            />
            <input
              type="password"
              name="passwordCheck"
              className="input user-info__input"
              placeholder="변경 비밀번호 확인"
              value={passwordCheck}
              onChange={e => handleInputChange(e)}
              required
            />
            <input
              type="submit"
              className="button button--orange user-info__button"
              value="회원정보 수정"
            />
          </form>
        ) : (
          ""
        )}
      </>
    );
  }
}

export default UserInfoForm;
