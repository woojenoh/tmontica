package com.internship.tmontica.user;

import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.security.exception.UnauthorizedException;
import com.internship.tmontica.user.exception.*;
import com.internship.tmontica.user.find.FindDao;
import com.internship.tmontica.user.find.FindId;
import com.internship.tmontica.user.model.request.*;
import com.internship.tmontica.user.model.response.*;
import com.internship.tmontica.security.AuthenticationKey;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final MailSender sender;
    private final JwtService jwtService;

    public void signUp(UserSignUpReqDTO userSignUpReqDTO) throws MailSendException{

        checkUserIdDuplicatedException(userSignUpReqDTO.getId());
        checkPasswordMismatchException(userSignUpReqDTO.getPassword(), userSignUpReqDTO.getPasswordCheck());
        setRoleAndCheck(userSignUpReqDTO);
        String key = new AuthenticationKey().getAuthenticationKey();
        User user = modelMapper.map(userSignUpReqDTO, User.class);
        setActivateCode(user, key);

        if(userDao.addUser(user) < 1){
            throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }

        UserMailForm userMailForm = new UserMailForm(MailType.SIGN_UP, user, key);
        sender.send(userMailForm.getMsg());
    }

    private void setRoleAndCheck(UserSignUpReqDTO userSignUpReqDTO){

        String role = userSignUpReqDTO.getRole();

        if(role == null){
            userSignUpReqDTO.setRole("USER");
            return;
        }

        checkUserRoleMismatchException(role);
    }

    private void setActivateCode(User user, String activateCode){

        user.setActivateCode(activateCode);
    }

    public void activateUser(String id, String code){

        checkUserIdNotFoundException(id);
        User user = userDao.getUserByUserId(id);

        if(!code.equals(user.getActivateCode())){
            throw new UserException(UserExceptionType.ACTIVATE_CODE_MISMATCH_EXCEPTION);
        }

        if(userDao.updateActivateStatus(1) < 1){
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

    public void signIn(UserSignInReqDTO userSignInReqDTO) {

        User user = userDao.getUserByUserId(userSignInReqDTO.getId());
        checkUserIdNotFoundException(userSignInReqDTO.getId());
        checkPasswordMismatchException(userSignInReqDTO.getPassword(), user.getPassword());
        checkUserActivateException(user);
    }

    private void checkUserActivateException(User user){

        if(user.isActive()){
            return;
        }
        throw new UserException(UserExceptionType.USER_NOT_ACTIVATE_EXCEPTION);
    }

     public UserSignInRespDTO makeJwtToken(UserSignInReqDTO userSignInReqDTO){

        return new UserSignInRespDTO(jwtService.getToken(makeTokenUser(userSignInReqDTO.getId())));
    }

    private UserTokenInfoDTO makeTokenUser(String id){

        User user = userDao.getUserByUserId(id);
        return modelMapper.map(user, UserTokenInfoDTO.class);
    }

    public void checkPassword(UserCheckPasswordReqDTO userCheckPasswordReqDTO){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        checkPasswordMismatchException(userCheckPasswordReqDTO.getPassword(),
                userDao.getUserByUserId(userId).getPassword());
    }

    public void changePassword(UserChangePasswordReqDTO userChangePasswordReqDTO){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        checkPasswordMismatchException(userChangePasswordReqDTO.getNewPassword(), userChangePasswordReqDTO.getNewPasswordCheck());
        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO(userId, userChangePasswordReqDTO.getNewPassword());

        if(userDao.updateUserPassword(userChangePasswordDTO) < 1){
            throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }
    }

    public void withdrawUser(String id){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");

        if(!userId.equals(id)){
            throw new UnauthorizedException();
        }

        if(userDao.deleteUser(id) < 1){
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

        //우연히 인증코드가 겹치는 경우 재발급
        while(findDao.getAuthCode(key) != null){
            key = authenticationKey.getAuthenticationKey();
        }

        UserMailForm userMailForm = new UserMailForm(MailType.FIND_ID, userList.get(0), key);

        if(findDao.addAuthCode(new FindId(key, userList.stream().
                map(User :: getId).
                collect(Collectors.toList()).
                toString())) < 1){
            throw new UserException(UserExceptionType.DATABASE_FAIL_EXCEPTION);
        }

        sender.send(userMailForm.getMsg());
    }

    @Transactional
    public void sendUserPassword(String id, String email) throws MailSendException{

        checkUserIdNotFoundException(id);
        User user = userDao.getUserByUserId(id);
        checkEmailMismatchException(user.getEmail(), email);

        String tempPassword = new AuthenticationKey().getAuthenticationKey();
        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO(user.getId(), tempPassword);
        userDao.updateUserPassword(userChangePasswordDTO);

        user.setPassword(tempPassword);
        UserMailForm userMailForm = new UserMailForm(MailType.FIND_PW, user, false);
        sender.send(userMailForm.getMsg());
    }

    @Transactional
    public UserFindIdRespDTO checkAuthCode(UserFindIdReqDTO userFindIdReqDTO){

        FindId findId = findDao.getAuthCode(userFindIdReqDTO.getAuthCode());
        if(findId != null){
            List<String> findIds = new ArrayList<>(Arrays.asList(findId.getFindIds().replace(" ","").replace("[","").replace("]","").split(",")));
            findDao.withdrawAuthCode(userFindIdReqDTO.getAuthCode());
            return new UserFindIdRespDTO(findIds);
        }

        throw new UserException(UserExceptionType.AUTHCODE_NOT_FOUND_EXCEPTION);
    }

    public UserInfoRespDTO getUserInfo(String id){

        checkUserIdNotFoundException(id);
        return modelMapper.map(userDao.getUserByUserId(id), UserInfoRespDTO.class);
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
