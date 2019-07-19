package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    //CRUD
    int addUser(UserSignUpReqDTO userSignUpReqDTO);
    User getUserByUserId(String id);
    String getPasswordByUserId(String id);
    int updateUserPassword(User user);
    int deleteUser(String id);

}
