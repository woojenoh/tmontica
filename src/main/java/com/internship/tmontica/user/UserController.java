package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserPasswordCheckReqDTO;
import com.internship.tmontica.user.model.request.UserSignInReqDTO;
import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign_up")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignUpReqDTO userSignUpReqDTO){
        userService.signUp(userSignUpReqDTO);
        return new ResponseEntity<>("Sign up Success", HttpStatus.CREATED);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserSignInReqDTO userSignInReqDTO) {
        userService.signIn(userSignInReqDTO);
        // MAKE JWT TOKEN..
        return new ResponseEntity<>("Sign in Success", HttpStatus.OK);
    }

    @PutMapping("/")

//    @GetMapping("/{userId}")
//    public ResponseEntity<User> getUser(@PathVariable("userId")String id){
//        return new ResponseEntity<>(userService.getUserByUserId(id), HttpStatus.OK);
//    }

    @GetMapping("/idDuplicateCheck/{userId}")
    public ResponseEntity<Boolean> idDuplicateCheck(@PathVariable("userId")String id) {
        return new ResponseEntity<>(userService.isDuplicate(id), HttpStatus.OK);
    }

    @PostMapping("/check_password")
    public ResponseEntity<Boolean> passwordCheck(@RequestBody @Valid UserPasswordCheckReqDTO userPasswordCheckReqDTO){
        return new ResponseEntity<>(userService.checkPassword(userPasswordCheckReqDTO), HttpStatus.OK);
    }

}
