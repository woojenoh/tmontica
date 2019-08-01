package com.internship.tmontica.point;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface PointDao {

    @Insert("INSERT INTO points (user_id ,type ,amount, description) " +
            "VALUES(#{userId}, #{type}, #{amount}, #{description})")
    int addPoint(Point point);
    @Select("SELECT * FROM points")
    List<Point> getAllPoint(); // 모든 포인트 변동 내역을 가져옴. 타입별 포인트 내역은 스트림으로 필터링 할 예정.
    @Delete("DELETE FROM points WHERE user_id = #{userId}")
    int withdrawPointLogByUserId(String userId); // 특정 유저의 변동내역 삭제
    //토큰받아서 포인트 만빼는게
}
