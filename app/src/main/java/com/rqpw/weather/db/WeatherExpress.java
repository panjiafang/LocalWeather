package com.rqpw.weather.db;

import android.content.Context;

/**
 * Created by Pan Jiafang on 2014/7/31.
 */
public class WeatherExpress extends BasePreference {
    public WeatherExpress(Context context) {
        super(context);
        file = "express";
    }

    public void init(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("00", "晴");
        editor.putString("01", "多云");
        editor.putString("02", "阴");
        editor.putString("03", "阵雨");
        editor.putString("04", "雷阵雨");
        editor.putString("05", "雷阵雨伴有冰雹");
        editor.putString("06", "雨夹雪");
        editor.putString("07", "小雨");
        editor.putString("08", "中雨");
        editor.putString("09", "大雨");
        editor.putString("10", "暴雨");
        editor.putString("11", "大暴雨");
        editor.putString("12", "特大暴雨");
        editor.putString("13", "阵雪");
        editor.putString("14", "小雪");
        editor.putString("15", "中雪");
        editor.putString("16", "大雪");
        editor.putString("17", "暴雪");
        editor.putString("18", "雾");
        editor.putString("19", "冻雨");
        editor.putString("20", "沙尘暴");
        editor.putString("21", "小到中雨");
        editor.putString("22", "中到大雨");
        editor.putString("23", "大到暴雨");
        editor.putString("24", "暴雨到大暴雨");
        editor.putString("25", "大暴雨到特大暴雨");
        editor.putString("26", "小到中雪");
        editor.putString("27", "中到大雪");
        editor.putString("28", "大到暴雪");
        editor.putString("29", "浮尘");
        editor.putString("30", "扬沙");
        editor.putString("31", "强沙尘暴");
        editor.putString("53", "霾");
        editor.putString("99", "无");

        editor.commit();
    }

    public String getExpress(String key){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
