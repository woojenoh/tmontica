package com.internship.tmontica.point;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PointDao {

    public int addPoint(Point point);
    public List<Point> getAllPoint(); // 모든 포인트 변동 내역을 가져옴.
    public int deletePointById(int id); // 특정 내역 삭제
    public int deletePointByUserId(String userId); // 특정 유저의 내역 전부 삭제
}
