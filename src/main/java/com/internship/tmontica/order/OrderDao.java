package com.internship.tmontica.order;

import com.internship.tmontica.order.model.response.OrderStatusLogResp;
import com.internship.tmontica.order.model.response.Order_MenusResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDao {

    // userId로 주문 정보 가져오기
    @Select("select * from orders where user_id = #{userId}")
    List<Order> getOrderByUserId(String userId);

    // orderId로 주문 정보 가져오기
    @Select("select * from orders where id = #{orderId}")
    Order getOrderByOrderId(int orderId);

    // orderId로 주문상세 정보 가져오기 (메뉴 이름 추가)
    @Select("select A.menu_id, B.name_eng, B.name_ko, A.option, B.img_url, A.quantity, A.price " +
            "from order_details as A inner join menus as B " +
            "   on A.menu_id = B.id " +
            "where A.order_id = #{orderId}")
    List<Order_MenusResp> getOrderDetailByOrderId(int orderId);

    // orderId로 주문의 메뉴 이름들만 가져오기
    @Select("select name_ko " +
            "from menus inner join order_details " +
            "   on menus.id=order_details.menu_id " +
            "where order_details.order_id = #{orderId}")
    List<String> getMenuNamesByOrderId(int orderId);

    // orderId로 주문상태를 주문취소로 바꾸기
    @Update("update orders set status=#{status} where id=#{orderId}")
    void updateOrderStatus(int orderId, String status);

    // order_status_log 추가
    @Insert("insert into order_status_logs " +
            "values(0, #{status}, #{editorId}, #{orderId}, sysdate())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addOrderStatusLog(OrderStatusLog orderStatusLog);

    // 주문테이블에 추가하기
    @Insert("insert into orders " +
            "values(#{id}, sysdate(), #{payment}, #{totalPrice}, #{usedPoint}, #{realPrice}, #{status}, #{userId}, #{userAgent})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addOrder(Order order);

    // 주문 상세테이블에 추가하기
    @Insert("insert into order_details values(0, #{orderId}, #{option}, #{price}, #{quantity}, #{menuId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addOrderDetail(OrderDetail orderDetail);

    // orderId로 order_status_log 내역 가져오기
    @Select("select status, editor_id, modified_date from order_status_logs where order_id=#{orderId}")
    List<OrderStatusLogResp> getOrderStatusLogByOrderId(int orderId);

    // order Status로 주문정보 가져오기
    @Select("select * from orders where status = #{status}")
    List<Order> getOrderByStatus(String status);

}
