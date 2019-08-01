package com.internship.tmontica.option;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OptionDao {
    @Select("SELECT * FROM options WHERE id = #{id}")
    Option getOptionById(int id);
    @Select("SELECT * FROM options")
    List<Option> getAllOptions();
    @Select("select id from options where name = #{name}")
    int getOptionIdByName(String name);
}
