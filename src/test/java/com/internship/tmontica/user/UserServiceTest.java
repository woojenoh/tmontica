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
import org.springframework.mail.MailSender;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserDao userDao;
    @Mock
    private JwtService jwtService;
    @Mock
    private MailSender sender;
    private User badUser;
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
        badUser = new User("1", "1", "1",
                birthDate, "1", "3", "x", regDate,0, false, "abcdefg");
    }

    //** checkUserIdDuplicatedException **//
    @Test(expected = UserException.class)
    public void 아이디_중복체크_중복(){

        String id = willSignUpUser.getId();
        // given
        given(userDao.getUserByUserId(id)).willReturn(alreadySignUpUser);

        // when
        userService.checkUserIdDuplicatedException(id);

        // then  UserException 발생.
    }

    //** checkUserIdDuplicatedException **//
    @Test
    public void 아이디_중복체크_중복아님(){

        String id = willSignUpUser.getId();
        // given
        given(userDao.getUserByUserId(id)).willReturn(null);

        // when
        userService.checkUserIdDuplicatedException(id);

        // then  아무 예외도 발생하지 않았으면 OK
    }

    @Test
    public void 유저_권한체크_통과(){

        userService.checkUserRoleMismatchException(willSignUpUser.getRole());
    }

    @Test(expected = UserException.class)
    public void 유저_권한체크_실패(){

        userService.checkUserRoleMismatchException(badUser.getRole());
    }

    @Test
    public void 회원가입() {

        // given
        아이디_중복체크_중복아님();
        // 비밀번호 / 비밀번호 확인 일치
        //when(willSignUpUser.getPassword().equals(willSignUpUser.getPasswordCheck())).thenReturn(true);
        유저_권한체크_통과();
        when(userDao.addUser(willSignUpUser)).thenReturn(1);
        userService.signUp(willSignUpUser);
    }
}
