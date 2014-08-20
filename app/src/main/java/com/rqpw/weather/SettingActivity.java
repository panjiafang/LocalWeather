package com.rqpw.weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rqpw.weather.view.CustomerizeDialog;

/**
 * Created by Pan Jiafang on 2014/8/15.
 */
public class SettingActivity extends ActionBarActivity implements View.OnClickListener {

    private RelativeLayout layout_bg;
    private Button btn_cur, btn_daylist, btn_app, btn_weixin, btn_tuijian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        btn_cur = (Button) findViewById(R.id.setting_btn_cur);
        btn_daylist = (Button) findViewById(R.id.setting_btn_daylist);
        btn_app = (Button) findViewById(R.id.setting_btn_app);
        btn_weixin = (Button) findViewById(R.id.setting_btn_weixin);
        btn_tuijian = (Button) findViewById(R.id.setting_btn_tuijian);

        btn_cur.setOnClickListener(this);
        btn_daylist.setOnClickListener(this);
        btn_app.setOnClickListener(this);
        btn_weixin.setOnClickListener(this);
        btn_tuijian.setOnClickListener(this);

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
            CustomerizeDialog dialog = new CustomerizeDialog(this);
            dialog.show();
        }
        else if(v == btn_cur){

        }
        else if(v == btn_cur){

        }
        else if(v == btn_cur){

        }
        else if(v == btn_cur){

        }
    }
}
