package com.internship.tmontica.controller;

import com.internship.tmontica.dto.User;
import com.internship.tmontica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String addUser(@RequestBody User user){
        System.out.println("add User");
        userService.addUser(user);
        return "congratulation"; // 회원가입 성공 페이지 URI
    }
}
