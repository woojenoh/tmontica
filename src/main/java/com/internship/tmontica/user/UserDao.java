package com.internship.tmontica.user;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Mapper
public interface UserDao {

    //CRUD
    @Insert("INSERT INTO users (id ,name ,email, birth_date, password, role, activate_code) " +
            "VALUES(#{id}, #{name}, #{email}, #{birthDate}, #{password}, #{role}, #{activateCode})")
    int addUser(User user);
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserByUserId(String id);
    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    int updateUserPassword(User user);
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(String id);
    // 가입시 User Mail 중복검사 안해서 메일이 중복되는경우 문제생길수 있음.
    @Select("SELECT * FROM users WHERE email = #{email}")
    List<User> getUserByEmail(String email);
    @Select("SELECT id FROM users where id = #{id}")
    String getUserIdByUserId(String id);
    @Update("UPDATE users SET is_active = #{isActive}")
    int updateActivateStatus(int isActive);
    @PutMapping("UPDATE users SET point = #{point}")
    int updateUserPoint(int point);
}
