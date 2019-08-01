import React, { PureComponent } from "react";
import "./styles.scss";

export interface IFindAccountFormProps {
  fetchFindId(payload: string): void;
  fetchFindPassword(payload: { email: string; id: string }): void;
  fetchFindIdConfirm(payload: string): void;
  isFindIdLoading: boolean;
  isFindPasswordLoading: boolean;
}

export interface IFindAccountFormState {
  idEmail: string;
  idCode: string;
  passwordId: string;
  passwordEmail: string;
}

class FindAccountForm extends PureComponent<IFindAccountFormProps, IFindAccountFormState> {
  state = {
    idEmail: "",
    idCode: "",
    passwordId: "",
    passwordEmail: ""
  };

  handleInputChange = (e: React.FormEvent<HTMLInputElement>) => {
    this.setState({
      [e.currentTarget.name]: e.currentTarget.value
    } as { [K in keyof IFindAccountFormState]: IFindAccountFormState[K] });
  };

  handleFindIdSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    const { fetchFindId } = this.props;
    const { idEmail } = this.state;
    e.preventDefault();
    fetchFindId(idEmail);
    this.setState({
      idEmail: ""
    });
  };

  handleFindIdConfirmSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    const { fetchFindIdConfirm } = this.props;
    const { idCode } = this.state;
    e.preventDefault();
    fetchFindIdConfirm(idCode);
    this.setState({
      idCode: ""
    });
  };

  handleFindPasswordSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    const { fetchFindPassword } = this.props;
    const { passwordEmail, passwordId } = this.state;
    e.preventDefault();
    fetchFindPassword({ email: passwordEmail, id: passwordId });
    this.setState({
      passwordEmail: "",
      passwordId: ""
    });
  };

  render() {
    const { idEmail, idCode, passwordId, passwordEmail } = this.state;
    const { isFindIdLoading, isFindPasswordLoading } = this.props;
    const {
      handleInputChange,
      handleFindIdSubmit,
      handleFindIdConfirmSubmit,
      handleFindPasswordSubmit
    } = this;

    return (
      <>
        <form className="find-id__form" onSubmit={e => handleFindIdSubmit(e)}>
          <input
            type="text"
            name="idEmail"
            className="input find-id__form__input"
            placeholder="이메일"
            onChange={e => handleInputChange(e)}
            value={idEmail}
            required
          />
          {isFindIdLoading ? (
            <button className="button button--orange find-id__button" disabled>
              <img src="/img/Loading.svg" alt="loading" />
            </button>
          ) : (
            <input
              type="submit"
              className="button button--orange find-id__button"
              value="아이디 찾기"
            />
          )}
        </form>
        <form className="find-id__code" onSubmit={e => handleFindIdConfirmSubmit(e)}>
          <input
            type="text"
            name="idCode"
            className="input find-id__code-input"
            placeholder="인증코드"
            onChange={e => handleInputChange(e)}
            value={idCode}
            required
          />
          <input type="submit" className="button find-id__code-button" />
        </form>
        <form className="find-password__form" onSubmit={e => handleFindPasswordSubmit(e)}>
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
          {isFindPasswordLoading ? (
            <button className="button button--orange find-password__button" disabled>
              <img src="/img/Loading.svg" alt="loading" />
            </button>
          ) : (
            <input
              type="submit"
              className="button button--orange find-password__button"
              value="비밀번호 찾기"
            />
          )}
        </form>
      </>
    );
  }
}

export default FindAccountForm;
