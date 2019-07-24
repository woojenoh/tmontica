import { all } from "redux-saga/effects";
import userSagas from "./user";
import cartSagas from "./cart";

export default function* rootSaga() {
  yield all([userSagas(), cartSagas()]);
}
