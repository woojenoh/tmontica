package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.*;
import com.internship.tmontica.user.model.response.UserFindIdRespDTO;
import com.internship.tmontica.user.model.response.UserInfoRespDTO;
import com.internship.tmontica.user.model.response.UserSignInRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignUpReqDTO userSignUpReqDTO) {

        userService.signUp(userSignUpReqDTO);
        return new ResponseEntity<>(UserResponseMessage.SIGN_UP_SUCCESS.getMessage(), HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<String> activateUser(@RequestParam("id")String userId, @RequestParam("token")String token){

        userService.activateUser(userId, token);
        return new ResponseEntity<>(UserResponseMessage.ACTIVATE_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/duplicate/{userId}")
    public ResponseEntity<String> idDuplicateCheck(@PathVariable("userId") String id) {

        userService.checkUserIdDuplicatedException(id);
        return new ResponseEntity<>(UserResponseMessage.USABLE_ID.getMessage(), HttpStatus.OK);
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

        userService.changePassword(userChangePasswordReqDTO);
        return new ResponseEntity<>(UserResponseMessage.PASSWORD_CHANGE_SUCCESS.getMessage(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> withDrawUser(@PathVariable("userId") String id){

        userService.withdrawUser(id);
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
    public ResponseEntity<UserFindIdRespDTO> findIdConfirm(@RequestBody @Valid UserFindIdReqDTO userFindIdReqDTO){

        UserFindIdRespDTO userFindIdRespDTO = userService.checkAuthCode(userFindIdReqDTO);
        return new ResponseEntity<>(userFindIdRespDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoRespDTO> getUserInfo(@PathVariable("userId") String id) {

        return new ResponseEntity<>(userService.getUserInfo(id), HttpStatus.OK);
    }
}
