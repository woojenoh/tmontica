package com.internship.tmontica.option;

import com.internship.tmontica.config.DataAccessConfig;
import com.internship.tmontica.config.DataSourceConfig;
import com.internship.tmontica.option.Option;
import com.internship.tmontica.option.OptionDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Import({DataAccessConfig.class , DataSourceConfig.class})
public class OptionDaoTest {

    @Autowired
    private OptionDao optionDao;

    @Test
    public void insertOption() {
//        Option option = new Option("name", 500,"type");
//        optionDao.addOption(option);
    }

    @Test
    public void getOptions() {
        List<Option> options = optionDao.getAllOptions();
        for(Option o : options)
            System.out.println(o.getId());
    }

}