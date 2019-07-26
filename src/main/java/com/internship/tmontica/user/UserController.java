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
            return new ResponseEntity<>("Sign up success need Activate with mail", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Sign up error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/active")
    public ResponseEntity<String> activateUser(@RequestParam("id")String userId, @RequestParam("token")String token){

        if(userService.activateUser(userId, token)){
            return new ResponseEntity<>("Activate Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Fail to Activate", HttpStatus.INTERNAL_SERVER_ERROR);
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
        return new ResponseEntity<>("Correct password", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> changePassword(@RequestBody @Valid UserChangePasswordReqDTO userChangePasswordReqDTO) {
        if(userService.changePassword(userChangePasswordReqDTO)){
            return new ResponseEntity<>("change password Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("change password Fail", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> withDrawUser(@PathVariable("userId") String id){
        if(userService.withDrawUser(id)){
            return new ResponseEntity<>("Delete User Success", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Delete User Fail", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findid")
    public ResponseEntity<String> findUserId(@RequestParam String email, HttpSession httpSession) {

        if(userService.sendUserId(email, httpSession)){
            return new ResponseEntity<>("Send email Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("이메일 정보가 없어서 메일은 발송하지 않았습니다.", HttpStatus.OK);
    }

    @GetMapping("/findpw")
    public ResponseEntity<String> findUserPassword(@RequestParam String id, @RequestParam String email){

        if(userService.sendUserPassword(id, email)){
            return new ResponseEntity<>("Send email Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Send email Fail", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/findid/confirm")
    public ResponseEntity<UserFindIdRespDTO> findIdConfirm(@RequestBody @Valid UserFindIdReqDTO userFindIdReqDTO, HttpSession session){

        UserFindIdRespDTO userFindIdRespDTO = userService.checkAuthCode(userFindIdReqDTO, session);
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
