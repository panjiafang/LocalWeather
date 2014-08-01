package com.rqpw.weather.db;

import android.content.Context;

/**
 * Created by Pan Jiafang on 2014/7/31.
 */
public class WindPreferrence extends BasePreference{


    public WindPreferrence(Context context) {
        super(context);

        file = "wind";
    }

    public void init(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("0", "无持续风向");
        editor.putString("1", "东北风");
        editor.putString("2", "东风");
        editor.putString("3", "东南风");
        editor.putString("4", "南风");
        editor.putString("5", "西南风");
        editor.putString("6", "西风");
        editor.putString("7", "西北风");
        editor.putString("8", "北风");
        editor.putString("9", "旋转风");
        editor.commit();
    }

    public String getWind(String key){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
