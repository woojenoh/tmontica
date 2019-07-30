package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.*;
import com.internship.tmontica.user.model.response.UserFindIdRespDTO;
import com.internship.tmontica.user.model.response.UserInfoRespDTO;
import com.internship.tmontica.user.model.response.UserSignInRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignUpReqDTO userSignUpReqDTO) {

        if(userService.signUp(userSignUpReqDTO)){
            return new ResponseEntity<>(UserResponseMessage.SIGN_UP_SUCCESS.getMessage(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(UserResponseMessage.SIGN_UP_FAIL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/active")
    public ResponseEntity<String> activateUser(@RequestParam("id")String userId, @RequestParam("token")String token){

        if(userService.activateUser(userId, token)){
            return new ResponseEntity<>(UserResponseMessage.ACTIVATE_SUCCESS.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(UserResponseMessage.ACTIVATE_FAIL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/duplicate/{userId}")
    public ResponseEntity<Boolean> idDuplicateCheck(@PathVariable("userId") String id) {

        if(userService.idDuplicateCheck(id)){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignInRespDTO> signIn(@RequestBody @Valid UserSignInReqDTO userSignInReqDTO) {
        userService.signIn(userSignInReqDTO);
        UserSignInRespDTO userSignInRespDTO = userService.makeJwtToken(userSignInReqDTO);
        return new ResponseEntity<>(userSignInRespDTO, HttpStatus.OK);
    }

    @PostMapping("/checkpw")
    public ResponseEntity<String> checkPassword(@RequestBody @Valid UserCheckPasswordReqDTO userCheckPasswordReqDTO) {
        userService.checkPassword(userCheckPasswordReqDTO);
        return new ResponseEntity<>(UserResponseMessage.PASSWORD_CHECK_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> changePassword(@RequestBody @Valid UserChangePasswordReqDTO userChangePasswordReqDTO) {
        if(userService.changePassword(userChangePasswordReqDTO)){
            return new ResponseEntity<>(UserResponseMessage.PASSWORD_CHANGE_SUCCESS.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(UserResponseMessage.PASSWORD_CHANGE_FAIL.getMessage(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> withDrawUser(@PathVariable("userId") String id){
        if(userService.withdrawUser(id)){
            return new ResponseEntity<>(UserResponseMessage.WITHDRAW_USER_SUCCESS.getMessage(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(UserResponseMessage.WITHDRAW_USER_FAIL.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findid")
    public ResponseEntity<String> findUserId(@RequestParam String email) {

        if(userService.sendUserId(email)){
            return new ResponseEntity<>(UserResponseMessage.MAIL_SEND_SUCCESS.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(UserResponseMessage.MAIL_SEND_FAIL.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/findpw")
    public ResponseEntity<String> findUserPassword(@RequestParam String id, @RequestParam String email){

        if(userService.sendUserPassword(id, email)){
            return new ResponseEntity<>(UserResponseMessage.MAIL_SEND_SUCCESS.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(UserResponseMessage.MAIL_SEND_FAIL.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/findid/confirm")
    public ResponseEntity<UserFindIdRespDTO> findIdConfirm(@RequestBody @Valid UserFindIdReqDTO userFindIdReqDTO){

        UserFindIdRespDTO userFindIdRespDTO = userService.checkAuthCode(userFindIdReqDTO);
        if(userFindIdRespDTO.isSuccess()){
            return new ResponseEntity<>(userFindIdRespDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(userFindIdRespDTO, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoRespDTO> getUserInfo(@PathVariable("userId") String id) {
        return new ResponseEntity<>(userService.getUserInfo(id), HttpStatus.OK);
    }

    //    @PostMapping("/check-email-confirm")
//    public ResponseEntity<Boolean> checkEmailConfirm() {
//        //미구현
//        return new ResponseEntity<>(true, HttpStatus.OK);
//    }
//
}
