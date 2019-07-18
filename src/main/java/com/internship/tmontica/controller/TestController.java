package com.internship.tmontica.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "TestController", tags = {"tag1"})
public class TestController {

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ApiOperation(value = "Test API", notes = "Swagger test")
    public String test(){
        return "Swagger Test @@";
    }
}
