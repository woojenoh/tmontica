import * as React from "react";
import SignupForm from "../../components/SignupForm";
import "./styles.scss";

export interface ISignupProps {}

const Signup = React.memo((props: ISignupProps) => {
  return (
    <main className="main">
      <section className="signup">
        <div className="signup__logo-wrapper">
          <img src="/img/tmon-logo.png" alt="Tmon logo" className="signup__logo" />
        </div>
        <h1 className="signup__title">WELCOME</h1>
        <SignupForm />
      </section>
    </main>
  );
});

export default Signup;
