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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService { //implements UserDetail

    private final UserDao userDao;
    private final FindDao findDao;
    private final ModelMapper modelMapper;
    private final MailSender sender;
    private final JwtService jwtService;


    public boolean signUp(UserSignUpReqDTO userSignUpReqDTO) throws MailSendException{

        //userSignUpReqDTO.setPassword(passwordEncoder.encode(user.getPassword())); need passwordEnconding
        checkUserIdDuplicatedException(userSignUpReqDTO.getId());
        checkPasswordMismatchException(userSignUpReqDTO.getPassword(), userSignUpReqDTO.getPasswordCheck());
        setRole(userSignUpReqDTO);
        String key = new AuthenticationKey().getAuthenticationKey();
        User user = modelMapper.map(userSignUpReqDTO, User.class);
        setActivateCode(user, key);
        if(userDao.addUser(user) < 1){
            return false;
        }
        UserMailForm userMailForm = new UserMailForm(MailType.SIGN_UP, user);
        userMailForm.setAuthenticationKey(key);
        userMailForm.makeMail();
        sender.send(userMailForm.getMsg());

        return true;
    }

    // 권한 추가되면 수정되야함 구조 변경 가능할듯,,
    private void setRole(UserSignUpReqDTO userSignUpReqDTO){

        String role = userSignUpReqDTO.getRole();
        if(role == null || role.equals(UserRole.USER.getRole())){
            userSignUpReqDTO.setRole("USER");
            return;
        } else if (role.equals(UserRole.ADMIN.getRole())){
            return;
        }

        throw new UserException(UserExceptionType.INVALID_USER_ROLE_EXCEPTION);
    }

    private void setActivateCode(User user, String activateCode){

        user.setActivateCode(activateCode);
    }

    public boolean activateUser(String id, String code){

        checkUserIdNotFoundException(id);
        User user = userDao.getUserByUserId(id);
        if(!code.equals(user.getActivateCode())){
            throw new UserException(UserExceptionType.ACTIVATE_CODE_MISMATCH_EXCEPTION);
        }

        return userDao.updateActivateStatus(1) > 0;
    }

    public boolean idDuplicateCheck(String id){
        return !isDuplicate(id);
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
        checkMissingSessionUserIdException(userId);
        checkPasswordMismatchException(userCheckPasswordReqDTO.getPassword(),
                userDao.getUserByUserId(userId).getPassword());
    }

    public boolean changePassword(UserChangePasswordReqDTO userChangePasswordReqDTO){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        checkMissingSessionUserIdException(userId);
        checkPasswordMismatchException(userChangePasswordReqDTO.getNewPassword(), userChangePasswordReqDTO.getNewPasswordCheck());
        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO(userId, userChangePasswordReqDTO.getNewPassword());
        return userDao.updateUserPassword(userChangePasswordDTO) > 0;
    }

    private void checkMissingSessionUserIdException(String userId){

        if(userId == null){
            throw new UserException(UserExceptionType.MISSING_SESSION_ATTRIBUTE_EXCEPTION);
        }
    }

    public boolean withdrawUser(String id){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        if(!userId.equals(id)){
            throw new UnauthorizedException();
        }
        checkUserIdNotFoundException(id);
        return userDao.deleteUser(id) > 0;
    }

    @Transactional
    public boolean sendUserId(String email) throws MailSendException{

        List<User> userList = userDao.getUserByEmail(email);
        if(userList.isEmpty()){
            return false;
        }
        AuthenticationKey authenticationKey = new AuthenticationKey();
        String key = authenticationKey.getAuthenticationKey();
        UserMailForm userMailForm = new UserMailForm(MailType.FIND_ID, userList.get(0));

        while(findDao.getAuthCode(key) != null){
            key = authenticationKey.getAuthenticationKey();
        }
        findDao.addAuthCode(new FindId(key, userList.stream().map(User :: getId).collect(Collectors.toList()).toString()));
        userMailForm.setAuthenticationKey(key);
        userMailForm.makeMail();
        sender.send(userMailForm.getMsg());
        return true;
    }

    @Transactional
    public boolean sendUserPassword(String id, String email) throws MailSendException{
        User user = userDao.getUserByUserId(id);
        checkEmailMismatchException(user.getEmail(), email);
        String tempPassword = new AuthenticationKey().getAuthenticationKey();
        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO(user.getId(), tempPassword);
        userDao.updateUserPassword(userChangePasswordDTO);
        user.setPassword(tempPassword);
        UserMailForm userMailForm = new UserMailForm(MailType.FIND_PW, user);
        userMailForm.makeMail();
        sender.send(userMailForm.getMsg());
        return true;
    }

    @Transactional
    public UserFindIdRespDTO checkAuthCode(UserFindIdReqDTO userFindIdReqDTO){

        FindId findId = findDao.getAuthCode(userFindIdReqDTO.getAuthCode());
        if(findId != null){
            List<String> findIds = new ArrayList<>(Arrays.asList(findId.getFindIds().replace("[","").replace("]","").split(",")));
            findDao.withdrawAuthCode(userFindIdReqDTO.getAuthCode());
            return new UserFindIdRespDTO(findIds);
        }

        throw new UserException(UserExceptionType.AUTHCODE_NOT_FOUND_EXCEPTION);
    }

    public UserInfoRespDTO getUserInfo(String id){

        checkUserIdNotFoundException(id);
        return modelMapper.map(userDao.getUserByUserId(id), UserInfoRespDTO.class);
    }

    private void checkUserIdDuplicatedException(String id){

        if(isDuplicate(id)){
            throw new UserException(UserExceptionType.USER_ID_DUPLICATED_EXCEPTION);
        }
    }

    private boolean isSamePassword(String password, String comparePassword){

        return password.equals(comparePassword);
    }

    private void checkPasswordMismatchException(String password, String comparePassword){

        if(!isSamePassword(password, comparePassword)){
            throw new UserException(UserExceptionType.PASSWORD_MISMATCH_EXCEPTION);
        }
    }

    private boolean isExistUser(String id){

        return userDao.getUserIdByUserId(id)!=null;
    }

    private void checkUserIdNotFoundException(String id){

        if(!isExistUser(id)){
            throw new UserException(UserExceptionType.USER_ID_NOT_FOUND_EXCEPTION);
        }
    }

    private boolean isCorrectEmail(String email, String compareEmail){

        return email.equals(compareEmail);
    }

    private void checkEmailMismatchException(String email, String compareEmail){

        if(!isCorrectEmail(email, compareEmail)){
            throw new UserException(UserExceptionType.EMAIL_MISMATCH_EXCEPTION);
        }
    }
//    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//
//        User user = userDao.getUserByUserId(userId);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//               authorities.add(new SimpleGrantedAuthority("user"));
//        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),authorities);
//
//    }
}
