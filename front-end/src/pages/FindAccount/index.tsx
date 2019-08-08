import * as React from "react";
import FindAccountForm from "../../components/FindAccountForm";
import "./styles.scss";

export interface IFindAccountProps {}

const FindAccount = React.memo((props: IFindAccountProps) => {
  return (
    <main className="main">
      <section className="find-account">
        <div className="find-account__logo-wrapper">
          <img src="/img/tmon-logo.png" alt="Tmon logo" className="find-account__logo" />
        </div>
        <h1 className="find-account__title">FIND ACCOUNT</h1>
        <FindAccountForm />
      </section>
    </main>
  );
});

export default FindAccount;
