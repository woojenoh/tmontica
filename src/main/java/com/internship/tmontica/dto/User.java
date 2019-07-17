package com.internship.tmontica.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class User {

    private String id;
    private String name;
    private String email;
    private Date birthDate;
    private String password;
    private String role;
    private Date createdDate;
    private int point;

}
