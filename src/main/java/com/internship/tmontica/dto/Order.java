package com.internship.tmontica.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Alias("order")
@Data
public class Order {
    private int id;            // 주문번호(서버생성)
    @NotNull
    private Date orderDate;    // 주문날짜
    @NotNull
    private String payment;    // 결제수단
    @NotNull
    private int totalPrice;    // 주문 전체 가격

    private int usedPoint;     // 사용한 포인트
    @NotNull
    private int realPrice;     // 실제 지불해야할 금액
    @NotNull
    private String status;     // 주문상태(코드)
    @NotNull
    private String userId;     // 주문한 유저의 아이디
    @NotNull
    private String userAgent;  // 주문자의 기기환경?(모바일/웹)


}
