import * as React from "react";

export interface IAdminOrdersModalProps {}

function AdminOrdersModal(props: IAdminOrdersModalProps) {
  return (
    <div
      className="modal fade"
      id="orderModal"
      tabIndex={-1}
      role="dialog"
      aria-labelledby="orderModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog" role="document" style={{ top: "50px" }}>
        <div className="modal-content d-flex flex-column bg-white border-right-1 border-left-1 border-top-0 border-bottom-1 border-dark">
          <div id="order-detail" className="input-group-wrap">
            {/* <!-- 주문번호/주문인 --> */}
            <div className="row">
              <div className="input-group order-num half">
                <div className="input-group-prepend">
                  <span className="input-group-text border-left-0">주문번호</span>
                </div>
                <span
                  className="form-control border-right-0"
                  id="basic-url"
                  aria-describedby="basic-addon3"
                  placeholder="메뉴명"
                />
              </div>
              <div className="input-group orderer half">
                <div className="input-group-prepend">
                  <span className="input-group-text">주문인</span>
                </div>
                <span
                  className="form-control border-right-0"
                  id="basic-url"
                  aria-describedby="basic-addon3"
                  placeholder="메뉴명"
                />
              </div>
            </div>
            {/* <!-- 설명 --> */}
            <div className="row">
              <div className="input-group description mb-0">
                <div className="input-group-prepend">
                  <span className="input-group-text border-left-0 border-bottom-0">설명</span>
                </div>
              </div>
              <table className="table table-sm bg-white table-bordered">
                <thead className="thead-dark">
                  <tr>
                    <th>메뉴명</th>
                    <th>옵션</th>
                    <th>결제금액</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>아메리카노</td>
                    <td>HOT/시럽추가(1개)</td>
                    <td>1,300원</td>
                  </tr>
                </tbody>
                <tfoot>
                  <tr>
                    <td colSpan={3} className="" style={{ textAlign: "right" }}>
                      총 결제금액: <span className="total-price">1,300</span>원
                    </td>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
          <div className="">
            <h4 className="m-2">주문기록</h4>
            <table className="table table-sm bg-white table-bordered mb-0 border-bottom-0">
              <thead className="thead-dark">
                <tr>
                  <th>번호</th>
                  <th>주문상태</th>
                  <th>변경인</th>
                  <th>변경일</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>결제완료</td>
                  <td>티몽이</td>
                  <td>2019.07.15 11:13:02</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div className="modal-footer pl-0 pr-0 pb-0">
          <button className="close__button btn btn-primary pl-4 pr-4" data-dismiss="modal">
            닫기
          </button>
        </div>
      </div>
    </div>
  );
}

export default AdminOrdersModal;
