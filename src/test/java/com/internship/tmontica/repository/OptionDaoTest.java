package com.internship.tmontica.repository;

import com.internship.tmontica.dto.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptionDaoTest {

    @Autowired
    private OptionDao optionDao;

    @Test
    public void insertOption() {
        Option option = new Option("name", 500, "type");
        optionDao.addOption(option);
    }

    @Test
    public void getOptions() {
        List<Option> options = optionDao.getAllOptions();
        for(Option o : options)
            System.out.println(o.getId());
    }

}