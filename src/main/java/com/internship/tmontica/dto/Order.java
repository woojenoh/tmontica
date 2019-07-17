package com.internship.tmontica.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Alias("orders")
@Data
public class Order {
    private int id;             // 주문번호(서버생성)
    private Date order_date;    // 주문날짜
    private String payment;     // 결제수단
    private int total_price;    // 주문 전체 가격
    private int used_point;     // 사용한 포인트
    private int real_price;     // 실제 지불해야할 금액
    private String status;      // 주문상태(코드)
    private String user_id;     // 주문한 유저의 아이디
    private String user_agent;  // 주문자의 기기환경?(모바일/웹)

}
