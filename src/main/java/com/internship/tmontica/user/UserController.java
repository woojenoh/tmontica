package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody UserSignUpReqDTO userSignUpReqDTO){
        userService.addUser(userSignUpReqDTO);
        return new ResponseEntity<>("Sign up Success", HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId")String id){
        User user = userService.getUserByUserId(id);
        return user;
    }

    /*@GetMapping("/idDuplicateCheck")
    public ResponseEntity<Boolean> idDuplicateCheck(String Id) {
        return new ResponseEntity<>(userService.isDuplicate(), HttpStatus.OK);
    }
*/


}
