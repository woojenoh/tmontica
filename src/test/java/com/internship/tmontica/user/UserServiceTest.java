package com.internship.tmontica.user;

import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.user.exception.UserException;
import com.internship.tmontica.user.exception.UserExceptionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserDao userDao;
    @Mock
    private JwtService jwtService;

    private User willSignUpUser;
    private User alreadySignUpUser;

    @Before
    public void setUp(){

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date birthDate =  Date.from(LocalDateTime.of(1993,9,17, 3, 50).atZone(defaultZoneId).toInstant());
        Date regDate =  Date.from(LocalDateTime.of(2019,8,8, 10, 10).atZone(defaultZoneId).toInstant());
        willSignUpUser = new User("samkko", "김형석", "wkvk1234@gmail.com",
                birthDate, "tmon123!", "tmon123!", "USER", regDate,0, false, "abcdefg");
        alreadySignUpUser = new User("vndtjd1217", "산체스", "vndtjd1217@naver.com",
                birthDate, "tmon123!", "tmon123!", "ADMIN", regDate,0, true, "abcdefg");
    }

    //** checkUserIdDuplicatedException **//
    @Test(expected = UserException.class)
    public void 아이디_중복체크_중복(){

        String id = willSignUpUser.getId();
        // given
        when(userDao.getUserByUserId(id)).thenReturn(alreadySignUpUser);

        // when
        userService.checkUserIdDuplicatedException(id);
    }

    //** checkUserIdDuplicatedException **//
    @Test
    public void 아이디_중복체크_중복아님(){

        String id = willSignUpUser.getId();
        // given
        when(userDao.getUserByUserId(id)).thenReturn(null);

        // when
        userService.checkUserIdDuplicatedException(id);

        // then void method..? 어떻게 처리하는지?
    }

    //** checkPasswordMismatchException **//
    @Test(expected = UserException.class)
    public void 비밀번호_비밀번호확인_불일치체크(){


    }
    @Test
    public void 회원가입() {


    }
}
