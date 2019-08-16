package com.internship.tmontica.user.find;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FindDao {

    @Insert({"INSERT INTO find_id (auth_code, find_ids) VALUES(#{authCode}, #{findIds})"})
    int addAuthCode(FindId findId);
    @Select("SELECT id, auth_code, find_ids FROM find_id WHERE auth_code = #{authCode}")
    FindId getAuthCode(String authCode);
    @Delete("Delete From find_id Where auth_code = #{authCode}")
    int withdrawAuthCode(String authCode);
}
