package com.internship.tmontica.user;

import com.internship.tmontica.user.model.request.UserSignUpReqDTO;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Alias("user")
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
