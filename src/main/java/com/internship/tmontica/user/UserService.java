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
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserService { //implements UserDetail

    private final UserDao userDao;
    //private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MailSender sender;
    private final JwtService jwtService;


    private enum MailOption {
        findId, findPassword
    }

    @Transactional
    public boolean signUp(UserSignUpReqDTO userSignUpReqDTO){
        //userSignUpReqDTO.setPassword(passwordEncoder.encode(user.getPassword())); need passwordEnconding
        checkUserIdDuplicatedException(userSignUpReqDTO.getId());
        checkPasswordMismatchException(userSignUpReqDTO.getPassword(), userSignUpReqDTO.getPasswordCheck());
        setRole(userSignUpReqDTO);
        User user = modelMapper.map(userSignUpReqDTO, User.class);
        return userDao.addUser(user) > 0;
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

        checkUserIdNotFoundException(userSignInReqDTO.getId());
        checkPasswordMismatchException(userSignInReqDTO.getPassword(),
                userDao.getUserByUserId(userSignInReqDTO.getId()).getPassword());
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
        sender.send(sendMail(user, MailOption.findId, authenticationKey.getAuthenticationKey()));
        httpSession.setAttribute(UserConfigValueName.ID_AUTH_CODE, authenticationKey.getAuthenticationKey());
        httpSession.setAttribute(UserConfigValueName.FIND_ID_SESSION_ATTRIBUTE_NAME, user.getId());
        return true;
    }

    @Transactional
    public boolean sendUserPassword(String id, String email) throws MailSendException{
        User user = userDao.getUserByUserId(id);
        checkEmailMismatchException(user.getEmail(), email);
        sender.send(sendMail(user, MailOption.findPassword));
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

    private SimpleMailMessage sendMail(User user, MailOption mailOption, String... strings) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("tmontica701@gmail.com");
        msg.setTo(user.getEmail());

        switch(mailOption){
            case findId:
                msg.setSubject("[TMONG CAFFE]"+user.getName()+"님 아이디 찾기 메일입니다.");
                msg.setText("인증키는 "+strings[0]+"입니다. 본인과 관련없는 메일이라면 무시하시면 됩니다.");
                break;
            case findPassword:
                msg.setSubject("[TMONG CAFFE]"+user.getName()+"님 비밀번호 찾기 메일입니다.");
                msg.setText("찾으시는 비밀번호는 "+user.getPassword()+"입니다. 본인과 관련없는 메일이라면 무시하시면 됩니다.");
                break;
        }

        return msg;
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

        return userDao.getUserByUserId(id)!=null;
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
