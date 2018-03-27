package com.zmsoft.ccd.data.spdata;

import android.content.Context;
import android.content.SharedPreferences;

import com.dfire.mobile.util.JsonMapper;
import com.google.gson.Gson;
import com.zmsoft.ccd.lib.bean.mistakes.Mistake;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jihuo on 2017/2/14.
 */

public class MistakeDataManager {

    private static final String MISTAKE_KEY = "mistake";

    public static List<Mistake> getMistakeData(Context context){
        List<Mistake> mistakes = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(MISTAKE_KEY, Context.MODE_PRIVATE);
        String data = sp.getString(MISTAKE_KEY, "");
        if (!StringUtils.isEmpty(data)){
            mistakes = JsonMapper.fromJsonArray(data, Mistake.class);
        }
        Iterator iterator = mistakes.iterator();
        while (iterator.hasNext()){
            Mistake mistake = (Mistake) iterator.next();
            long mistakeTime = mistake.getMistakeTime();
            //服务器传过来的数据可能是秒，需要转成毫秒
            if (String.valueOf(mistakeTime).length() == 10){
                mistakeTime = mistakeTime * 1000;
            }
            if (System.currentTimeMillis() - mistakeTime > 2*60*60*1000){
                iterator.remove();
            }
        }
        return mistakes;
    }

    public static void saveMistakeData(Context context, Mistake mistake){
        SharedPreferences.Editor editor = context.getSharedPreferences(MISTAKE_KEY, Context.MODE_PRIVATE).edit();
        List<Mistake> mistakes = getMistakeData(context);
        if (mistake != null){
            mistakes.add(0, mistake);
            Gson gson = new Gson();
            String result = gson.toJson(mistakes);
            editor.putString(MISTAKE_KEY, result);
            editor.apply();
        }
    }
}
