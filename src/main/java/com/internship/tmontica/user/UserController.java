package com.internship.tmontica.user;

import com.internship.tmontica.security.JwtInterceptor;
import com.internship.tmontica.security.JwtService;
import com.internship.tmontica.user.model.request.*;
import com.internship.tmontica.user.model.response.UserInfoRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
            return new ResponseEntity<>("Sign up Success", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Sign up Fail", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/duplicate/{userId}")
    public ResponseEntity<Boolean> idDuplicateCheck(@PathVariable("userId") String id) {

        if(userService.idDuplicateCheck(id)){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserSignInReqDTO userSignInReqDTO, HttpServletResponse response, HttpSession session) {
        userService.signIn(userSignInReqDTO, session);
        userService.makeJwtToken(userSignInReqDTO, response);
        return new ResponseEntity<>("Sign in Success", HttpStatus.OK);
    }

    @PostMapping("/checkpw")
    public ResponseEntity<String> checkPassword(@RequestBody @Valid UserCheckPasswordReqDTO userCheckPasswordReqDTO, HttpSession session) {
        userService.checkPassword(userCheckPasswordReqDTO, session);
        return new ResponseEntity<>("Correct password", HttpStatus.OK);
    }
    @GetMapping("/help/find-id")
    public ResponseEntity<String> findUserId(@RequestParam String email, HttpSession httpSession) {

        if(userService.sendUserId(email, httpSession)){
            return new ResponseEntity<>("Send email Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("등록되지 않은 사용자입니다.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/help/find-pw")
    public ResponseEntity<String> findUserPassword(@RequestParam String id, @RequestParam String email){

        if(userService.sendUserPassword(id, email)){
            return new ResponseEntity<>("Send email Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Send email Fail", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoRespDTO> getUserInfo(@PathVariable("userId") String id) {
        return new ResponseEntity<>(userService.getUserInfo(id), HttpStatus.OK);
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

    //    @PostMapping("/check-email-confirm")
//    public ResponseEntity<Boolean> checkEmailConfirm() {
//        //미구현
//        return new ResponseEntity<>(true, HttpStatus.OK);
//    }
//
}
