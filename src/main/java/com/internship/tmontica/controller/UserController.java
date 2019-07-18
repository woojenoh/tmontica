package com.internship.tmontica.controller;

import com.internship.tmontica.dto.User;
import com.internship.tmontica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/check")
//    public boolean checkPassword(@RequestBody @Valid String password, @RequestBody @Valid String id){
//        String data = userService.getUserPasswordByUserId(id);
//        if(password.equals(data)){
//            return true;
//        }
//        return false;
//    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId")String id){
        User user = userService.getUserByUserId(id);
        return user;
    }

}
