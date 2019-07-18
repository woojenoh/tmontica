package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    //CRUD
    public int addUser(UserSignUpReqDTO userSignUpReqDTO);
    public User getUserByUserId(String id);
    public String getPasswordByUserId(String id);
    public int updateUserPassword(User user);
    public int deleteUser(String id);

}
