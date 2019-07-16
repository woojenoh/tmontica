package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Option;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@Mapper
public interface OptionDao {
    public int insertOption(Option option);
    public Option getOption(int id);
    public List<Option> getAllOptions();
}
