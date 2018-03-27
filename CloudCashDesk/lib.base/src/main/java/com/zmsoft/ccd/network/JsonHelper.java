package com.zmsoft.ccd.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/10 14:10
 */
public class JsonHelper {

    public static String toJson(Map<String,Object> map){
        return new Gson().toJson(map);
    }

    public static String toJson(Object object){
        return new Gson().toJson(object);
    }

    public static String toJsonStr(Object src) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(src);
    }

    public static String jsonFormatter(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonString);
        return gson.toJson(je);
    }
}
