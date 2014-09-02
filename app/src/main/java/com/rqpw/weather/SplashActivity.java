package com.rqpw.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;

import com.rqpw.weather.util.Utils;
import com.umeng.analytics.MobclickAgent;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;

/**
 * Created by Pan Jiafang on 2014/8/14.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Utils.Log(android_id);

        new AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... params) {

                Utils.initData(SplashActivity.this);

                AdManager.getInstance(SplashActivity.this).init("ff7f57a25283c764", "10c072fd636d6282", false);
                OffersManager.getInstance(SplashActivity.this).onAppLaunch();

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.execute("");

    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
    }
}
