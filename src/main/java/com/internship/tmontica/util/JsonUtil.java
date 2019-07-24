package com.internship.tmontica.util;

public class JsonUtil {

    public static String getJsonElementValue(String json, String key){

        return json.split(key)[1].split(",")[0].replaceAll("[\"{},:]","");
    }
}
