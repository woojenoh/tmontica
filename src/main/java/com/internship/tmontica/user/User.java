package com.internship.tmontica.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class User {

    private String id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private Date birthDate;
    @NotNull
    private String password;
    private String role;
    private Date createdDate;
    private int point;
}
