import React, { Component } from "react";
import { Link } from "react-router-dom";
import Header from "../../components/Header";

interface Props {}
interface State {}

export default class AdminMenus extends Component<Props, State> {
  state = {};

  render() {
    return (
      <>
        <Header title="메뉴 관리"/>
        <div className="main-wrapper">
          <nav className="col-md-2 d-none d-md-block bg-light sidebar">
            <div className="sidebar-sticky">
              <ul className="nav flex-column">
                <li className="nav-item">
                  <Link to="#" className="nav-link">
                    <i className="fas fa-shopping-cart feather" />
                    주문 관리
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to="#" className="nav-link">
                    <i className="fas fa-coffee feather" />
                    메뉴 관리
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to="#" className="nav-link">
                    <i className="far fa-flag feather" />
                    배너 관리
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to="#" className="nav-link">
                    <i className="fas fa-user feather" />
                    사용자 관리
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to="#" className="nav-link">
                    <i className="far fa-chart-bar feather" />
                    통계
                  </Link>
                </li>
              </ul>
            </div>
          </nav>
          <main className="col-md-10">
            <section>
              <div className="content-head d-flex flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <button className="btn btn-primary" data-toggle="modal" data-target="#menuModal">
                  메뉴 추가
                </button>
                <button className="btn btn-primary">이달의 메뉴 보기</button>
              </div>
              <table className="content-table table table-striped table-sm">
                <thead>
                  <tr>
                    <th>
                      <input type="checkbox" aria-label="Checkbox for following text input" />
                    </th>
                    <th>미리보기</th>
                    <th>카테고리</th>
                    <th>메뉴명</th>
                    <th>설명</th>
                    <th>이달의 메뉴</th>
                    <th>상품가</th>
                    <th>할인율</th>
                    <th>판매가</th>
                    <th>재고</th>
                    <th>등록일</th>
                    <th>등록인</th>
                    <th>수정일</th>
                    <th>수정인</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td className="menu__td check">
                      <input type="checkbox" aria-label="Checkbox for following text input" />
                    </td>
                    <td className="menu__td preview" />
                    <td className="menu__td category" />
                    <td className="menu__td name" />
                    <td className="menu__td description" />
                    <td className="menu__td monthly" />
                    <td className="menu__td product-price" />
                    <td className="menu__td discount-rate" />
                    <td className="menu__td sales-price" />
                    <td className="menu__td quantity" />
                    <td className="menu__td create-date" />
                    <td className="menu__td creator" />
                    <td className="menu__td edit-date" />
                    <td className="menu__td editor" />
                  </tr>
                </tbody>
              </table>
            </section>

            <div className="modal fade">
              <div className="modal-dialog" role="document">
                <div className="modal-content">
                  <div className="input-group-wrap">
                    <div className="input-group name">
                      <div className="input-group-prepend">
                        <span className="input-group-text">메뉴명</span>
                      </div>
                      <input
                        type="text"
                        className="form-control"
                        id="basic-url"
                        aria-describedby="basic-addon3"
                        placeholder="메뉴명"
                      />
                    </div>
                    <div className="input-group en-name">
                      <div className="input-group-prepend">
                        <span className="input-group-text">영문명</span>
                      </div>
                      <input
                        type="text"
                        className="form-control"
                        id="basic-url"
                        aria-describedby="basic-addon3"
                        placeholder="영문명"
                      />
                    </div>
                    <div className="input-group category">
                      <div className="input-group-prepend">
                        <span className="input-group-text">카테고리</span>
                      </div>
                      <div className="input-group-append">
                        <button
                          className="btn btn-outline-secondary dropdown-toggle"
                          type="button"
                          data-toggle="dropdown"
                          aria-haspopup="true"
                          aria-expanded="false"
                        >
                          카테고리
                        </button>
                        <div className="dropdown-menu">
                          <Link className="dropdown-item" to="#">
                            음료
                          </Link>
                          <Link className="dropdown-item" to="#">
                            디저트
                          </Link>
                        </div>
                      </div>
                    </div>
                    <div className="input-group description">
                      <div className="input-group-prepend">
                        <span className="input-group-text">설명</span>
                      </div>
                      <textarea
                        className="form-control"
                        id="basic-url"
                        aria-describedby="basic-addon3"
                        placeholder="설명입니다."
                      />
                    </div>
                    <div className="input-group monthly">
                      <div className="input-group-prepend">
                        <span className="input-group-text">이달의 메뉴</span>
                      </div>
                      <div className="form-control">
                        <div className="input-group">
                          <input type="radio" aria-label="Radio button for following text input" />
                          <label className="choice yes">예</label>
                        </div>
                        <div className="input-group">
                          <input
                            type="radio"
                            aria-label="Radio button for following text input"
                            checked
                          />
                          <label className="choice no">아니오</label>
                        </div>
                      </div>
                    </div>
                    <div className="row">
                      <div className="input-group product-price half">
                        <div className="input-group-prepend">
                          <span className="input-group-text">상품가</span>
                        </div>
                        <input
                          type="text"
                          className="form-control"
                          id="basic-url"
                          aria-describedby="basic-addon3"
                          placeholder="0,000(원)"
                        />
                      </div>
                      <div className="input-group discount-rate half">
                        <div className="input-group-prepend">
                          <span className="input-group-text">할인율</span>
                        </div>
                        <input
                          type="text"
                          className="form-control"
                          id="basic-url"
                          aria-describedby="basic-addon3"
                          placeholder="00(%)"
                        />
                      </div>
                    </div>
                    <div className="row">
                      <div className="input-group sales-price half">
                        <div className="input-group-prepend">
                          <span className="input-group-text">판매가</span>
                        </div>
                        <input
                          type="text"
                          className="form-control"
                          id="basic-url"
                          aria-describedby="basic-addon3"
                          placeholder="0,000(원)"
                        />
                      </div>
                      <div className="input-group quantity half">
                        <div className="input-group-prepend">
                          <span className="input-group-text">재고</span>
                        </div>
                        <input
                          type="text"
                          className="form-control"
                          id="basic-url"
                          aria-describedby="basic-addon3"
                          placeholder="0(수량)"
                        />
                      </div>
                    </div>
                    <div className="input-group options">
                      <div className="input-group-prepend">
                        <span className="input-group-text">옵션</span>
                      </div>
                      <div className="form-control">
                        <div className="input-group align-items-center pr-1">
                          <input type="checkbox" className="option__checkbox mr-1" />
                          <label className="option-name m-0">HOT</label>
                        </div>
                        <div className="input-group align-items-center pr-1">
                          <input type="checkbox" className="option__checkbox mr-1" />
                          <label className="option-name m-0">ICE</label>
                        </div>
                        <div className="input-group align-items-center pr-1">
                          <input type="checkbox" className="option__checkbox mr-1" />
                          <label className="option-name m-0">샷추가</label>
                        </div>
                        <div className="input-group align-items-center">
                          <input type="checkbox" className="option__checkbox mr-1" />
                          <label className="option-name m-0">시럽추가</label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="left-wrap">
                    <div className="menu-image">
                      <img
                        src="https://dummyimage.com/600x400/ffffff/ff7300.png&text=tmontica"
                        alt="메뉴 이미지"
                      />
                    </div>
                    <button className="reg-image__button btn btn-warning">이미지 등록</button>
                    <input type="file" className="hide" hidden />
                  </div>
                </div>
                <div className="modal-footer">
                  <button type="button" className="reg-menu__button btn btn-primary">
                    등록
                  </button>
                  <button
                    type="button"
                    className="cancle-menu__button btn btn-danger"
                    data-dismiss="modal"
                  >
                    취소
                  </button>
                </div>
              </div>
            </div>
          </main>
        </div>
      </>
    );
  }
}
