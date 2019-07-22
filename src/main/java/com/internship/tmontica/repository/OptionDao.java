package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Option;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OptionDao {
    @Insert("INSERT INTO options (name ,price ,type) VALUES(#{name}, #{price}, #{type})")
    int addOption(Option option);
    @Select("SELECT * FROM options WHERE id = #{id}")
    Option getOptionById(int id);
    @Select("SELECT * FROM options")
    List<Option> getAllOptions();
    @Update("UPDATE options SET name = #{name} , price = #{price} , type = #{type} WHERE id = #{id}")
    void updateOption(Option option);
    @Delete("DELETE FROM options WHERE id = #{id}")
    void deleteOption(int id);
    @Select("select id from options where name = #{name}")
    int getOptionIdByName(String name);
}
