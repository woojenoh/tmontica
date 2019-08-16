package com.internship.tmontica.user;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    // Create
    @Insert("INSERT INTO users (id ,name ,email, birth_date, password, role, activate_code) " +
            "VALUES(#{id}, #{name}, #{email}, #{birthDate}, #{password}, #{role}, #{activateCode})")
    int addUser(User user);
    // Read
    @Select("SELECT id, name, email, birth_date, password, role, created_date, point, is_active, activate_code" +
            " FROM users WHERE id = #{id}")
    User getUserByUserId(String id);
    @Select("SELECT id, name, email, birth_date, password, role, created_date, point, is_active, activate_code" +
            " FROM users WHERE email = #{email}")
    List<User> getUserByEmail(String email);
    @Select("SELECT id FROM users where id = #{id}")
    String getUserIdByUserId(String id);
    @Select("SELECT point FROM users WHERE id = #{id}")
    int getUserPointByUserId(String id);
    // Update
    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    int updateUserPassword(User user);
    @Update("UPDATE users SET is_active = #{isActive} WHERE id = #{id}")
    int updateActivateStatus(int isActive, String id);
    @Update("UPDATE users SET point = #{point} where id = #{id}")
    int updateUserPoint(int point, String id);
    // Delete
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(String id);


}
