package com.internship.tmontica.user;

import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.security.exception.UnauthorizedException;
import com.internship.tmontica.user.exception.*;
import com.internship.tmontica.user.find.FindDao;
import com.internship.tmontica.user.find.FindId;
import com.internship.tmontica.user.model.response.*;
import com.internship.tmontica.security.AuthenticationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final FindDao findDao;
    private final JwtService jwtService;
    private final MailSender sender;

    @Transactional
    public void signUp(User user) throws MailSendException {

        //회원가입 정보 예외검사 및 기본설정
        checkUserIdDuplicatedException(user.getId());
        checkPasswordMismatchException(user.getPassword(), user.getPasswordCheck());
        setDefaultRole(user);
        checkUserRoleMismatchException(user.getRole());

        //문제없는 회원가입 정보가 왔다면 활성화 코드 설정
        String key = new AuthenticationKey().getAuthenticationKey();
        setActivateCode(user, key);

        if(userDao.addUser(user) < 1){
             throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }

        //Sign-up 메일 전송
        UserMailForm userMailForm = new UserMailForm(MailType.SIGN_UP, user, key);
        sender.send(userMailForm.getMsg());
    }

    // 권한 설정이 안되어 있는 경우 USER 을 기본 권한으로 설정
    private void setDefaultRole(User user){

        String role = user.getRole();

        if(role == null){
            user.setRole("USER");
        }
    }

    private void setActivateCode(User user, String activateCode){

        user.setActivateCode(activateCode);
    }

    // 신규 가입자 활성화해주는 메소드
    public void activateUser(String id, String code){

        checkUserIdNotFoundException(id);
        User user = userDao.getUserByUserId(id);

        if(!code.equals(user.getActivateCode())){
            throw new UserException(UserExceptionType.ACTIVATE_CODE_MISMATCH_EXCEPTION);
        }

        if(userDao.updateActivateStatus(1, id) < 1){
            throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }
    }

    public void checkUserIdDuplicatedException(String id){

        if(isDuplicate(id)){
            throw new UserException(UserExceptionType.USER_ID_DUPLICATED_EXCEPTION);
        }
    }

    private boolean isDuplicate(String id){
        User user = userDao.getUserByUserId(id);

        return !(user == null);
    }

    // Sign-in 관련 데이터 체크
    public void signInCheck(User user) {

        User data = userDao.getUserByUserId(user.getId());
        checkUserIdNotFoundException(user.getId());
        checkPasswordMismatchException(user.getPassword(), data.getPassword());
        checkUserActivateException(data);
    }

    private void checkUserActivateException(User user){

        if(user.isActive()){
            return;
        }
        throw new UserException(UserExceptionType.USER_NOT_ACTIVATE_EXCEPTION);
    }

    // Sign-in시 발급할 JWT 토큰을 만드는 메소드
    public String makeJwtToken(User user){

        return jwtService.getToken(makeTokenUser(user.getId()));
    }

    private User makeTokenUser(String id){

        return userDao.getUserByUserId(id);
    }

    public void checkPassword(User user){

        checkPasswordMismatchException(user.getPassword(),
                userDao.getUserByUserId(user.getId()).getPassword());
    }

    public void changePassword(User user){

        checkPasswordMismatchException(user.getPassword(), user.getPasswordCheck());

        if(userDao.updateUserPassword(user) < 1){
            throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }
    }

    //JWT토큰에 담긴 유저아이디와 삭제 타겟 유저의 아이디를 체크하고 삭제
    public void withdrawUser(String targetId, String currentId){

        if(!currentId.equals(targetId)){
            throw new UnauthorizedException();
        }

        if(userDao.deleteUser(targetId) < 1){
            throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }
    }

    @Transactional
    public void sendUserId(String email) throws MailSendException{

        List<User> userList = userDao.getUserByEmail(email);
        if(userList.isEmpty()){
            throw new UserException(UserExceptionType.EMAIL_NOT_FOUND_EXCEPTION);
        }

        AuthenticationKey authenticationKey = new AuthenticationKey();
        String key = authenticationKey.getAuthenticationKey();

        //우연히 인증코드가 겹치는 경우 인증키 재발급
        while(findDao.getAuthCode(key) != null){
            key = authenticationKey.getAuthenticationKey();
        }

        // 인증코드와 아이디 찾기를 요청받은 아이디들을 DB에 등록하고 실패하면 DATABASE_FAIL예외 throw
        if(findDao.addAuthCode(new FindId(key, userList.stream().
                filter(User::isActive).
                map(User :: getId).
                collect(Collectors.toList()).
                toString())) < 1){
            throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }

        UserMailForm userMailForm = new UserMailForm(MailType.FIND_ID, userList.get(0), key);
        sender.send(userMailForm.getMsg());
    }

    @Transactional
    public void sendUserPassword(String id, String email) throws MailSendException{

        checkUserIdNotFoundException(id);
        User user = userDao.getUserByUserId(id);
        checkEmailMismatchException(user.getEmail(), email);

        user.setPassword(new AuthenticationKey().getAuthenticationKey());
        userDao.updateUserPassword(user);

        UserMailForm userMailForm = new UserMailForm(MailType.FIND_PW, user, false);
        sender.send(userMailForm.getMsg());
    }

    // 아이디 찾기 관련 인증코드 확인 메소드
    @Transactional
    public UserFindIdResponseDTO checkAuthCode(FindId findId){

        FindId data = findDao.getAuthCode(findId.getAuthCode());
        if(data != null){
            List<String> findIds = new ArrayList<>(Arrays.asList(data.getFindIds().replace(" ","").replace("[","").replace("]","").split(",")));
            findDao.withdrawAuthCode(data.getAuthCode());
            return new UserFindIdResponseDTO(findIds);
        }

        throw new UserException(UserExceptionType.AUTHCODE_NOT_FOUND_EXCEPTION);
    }

    public User getUserInfo(String id){

        checkUserIdNotFoundException(id);
        return userDao.getUserByUserId(id);
    }

    private void checkPasswordMismatchException(String password, String comparePassword){

        if(password.equals(comparePassword)){
            return;
        }
        throw new UserException(UserExceptionType.PASSWORD_MISMATCH_EXCEPTION);
    }

    private boolean isExistUser(String id){

        return userDao.getUserIdByUserId(id)!=null;
    }

    private void checkUserIdNotFoundException(String id){

        if(!isExistUser(id)){
            throw new UserException(UserExceptionType.USER_ID_NOT_FOUND_EXCEPTION);
        }
    }

    private void checkEmailMismatchException(String email, String compareEmail){

        if(email.equals(compareEmail)){
            return;
        }
        throw new UserException(UserExceptionType.EMAIL_MISMATCH_EXCEPTION);
    }

    public void checkUserRoleMismatchException(String role){

        for(UserRole userRole : UserRole.values()){

            if(role.equals(userRole.getRole())){
                return;
            }
        }
        throw new UserException(UserExceptionType.INVALID_USER_ROLE_EXCEPTION);
    }
}
