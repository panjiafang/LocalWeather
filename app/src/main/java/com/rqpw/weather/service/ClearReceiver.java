package com.rqpw.weather.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.rqpw.weather.db.SettingPreference;
import com.rqpw.weather.util.Utils;

/**
 * Created by Pan Jiafang on 2014/8/22.
 */
public class ClearReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.Log("recevier");
        SettingPreference settingPreference = new SettingPreference(context);
        settingPreference.saveCurrentBG(settingPreference.getDefaultColor());
        settingPreference.saveCurrentFont(Color.argb(255, 0, 0, 0));
        settingPreference.saveDayListBG(settingPreference.getDefaultColor());
        settingPreference.saveDayListFont(Color.argb(255, 0, 0, 0));
        settingPreference.saveAppBG(Color.argb(255, 255, 255, 255));
    }
}
