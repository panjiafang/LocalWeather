package com.rqpw.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rqpw.weather.view.CityManagerDialog;
import com.rqpw.weather.view.CustomerizeAppBgDialog;
import com.rqpw.weather.view.CustomerizeDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Pan Jiafang on 2014/8/15.
 */
public class SettingActivity extends ActionBarActivity implements View.OnClickListener {

    private RelativeLayout layout_bg;
    private Button btn_cur, btn_daylist, btn_app, btn_weixin, btn_tuijian, btn_city;

    private CustomerizeAppBgDialog appDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        init();
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

    private void init() {
        btn_cur = (Button) findViewById(R.id.setting_btn_cur);
        btn_daylist = (Button) findViewById(R.id.setting_btn_daylist);
        btn_app = (Button) findViewById(R.id.setting_btn_app);
        btn_city = (Button) findViewById(R.id.setting_btn_city);
        btn_weixin = (Button) findViewById(R.id.setting_btn_weixin);
        btn_tuijian = (Button) findViewById(R.id.setting_btn_tuijian);

        btn_cur.setOnClickListener(this);
        btn_daylist.setOnClickListener(this);
        btn_app.setOnClickListener(this);
        btn_city.setOnClickListener(this);
        btn_weixin.setOnClickListener(this);
        btn_tuijian.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(appDialog != null && appDialog.isShowing()){
                appDialog.setImage(data);
            }
        }
        else{
            if(appDialog != null && appDialog.isShowing()){
                appDialog.setImage(null);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == btn_cur){
            CustomerizeDialog dialog = new CustomerizeDialog(this, CustomerizeDialog.TYPE_CUR);
            dialog.show();
        }
        else if(v == btn_daylist){
            CustomerizeDialog dialog = new CustomerizeDialog(this, CustomerizeDialog.TYPE_DAY);
            dialog.show();
        }
        else if(v == btn_app){
            appDialog = new CustomerizeAppBgDialog(this);
            appDialog.show();
        }
        else if(v == btn_city){
            CityManagerDialog dialog = new CityManagerDialog(this);
            dialog.show();
        }
        else if(v == btn_cur){

        }
    }
}
