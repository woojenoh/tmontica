package com.internship.tmontica.user.model.request;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class UserSignUpReqDTO {

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
}
