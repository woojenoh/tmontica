import * as React from "react";
import SigninForm from "../../components/SigninForm";
import "./styles.scss";

export interface ISigninProps {}

function Signin(props: ISigninProps) {
  return (
    <main className="main">
      <section className="signin">
        <div className="signin__logo-wrapper">
          <img src="/img/tmon-logo.png" alt="Tmon logo" className="signin__logo" />
        </div>
        <h1 className="signin__title">WELCOME</h1>
        <SigninForm />
      </section>
    </main>
  );
}

export default Signin;
