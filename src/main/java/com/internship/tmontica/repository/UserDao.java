package com.internship.tmontica.repository;

import com.internship.tmontica.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    //CRUD
    public int addUser(User user);
    public User getUserByUserId(String id);
    public String getPasswordByUserId(String id);
    public int updateUserPassword(User user);
    public int deleteUser(String id);

}
