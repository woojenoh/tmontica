package com.internship.tmontica.point;

import com.internship.tmontica.point.exception.PointException;
import com.internship.tmontica.point.exception.PointExceptionType;
import com.internship.tmontica.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointDao pointDao;
    private final UserDao userDao;

    @Transactional
    public void updateUserPoint(Point point){

        int finalPoint = getFinalPoint(point);
        if(finalPoint < 0){
            throw new PointException(PointExceptionType.POINT_LESS_THEN_ZERO_EXCEPTION);
        }

        if(userDao.updateUserPoint(finalPoint, point.getUserId()) < 1){
            throw new PointException(PointExceptionType.DATABASE_FAIL_EXCEPTION);
        }

       if(pointDao.addPoint(point) < 1){
            throw new PointException(PointExceptionType.DATABASE_FAIL_EXCEPTION);
        }
    }

    // 기존 포인트 + 변동 포인트 = 최종 포인트 구하는 메소드
    private int getFinalPoint(Point point){

        return userDao.getUserPointByUserId(point.getUserId()) + PointLogType.getRealAmount(point.getAmount(), point.getType());
    }
}
