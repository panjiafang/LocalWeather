package com.rqpw.weather.db;

import android.content.Context;

/**
 * Created by Pan Jiafang on 2014/7/15.
 */
public class SettingPreference extends BasePreference {

    public SettingPreference(Context context) {
        super(context);
        file = "setting";
    }

//    public int getColor(){
//        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt("color", 0xFF000000);
//    }
//
//    public void saveColor(int color){
//        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        editor.putInt("color", color);
//        editor.commit();
//    }

    public int getCurrentBG(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("current_bg", 0x33111111);
    }

    public void saveCurrentBG(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("current_bg", color);
        editor.commit();
    }

    public int getCurrentFont(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("current_font", 0xFF000000);
    }
    public void saveCurrentFont(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("current_font", color);
        editor.commit();
    }

    public int getDefaultColor(){
        return 0x33111111;
    }

    public int getDayListBG(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("daylist_bg", 0x33111111);
    }

    public void saveDayListBG(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("daylist_bg", color);
        editor.commit();
    }

    public int getDayListFont(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("daylist_font", 0xFF000000);
    }
    public void saveDayListFont(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("daylist_font", color);
        editor.commit();
    }

    public int getAppBG(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("app_bg", 0xFFFFFFFF);
    }

    public void saveAppBG(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("app_bg", color);
        editor.commit();
    }

    public String getAppBGPicPath(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString("app_bg_pic", "");
    }

    public void saveAppBGPicPath(String path){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("app_bg_pic", path);
        editor.commit();
    }

    public boolean getShare2Weixin(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("share2weixin", true);
    }

    public void setShare2Weixin(boolean value){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("share2weixin", value);
        editor.commit();
    }
}
