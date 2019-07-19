package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Option;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@Mapper
public interface OptionDao {
    int addOption(Option option);
    Option getOptionById(int id);
    List<Option> getAllOptions();
    void updateOption(Option option);
    void deleteOption(int id);

}
