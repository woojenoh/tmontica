package com.internship.tmontica.point;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface PointDao {

    @Insert("INSERT INTO points (user_id ,type ,amount, description) " +
            "VALUES(#{userId}, #{type}, #{amount}, #{description})")
    int addPoint(Point point);
    @Select("SELECT * FROM points where user_id = #{userId")
    List<Point> getAllPointByUserId(String userId);
    @Delete("DELETE FROM points WHERE user_id = #{userId}")
    int withdrawPointLogByUserId(String userId); // 특정 유저의 변동내역 삭제
}
