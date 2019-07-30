package com.internship.tmontica.point;

import com.internship.tmontica.point.model.request.PointLogReqDTO;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.security.exception.UnauthorizedException;
import com.internship.tmontica.user.UserRole;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

//JWTSERVICE 관련 메소드들은 컨트롤러단에서 커트하는게 이득이니 컨트롤러로 가는게 좋을듯..?
//user는 이미 서비스에서 동작하고있으니 우선 통일하고 나중에 고려해서 리팩토링..
@Service
@RequiredArgsConstructor
public class PointService {

    private final PointDao pointDao;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public boolean postPointLog(PointLogReqDTO pointLogReqDTO){


        return true;
    }

    public boolean withdrawPointLogByUserId(String userId){

        checkAdminRole();
        return pointDao.withdrawPointLogByUserId(userId) > 0;
    }

    private void checkAdminRole(){

        if(!JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "role").equals(UserRole.ADMIN.getRole())){
            throw new UnauthorizedException();
        }
    }
}
