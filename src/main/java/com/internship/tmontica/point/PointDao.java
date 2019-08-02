package com.internship.tmontica.point;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface PointDao {

    @Insert("INSERT INTO points (user_id ,type ,amount, description) " +
            "VALUES(#{userId}, #{type}, #{amount}, #{description})")
    int addPoint(Point point);
}
