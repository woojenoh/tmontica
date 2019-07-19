package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserInfoReqDTO;
import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface UserDao {

    //CRUD
    @Insert("INSERT INTO users (id ,name ,email, birth_date, password, role) " +
            "VALUES(#{id}, #{name}, #{email}, #{birthDate}, #{password}, #{role})")
    int addUser(User user);
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserByUserId(String id);
    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    int updateUserPassword(User user);
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(String id);


}
