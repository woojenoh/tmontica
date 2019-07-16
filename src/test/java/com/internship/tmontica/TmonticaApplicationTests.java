package com.internship.tmontica;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmonticaApplicationTests {

	@Autowired
	SqlSession sqlSession;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSqlSession() {
		System.out.println("sqlSession : "+sqlSession);
	}


}
