package com.internship.tmontica.user;

import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.security.exception.UnauthorizedException;
import com.internship.tmontica.user.exception.*;
import com.internship.tmontica.user.model.request.*;
import com.internship.tmontica.user.model.response.UserFindIdRespDTO;
import com.internship.tmontica.user.model.response.UserInfoRespDTO;
import com.internship.tmontica.security.AuthenticationKey;
import com.internship.tmontica.user.model.response.UserSignInRespDTO;
import com.internship.tmontica.user.model.response.UserTokenInfoDTO;
import com.internship.tmontica.util.JsonUtil;
import com.internship.tmontica.util.UserConfigValueName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserService { //implements UserDetail

    private final UserDao userDao;
    //private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MailSender sender;
    private final JwtService jwtService;

    @Transactional
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

    private void setRole(UserSignUpReqDTO userSignUpReqDTO){

        String role = userSignUpReqDTO.getRole();
        if(role == null || role.equals(UserRole.USER.toString())){
            userSignUpReqDTO.setRole("USER");
            return;
        } else if (role.equals(UserRole.ADMIN.toString())){
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

    @Transactional(readOnly = true)
    public boolean idDuplicateCheck(String id){
        return !isDuplicate(id);
    }

    private boolean isDuplicate(String id){
        User user = userDao.getUserByUserId(id);

        return !(user == null);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
     public UserSignInRespDTO makeJwtToken(UserSignInReqDTO userSignInReqDTO){

        return new UserSignInRespDTO(jwtService.getToken(makeTokenUser(userSignInReqDTO.getId())));
    }

    private UserTokenInfoDTO makeTokenUser(String id){

        User user = userDao.getUserByUserId(id);
        return modelMapper.map(user, UserTokenInfoDTO.class);
    }

    @Transactional(readOnly = true)
    public void checkPassword(UserCheckPasswordReqDTO userCheckPasswordReqDTO){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        checkMissingSessionUserIdException(userId);
        checkPasswordMismatchException(userCheckPasswordReqDTO.getPassword(),
                userDao.getUserByUserId(userId).getPassword());
    }

    @Transactional
    public boolean changePassword(UserChangePasswordReqDTO userChangePasswordReqDTO){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        checkMissingSessionUserIdException(userId);
        checkPasswordMismatchException(userChangePasswordReqDTO.getNewPassword(), userChangePasswordReqDTO.getNewPasswordCheck());
        User user = modelMapper.map(userChangePasswordReqDTO, User.class);
        user.setId(userId);
        return userDao.updateUserPassword(user) > 0;
    }

    private void checkMissingSessionUserIdException(String userId){

        if(userId == null){
            throw new UserException(UserExceptionType.MISSING_SESSION_ATTRIBUTE_EXCEPTION);
        }
    }

    @Transactional
    public boolean withDrawUser(String id){

        String userId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        if(!userId.equals(id)){
            throw new UnauthorizedException();
        }
        checkUserIdNotFoundException(id);
        return userDao.deleteUser(id) > 0;
    }

    @Transactional(readOnly = true)
    public boolean sendUserId(String email, HttpSession httpSession) throws MailSendException{
        User user = userDao.getUserByEmail(email);
        if(user == null){
            return false;
        }
        AuthenticationKey authenticationKey = new AuthenticationKey();
        String key = authenticationKey.getAuthenticationKey();
        UserMailForm userMailForm = new UserMailForm(MailType.FIND_ID, user);
        userMailForm.setAuthenticationKey(key);
        userMailForm.makeMail();
        sender.send(userMailForm.getMsg());
        httpSession.setAttribute(UserConfigValueName.ID_AUTH_CODE, key);
        httpSession.setAttribute(UserConfigValueName.FIND_ID_SESSION_ATTRIBUTE_NAME, user.getId());
        return true;
    }

    @Transactional
    public boolean sendUserPassword(String id, String email) throws MailSendException{
        User user = userDao.getUserByUserId(id);
        checkEmailMismatchException(user.getEmail(), email);
        UserMailForm userMailForm = new UserMailForm(MailType.FIND_PW, user);
        sender.send(userMailForm.getMsg());
        return true;
    }

    @Transactional
    public UserFindIdRespDTO checkAuthCode(UserFindIdReqDTO userFindIdReqDTO, HttpSession session){

        String authCode = checkSessionAttribute(session, UserConfigValueName.ID_AUTH_CODE);
        if(userFindIdReqDTO.getAuthCode().equals(authCode)){
            String findId = checkSessionAttribute(session, UserConfigValueName.FIND_ID_SESSION_ATTRIBUTE_NAME);
            return new UserFindIdRespDTO(findId, true);
        }

        return new UserFindIdRespDTO("",false );
    }

    private String checkSessionAttribute(HttpSession session, String key){

        String attribute = (String)session.getAttribute(key);
        if(attribute==null){
            throw new UserException(UserExceptionType.MISSING_SESSION_ATTRIBUTE_EXCEPTION);
        }
        return attribute;
    }

    @Transactional(readOnly = true)
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
