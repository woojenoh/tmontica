package com.internship.tmontica.user;

import com.internship.tmontica.point.Point;
import com.internship.tmontica.point.PointLogType;
import com.internship.tmontica.point.PointService;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.user.exception.UserValidException;
import com.internship.tmontica.user.find.FindId;
import com.internship.tmontica.user.model.request.*;
import com.internship.tmontica.user.model.response.UserFindIdRespDTO;
import com.internship.tmontica.user.model.response.UserInfoRespDTO;
import com.internship.tmontica.user.model.response.UserSignInRespDTO;
import com.internship.tmontica.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PointService pointService;
    private static final String SIGN_UP_CELEBRATE_POINT = "1000";

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignUpReqDTO userSignUpReqDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            for(FieldError f : bindingResult.getFieldErrors()){
                System.out.println(f.getField() + " : " + f.getRejectedValue());
            }
            throw new UserValidException("User Sign-up Form", "회원가입 폼 데이터가 올바르지 않습니다.", bindingResult);
        }
        User user = modelMapper.map(userSignUpReqDTO, User.class);
        userService.signUp(user);
        return new ResponseEntity<>(UserResponseMessage.SIGN_UP_SUCCESS.getMessage(), HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<String> activateUser(@RequestParam("id")String userId, @RequestParam("token")String token){

        userService.activateUser(userId, token);
        Point point = new Point(userId, PointLogType.GET_POINT.getType(), SIGN_UP_CELEBRATE_POINT, "가입 축하 적립금.");
        pointService.updateUserPoint(point);
        return new ResponseEntity<>(UserResponseMessage.ACTIVATE_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/duplicate/{userId}")
    public ResponseEntity<String> idDuplicateCheck(@PathVariable("userId") String id) {

        userService.checkUserIdDuplicatedException(id);
        return new ResponseEntity<>(UserResponseMessage.USABLE_ID.getMessage(), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignInRespDTO> signIn(@RequestBody @Valid UserSignInReqDTO userSignInReqDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new UserValidException("User Sign-in Form", "로그인 폼 데이터가 올바르지 않습니다.", bindingResult);
        }
        User user = modelMapper.map(userSignInReqDTO, User.class);
        userService.signInCheck(user);
        UserSignInRespDTO userSignInRespDTO = userService.makeJwtToken(user);
        return new ResponseEntity<>(userSignInRespDTO, HttpStatus.OK);
    }

    @PostMapping("/checkpw")
    public ResponseEntity<String> checkPassword(@RequestBody @Valid UserCheckPasswordReqDTO userCheckPasswordReqDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new UserValidException("User Check-password Form", "비밀번호 확인 폼 데이터가 올바르지 않습니다.", bindingResult);
        }
        User user = modelMapper.map(userCheckPasswordReqDTO, User.class);
        user.setId(JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id"));
        userService.checkPassword(user);
        return new ResponseEntity<>(UserResponseMessage.PASSWORD_CHECK_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> changePassword(@RequestBody @Valid UserChangePasswordReqDTO userChangePasswordReqDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new UserValidException("User Change-password Form", "비밀번호 변경 폼 데이터가 올바르지 않습니다.", bindingResult);
        }
        User user = modelMapper.map(userChangePasswordReqDTO, User.class);
        userService.changePassword(user);
        return new ResponseEntity<>(UserResponseMessage.PASSWORD_CHANGE_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> withDrawUser(@PathVariable("userId") String id){

        String currentId = JsonUtil.getJsonElementValue(jwtService.getUserInfo("userInfo"), "id");
        userService.withdrawUser(id, currentId);
        return new ResponseEntity<>(UserResponseMessage.WITHDRAW_USER_SUCCESS.getMessage(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/findid")
    public ResponseEntity<String> findUserId(@RequestParam String email) {

        userService.sendUserId(email);
        return new ResponseEntity<>(UserResponseMessage.MAIL_SEND_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/findpw")
    public ResponseEntity<String> findUserPassword(@RequestParam String id, @RequestParam String email){

        userService.sendUserPassword(id, email);
        return new ResponseEntity<>(UserResponseMessage.MAIL_SEND_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @PostMapping("/findid/confirm")
    public ResponseEntity<UserFindIdRespDTO> findIdConfirm(@RequestBody @Valid UserFindIdReqDTO userFindIdReqDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            throw new UserValidException("User Find-id Form", "아이디 찾기 폼 데이터가 올바르지 않습니다.", bindingResult);
        }
        FindId findId = modelMapper.map(userFindIdReqDTO, FindId.class);
        UserFindIdRespDTO userFindIdRespDTO = userService.checkAuthCode(findId);
        return new ResponseEntity<>(userFindIdRespDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoRespDTO> getUserInfo(@PathVariable("userId") String id) {

        return new ResponseEntity<>(userService.getUserInfo(id), HttpStatus.OK);
    }
}
