package com.internship.tmontica.user.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserTokenInfoDTO {

    private String id;
    private String name;
    private String email;
    private Date birthDate;
    private String role;
    private int point;

    public String toJson(){

        StringBuilder stringBuilder = new StringBuilder();
        makeJsonObjectStart("id", id, stringBuilder);
        makeJsonElement("name", name, stringBuilder, false);
        makeJsonElement("email", email, stringBuilder, false);
        makeJsonElement("birthDate", birthDate.toString(), stringBuilder, false);
        makeJsonElement("role", role, stringBuilder, false);
        makeJsonElement("point",Integer.toString(point), stringBuilder, true);

        return stringBuilder.toString();
    }

    private void makeJsonObjectStart(String type, String value, StringBuilder stringBuilder){
        stringBuilder.append("{");
        makeJsonElement(type, value, stringBuilder, false);
    }

    private void makeJsonElement(String type, String value, StringBuilder stringBuilder, boolean isLast){

        stringBuilder.append("\"");
        stringBuilder.append(type);
        stringBuilder.append("\":\"");
        stringBuilder.append(value);
        if(!isLast) {
            stringBuilder.append("\",");
            return;
        }
        stringBuilder.append("}");
    }
}
