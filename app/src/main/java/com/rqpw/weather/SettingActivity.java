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

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
        else if(v == btn_weixin){
//            Platform weixin = ShareSDK.getPlatform(this, Platform.);
            Platform[] p = ShareSDK.getPlatformList();

        }
    }

    public void showOnekeyshare(String platform, boolean silent) {
        OnekeyShare oks = new OnekeyShare();

        // 分享时Notification的图标和文字
        oks.setNotification(R.drawable.laucher,
                getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // text是分享文本，所有平台都需要这个字段
        oks.setText("自我天气,凸显自我");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(MainActivity.TEST_IMAGE);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://app.mi.com/detail/69770");
        // 是否直接分享（true则直接分享）
        oks.setSilent(silent);
        // 指定分享平台，和slient一起使用可以直接分享到指定的平台
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // 去除注释可通过OneKeyShareCallback来捕获快捷分享的处理结果
//        oks.setCallback(new OneKeyShareCallback());

        oks.show(this);
    }
}
