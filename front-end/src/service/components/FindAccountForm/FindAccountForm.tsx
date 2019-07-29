import * as React from "react";
import "./styles.scss";

export interface IFindAccountFormProps {
  fetchFindId(payload: string): void;
}

export interface IFindAccountFormState {
  idEmail: string;
  passwordId: string;
  passwordEmail: string;
}

class FindAccountForm extends React.Component<IFindAccountFormProps, IFindAccountFormState> {
  state = {
    idEmail: "",
    passwordId: "",
    passwordEmail: ""
  };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      [e.currentTarget.name]: e.currentTarget.value
    } as { [K in keyof IFindAccountFormState]: IFindAccountFormState[K] });
  };

  handleFindIdSubmit = () => {
    const { fetchFindId } = this.props;
    const { idEmail } = this.state;
    fetchFindId(idEmail);
    this.setState({
      idEmail: ""
    });
  };

  render() {
    const { idEmail, passwordId, passwordEmail } = this.state;
    const { handleInputChange, handleFindIdSubmit } = this;

    return (
      <>
        <form className="find-id__form">
          <input
            type="text"
            name="accountEmail"
            className="input find-id__form__input"
            placeholder="이메일"
            onChange={e => handleInputChange(e)}
            value={idEmail}
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
            onClick={() => handleFindIdSubmit()}
          />
        </form>
      </>
    );
  }
}

export default FindAccountForm;
