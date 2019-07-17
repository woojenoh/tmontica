package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {

    /** 한명 사용자의 주문정보 내역 가져오기 */
    public List<Order> getOrderByOrderId(String user_id);
}
