package com.internship.tmontica.user;

import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.user.exception.UserException;
import com.internship.tmontica.user.exception.UserExceptionType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailSender;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    @Mock
    private MailSender sender;
    @Mock
    private ModelMapper mapper;
    private User badUser;
    private User willSignUpUser;
    private User alreadySignUpUser;
    private User alreadySignUpUserWithBadPassword;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date birthDate =  Date.from(LocalDateTime.of(1993,9,17, 3, 50).atZone(defaultZoneId).toInstant());
        Date regDate =  Date.from(LocalDateTime.of(2019,8,8, 10, 10).atZone(defaultZoneId).toInstant());
        willSignUpUser = new User("samkko", "김형석", "vndtjd1217@naver.com",
                birthDate, "tmon123!", "tmon123!", "USER", regDate,0, false, "abcdefg");
        alreadySignUpUser = new User("vndtjd1217", "산체스", "wkvk1234@gmail.com",
                birthDate, "tmon123!", "tmon123!", "ADMIN", regDate,0, true, "34567");
        badUser = new User("1", "1", "1",
                birthDate, "1", "3", "x", regDate,0, false, "badActivateCode");
        alreadySignUpUserWithBadPassword = new User("vndtjd1217", "산체스", "wkvk1234@gmail.com",
                birthDate, "badpassword!", "badpassword!", "ADMIN", regDate,0, true, "34567");
    }

    //** checkUserIdDuplicatedException **//
    @Test
    public void 아이디_중복체크_중복(){

        // expected
        expectedException.expect(UserException.class);
        expectedException.expectMessage(new UserException(UserExceptionType.USER_ID_DUPLICATED_EXCEPTION).getMessage());

        // given
        String id = willSignUpUser.getId();
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
    public void 유저_권한체크_성공(){

        userService.checkUserRoleMismatchException(willSignUpUser.getRole());
    }

    @Test
    public void 유저_권한체크_실패(){

        // expected
        expectedException.expect(UserException.class);
        expectedException.expectMessage(new UserException(UserExceptionType.INVALID_USER_ROLE_EXCEPTION).getMessage());

        userService.checkUserRoleMismatchException(badUser.getRole());
    }

    @Test
    public void 회원가입_성공() {

        // given
        아이디_중복체크_중복아님();
        // 비밀번호 / 비밀번호 확인 일치
        Assert.assertEquals(willSignUpUser.getPassword(), willSignUpUser.getPasswordCheck());
        유저_권한체크_성공();
        when(userDao.addUser(willSignUpUser)).thenReturn(1);

        userService.signUp(willSignUpUser);
    }

    @Test
    public void 유저_활성화_성공(){

        String id = willSignUpUser.getId();
        String code = willSignUpUser.getActivateCode();
        given(userDao.getUserIdByUserId(id)).willReturn(willSignUpUser.getId());
        given(userDao.getUserByUserId(id)).willReturn(willSignUpUser);
        when(userDao.updateActivateStatus(1, id)).thenReturn(1);

        userService.activateUser(id, code);
    }

    @Test
    public void 유저_활성화_코드불일치_실패(){

        // expected
        expectedException.expect(UserException.class);
        expectedException.expectMessage(new UserException(UserExceptionType.ACTIVATE_CODE_MISMATCH_EXCEPTION).getMessage());

        // given
        String id = willSignUpUser.getId();
        String code = badUser.getActivateCode();
        given(userDao.getUserIdByUserId(id)).willReturn(willSignUpUser.getId());
        given(userDao.getUserByUserId(id)).willReturn(willSignUpUser);

        // when
        userService.activateUser(id, code);
    }

    @Test
    public void 로그인_성공(){

        // given
        String id = alreadySignUpUser.getId();
        given(userDao.getUserByUserId(id)).willReturn(alreadySignUpUser);
        given(userDao.getUserIdByUserId(id)).willReturn(alreadySignUpUser.getId());

        // then
        userService.signInCheck(alreadySignUpUser);
    }

    @Test
    public void 로그인_실패_비활성화유저(){

        // expected
        expectedException.expect(UserException.class);
        expectedException.expectMessage(new UserException(UserExceptionType.USER_NOT_ACTIVATE_EXCEPTION).getMessage());

        // given
        String id = badUser.getId();
        given(userDao.getUserByUserId(id)).willReturn(badUser);
        given(userDao.getUserIdByUserId(id)).willReturn(badUser.getId());

        // then
        userService.signInCheck(badUser);
    }

    @Test
    public void 로그인_실패_비밀번호틀림(){

        // expected
        expectedException.expect(UserException.class);
        expectedException.expectMessage(new UserException(UserExceptionType.PASSWORD_MISMATCH_EXCEPTION).getMessage());

        // given
        String id = alreadySignUpUser.getId();
        given(userDao.getUserByUserId(id)).willReturn(alreadySignUpUser);
        given(userDao.getUserIdByUserId(id)).willReturn(alreadySignUpUser.getId());

        // then
        userService.signInCheck(alreadySignUpUserWithBadPassword);
    }

    @Test
    public void 로그인_실패_없는아이디(){

        // expected
        expectedException.expect(UserException.class);
        expectedException.expectMessage(new UserException(UserExceptionType.USER_ID_NOT_FOUND_EXCEPTION).getMessage());

        // given
        String id = badUser.getId();
        given(userDao.getUserByUserId(id)).willReturn(null);

        // when
        userService.signInCheck(badUser);
    }

    @Test
    public void 비밀번호_확인_성공(){

        // given
        String id = alreadySignUpUser.getId();
        given(userDao.getUserByUserId(alreadySignUpUser.getId())).willReturn(alreadySignUpUser);

        // when
        userService.checkPassword(alreadySignUpUser);
    }

    @Test
    public void 비밀번호_수정_성공(){

        // given
        given(userDao.updateUserPassword(alreadySignUpUser)).willReturn(1);

        // when
        userService.changePassword(alreadySignUpUser);
    }


}
