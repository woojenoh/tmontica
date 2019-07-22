package com.internship.tmontica.repository;

import com.internship.tmontica.user.User;
import org.apache.ibatis.annotations.*;

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
    // 가입시 User Mail 중복검사 안해서 메일이 중복되는경우 문제생길수 있음.
    @Select("SELECT * FROM users WHERE email = #{email}")
    User getUserByEmail(String email);
}
