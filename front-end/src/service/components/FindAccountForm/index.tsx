import * as React from "react";
import "./styles.scss";

export interface IFindAccountFormProps {}

export interface IFindAccountFormState {
  accountEmail: string;
  passwordId: string;
  passwordEmail: string;
}

class FindAccountForm extends React.Component<IFindAccountFormProps, IFindAccountFormState> {
  state = {
    accountEmail: "",
    passwordId: "",
    passwordEmail: ""
  };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      [e.currentTarget.name]: e.currentTarget.value
    } as { [K in keyof IFindAccountFormState]: IFindAccountFormState[K] });
  };

  render() {
    const { accountEmail, passwordId, passwordEmail } = this.state;
    const { handleInputChange } = this;

    return (
      <>
        <form className="find-id__form">
          <input
            type="text"
            name="accountEmail"
            className="input find-id__form__input"
            placeholder="이메일"
            onChange={e => handleInputChange(e)}
            value={accountEmail}
            required
          />
          <input
            type="submit"
            className="button button--orange find-id__button"
            value="아이디 찾기"
          />
        </form>
        <form className="find-password__form">
          <input
            type="text"
            name="passwordId"
            className="input find-password__input"
            placeholder="아이디"
            onChange={e => handleInputChange(e)}
            value={passwordId}
            required
          />
          <input
            type="text"
            name="passwordEmail"
            className="input find-password__input"
            placeholder="이메일"
            onChange={e => handleInputChange(e)}
            value={passwordEmail}
            required
          />
          <input
            type="submit"
            className="button button--orange find-password__button"
            value="비밀번호 찾기"
          />
        </form>
      </>
    );
  }
}

export default FindAccountForm;
