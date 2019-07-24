package com.internship.tmontica.util;

public class JsonUtil {

    public static String getJsonElementValue(String json, String key){

        return json.split(key)[1].split(",")[0].replaceAll("[\"{},:]","");
    }

    public static void main(String[] args) {
        String json = "{\"id\":\"string\",\"name\":\"string\",\"email\":\"string\",\"birthDate\":\"Wed May 03 09:00:00 KST 2017\",\"role\":\"string\",\"point\":\"0}";
        System.out.println(getJsonElementValue(json, "id"));
    }
}
