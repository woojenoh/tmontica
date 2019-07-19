package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody UserSignUpReqDTO userSignUpReqDTO){
        userService.addUser(userSignUpReqDTO);
        return new ResponseEntity<>("Sign up Success", HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId")String id){
        return userService.getUserByUserId(id);
    }

    /*@GetMapping("/idDuplicateCheck")
    public ResponseEntity<Boolean> idDuplicateCheck(String Id) {
        return new ResponseEntity<>(userService.isDuplicate(), HttpStatus.OK);
    }
*/
}
