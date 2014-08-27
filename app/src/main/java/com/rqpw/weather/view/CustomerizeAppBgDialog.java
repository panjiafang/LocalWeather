package com.rqpw.weather.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.db.SettingPreference;

import java.util.Random;

/**
 * Created by Pan Jiafang on 2014/8/22.
 */
public class CustomerizeAppBgDialog extends Dialog implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private SeekBar sb_red, sb_green, sb_blue;
    private RadioButton rb_color, rb_pic;
    private TextView tv_cur, tv_day;
    private RelativeLayout layout_bg;
    private Button btn_cancel, btn_save, btn_random;

    private Activity context;
    private SettingPreference settingPreference;

    private Intent intent;

    public CustomerizeAppBgDialog(Activity context) {
        super(context, R.style.dialog);

        this.context = context;

        init();
    }

    private void init() {
        setContentView(R.layout.dialog_custom_app);

        sb_red = (SeekBar) findViewById(R.id.setting_seekbar_app_red);
        sb_green = (SeekBar) findViewById(R.id.setting_seekbar_app_green);
        sb_blue = (SeekBar) findViewById(R.id.setting_seekbar_app_blue);

        rb_color = (RadioButton) findViewById(R.id.setting_rb_bg_color);
        rb_pic = (RadioButton) findViewById(R.id.setting_rb_bg_photo);

        tv_cur = (TextView) findViewById(R.id.dialog_app_cur_effect);
        tv_day = (TextView) findViewById(R.id.dialog_app_day_effect);

        layout_bg = (RelativeLayout) findViewById(R.id.dialog_app_bg);

        btn_cancel = (Button) findViewById(R.id.dialog_app_btn_cancel);
        btn_save = (Button) findViewById(R.id.dialog_app_btn_save);
        btn_random = (Button) findViewById(R.id.dialog_btn_random_font);

        sb_red.setOnSeekBarChangeListener(this);
        sb_green.setOnSeekBarChangeListener(this);
        sb_blue.setOnSeekBarChangeListener(this);

        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_random.setOnClickListener(this);

        rb_color.setOnClickListener(this);
        rb_pic.setOnClickListener(this);

        settingPreference = new SettingPreference(context);
        int current_bg = settingPreference.getCurrentBG();
        int cur_font = settingPreference.getCurrentFont();
        int daylist_bg = settingPreference.getDayListBG();
        int day_font = settingPreference.getDayListFont();

        tv_cur.setTextColor(cur_font);
        tv_cur.setBackgroundColor(current_bg);
        tv_day.setTextColor(day_font);
        tv_day.setBackgroundColor(daylist_bg);

        String app_pic_path = settingPreference.getAppBGPicPath();
        int app_bg = settingPreference.getAppBG();

        sb_red.setProgress(Color.red(app_bg));
        sb_green.setProgress(Color.green(app_bg));
        sb_blue.setProgress(Color.blue(app_bg));

        if(app_pic_path.equals("")) {
            rb_color.setChecked(true);
            layout_bg.setBackgroundColor(app_bg);
        }
        else {
            rb_pic.setChecked(true);
            Uri picUri = Uri.parse(app_pic_path);
            Bitmap bitmap = lessenUriImage(getPath(picUri));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            layout_bg.setBackgroundDrawable(bitmapDrawable);

            sb_red.setEnabled(false);
            sb_green.setEnabled(false);
            sb_blue.setEnabled(false);
        }
    }

    public void setImage(Intent intent){

        this.intent = intent;

        if(intent == null){
            rb_color.setChecked(true);

            SettingPreference settingPreference = new SettingPreference(context);
            if(settingPreference.getAppBGPicPath().equals(""))
                rb_color.setChecked(true);
        }
        else{

            rb_pic.setChecked(true);

            sb_red.setEnabled(false);
            sb_green.setEnabled(false);
            sb_blue.setEnabled(false);

            Uri picUri = intent.getData();
            Bitmap bitmap = lessenUriImage(getPath(picUri));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            layout_bg.setBackgroundDrawable(bitmapDrawable);

        }
    }

    @Override
    public void onClick(View v) {
        if(v == rb_color){

            rb_color.setChecked(true);

            sb_red.setEnabled(true);
            sb_green.setEnabled(true);
            sb_blue.setEnabled(true);

            int app_bg = settingPreference.getAppBG();
            sb_red.setProgress(Color.red(app_bg));
            sb_green.setProgress(Color.green(app_bg));
            sb_blue.setProgress(Color.blue(app_bg));

            layout_bg.setBackgroundColor(app_bg);
        }
        else if(v == rb_pic){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
            context.startActivityForResult(intent, 0);
        }
        else if(v == btn_cancel){
            dismiss();
        }
        else if(v == btn_save){
            dismiss();
            if(intent != null){
                settingPreference.saveAppBGPicPath(intent.getData().toString());
            }
            else{
                int color = Color.argb(255, sb_red.getProgress(), sb_green.getProgress(), sb_blue.getProgress());
                settingPreference.saveAppBG(color);
                settingPreference.saveAppBGPicPath("");
            }
        }
        else if(v == btn_random){
            sb_red.setEnabled(true);
            sb_green.setEnabled(true);
            sb_blue.setEnabled(true);
            rb_color.setChecked(true);

            Random random = new Random();
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);

            sb_red.setProgress(red);
            sb_green.setProgress(green);
            sb_blue.setProgress(blue);

            int color = Color.argb(255, red, green, blue);
            layout_bg.setBackgroundColor(color);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(rb_color.isChecked()){
            layout_bg.setBackgroundColor(Color.argb(255, sb_red.getProgress(), sb_green.getProgress(), sb_blue.getProgress()));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public String getPath(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA };
        Cursor cursor = context.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * use to lessen pic 50%
     * @param path sd card path
     * @return bitmap
     */
    public final static Bitmap lessenUriImage(String path)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = (int)(options.outHeight / (float)320);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
        bitmap=BitmapFactory.decodeFile(path,options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        System.out.println(w+" "+h); //after zoom
        return bitmap;
    }

}
