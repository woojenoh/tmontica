package com.internship.tmontica.repository;

import com.internship.tmontica.dto.CartMenu;
import com.internship.tmontica.dto.Order;
import com.internship.tmontica.dto.OrderDetail;
import com.internship.tmontica.dto.OrderStatusLog;
import com.internship.tmontica.dto.response.MenusResp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderDao {

    // userId로 주문 정보 가져오기
    @Select("select * from orders where user_id = #{userId}")
    List<Order> getOrderByUserId(String userId);

    // orderId로 주문 정보 가져오기
    @Select("select * from orders where id = #{orderId}")
    Order getOrderByOrderId(String orderId);

    // orderId로 주문상세 정보 가져오기 (메뉴 이름 추가)
    @Select("select A.menu_id, B.name_eng, B.name_ko, A.option, A.quantity, A.price " +
            "from order_details as A inner join menus as B " +
            "   on A.menu_id = B.id " +
            "where A.order_id = #{orderId}")
    List<MenusResp> getOrderDetailByOrderId(String orderId);

    // orderId로 주문의 메뉴 이름들만 가져오기
    @Select("select name_ko " +
            "from menus inner join order_details " +
            "   on menus.id=order_details.menu_id " +
            "where order_details.order_id = #{orderId}")
    List<String> getMenuNamesByOrderId(String orderId);

    // orderId로 주문상태를 주문취소로 바꾸기
    @Update("update orders set status=\"주문취소\" where id=#{orderId}")
    void deleteOrder(String orderId);

    // order_status_log 추가
    @Insert("insert into order_status_logs " +
            "values(0, #{statusType}, #{editorId}, #{orderId}, sysdate())")
    int addOrderStatusLog(OrderStatusLog orderStatusLog);

    // 주문테이블에 추가하기
    @Insert("insert into orders " +
            "values(#{id}, sysdate(), #{payment}, #{totalPrice}, #{usedPoint}, #{realPrice}, #{status}, #{userId}, #{userAgent})")
    int addOrder(Order order);

    // 주문 상세테이블에 추가하기
    @Insert("insert into order_details values(0, #{orderId}, #{option}, #{price}, #{quantity}, #{menuId})")
    int addOrderDetail(OrderDetail orderDetail);



}
