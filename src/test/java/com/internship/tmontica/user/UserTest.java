package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class UserTest {

    private UserService userService;
    private User user;
    private ModelMapper modelMapper;

    @Before
    public void setUp(){
        user = new User("vndtjd1217", "Kim hyung seok", "vndtjd1217@naver.com",
                Date.valueOf("1993-09-17"), "123qwe!", "ADMIN",
                Date.valueOf("2019-09-17"), 0, true, "fdfqerqer");
    }

    @Test
    public void signup() {

        UserSignUpReqDTO userSignUpReqDTO = modelMapper.map(user, UserSignUpReqDTO.class);
        //userService.signUp()
    }
}
