package com.internship.tmontica.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.internship.tmontica")
public class DataAccessConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*.xml"));
        // camel case 설정 : https://github.com/mybatis/spring-boot-starter/issues/78 참고
        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(ibatisConfiguration);
        return sessionFactory.getObject();
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
