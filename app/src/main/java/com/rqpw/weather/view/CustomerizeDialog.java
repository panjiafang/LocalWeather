package com.rqpw.weather.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.db.SettingPreference;
import com.rqpw.weather.util.Utils;

import java.util.Random;

/**
 * Created by Pan Jiafang on 2014/8/18.
 */
public class CustomerizeDialog extends Dialog implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    public static final String TYPE_CUR = "cur";
    public static final String TYPE_DAY = "day";

    private SeekBar sb_font_red, sb_font_green, sb_font_blue, sb_font_alpha, sb_bg_red, sb_bg_green, sb_bg_blue, sb_bg_alpha;
    private TextView tv_cur_effect, tv_day_effect;
    private RelativeLayout layout_cbg;

    private Button btn_cancel, btn_save, btn_font, btn_bg;

    private Activity context;
    private SettingPreference settingPreference;
    private String type;
    private int color_cfont, color_cbg, color_dfont, color_dbg, color_bg;

    public CustomerizeDialog(Activity context, String type) {
        super(context, R.style.dialog);

        this.context = context;
        this.type = type;

        init();
    }

    private void init() {
        setContentView(R.layout.dialog_custom);

        tv_cur_effect = (TextView) findViewById(R.id.setting_tv_cur_effect);
        tv_day_effect = (TextView) findViewById(R.id.setting_tv_day_effect);

        layout_cbg = (RelativeLayout) findViewById(R.id.setting_layout_effect);

        btn_cancel = (Button) findViewById(R.id.dialog_btn_cancel);
        btn_save = (Button) findViewById(R.id.dialog_btn_save);
        btn_font = (Button) findViewById(R.id.dialog_btn_random_font);
        btn_bg = (Button) findViewById(R.id.dialog_btn_random_bg);

        btn_bg.setOnClickListener(this);
        btn_font.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        sb_font_red = (SeekBar) findViewById(R.id.setting_seekbar_color_red);
        sb_font_green = (SeekBar) findViewById(R.id.setting_seekbar_color_green);
        sb_font_blue = (SeekBar) findViewById(R.id.setting_seekbar_color_blue);
        sb_font_alpha = (SeekBar) findViewById(R.id.setting_seekbar_color_alpha);

        sb_bg_red = (SeekBar) findViewById(R.id.setting_seekbar_red);
        sb_bg_green = (SeekBar) findViewById(R.id.setting_seekbar_green);
        sb_bg_blue = (SeekBar) findViewById(R.id.setting_seekbar_blue);
        sb_bg_alpha = (SeekBar) findViewById(R.id.setting_seekbar_alpha);

        sb_font_red.setOnSeekBarChangeListener(this);
        sb_font_green.setOnSeekBarChangeListener(this);
        sb_font_blue.setOnSeekBarChangeListener(this);
        sb_font_alpha.setOnSeekBarChangeListener(this);

        sb_bg_red.setOnSeekBarChangeListener(this);
        sb_bg_green.setOnSeekBarChangeListener(this);
        sb_bg_blue.setOnSeekBarChangeListener(this);
        sb_bg_alpha.setOnSeekBarChangeListener(this);

        settingPreference = new SettingPreference(context);
        if(type == null)
            return;
        else{
            color_cfont = settingPreference.getCurrentFont();
            color_cbg = settingPreference.getCurrentBG();
            color_dfont = settingPreference.getDayListFont();
            color_dbg = settingPreference.getDayListBG();
            color_bg = settingPreference.getAppBG();

            String app_pic_path = settingPreference.getAppBGPicPath();

            tv_cur_effect.setTextColor(color_cfont);
            tv_cur_effect.setBackgroundColor(color_cbg);

            tv_day_effect.setTextColor(color_dfont);
            tv_day_effect.setBackgroundColor(color_dbg);

            if(app_pic_path.equals("")) {
                layout_cbg.setBackgroundColor(color_bg);
            }
            else {
                Uri picUri = Uri.parse(app_pic_path);
                Bitmap bitmap = lessenUriImage(getPath(picUri));
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                layout_cbg.setBackgroundDrawable(bitmapDrawable);
            }

            if(type == TYPE_CUR){
                sb_font_red.setProgress(Color.red(color_cfont));
                sb_font_green.setProgress(Color.green(color_cfont));
                sb_font_blue.setProgress(Color.blue(color_cfont));
                sb_font_alpha.setProgress(Color.alpha(color_cfont));

                sb_bg_red.setProgress(Color.red(color_cbg));
                sb_bg_green.setProgress(Color.green(color_cbg));
                sb_bg_blue.setProgress(Color.blue(color_cbg));
                sb_bg_alpha.setProgress(Color.alpha(color_cbg));
            }
            else{
                sb_font_red.setProgress(Color.red(color_dfont));
                sb_font_green.setProgress(Color.green(color_dfont));
                sb_font_blue.setProgress(Color.blue(color_dfont));
                sb_font_alpha.setProgress(Color.alpha(color_dfont));

                sb_bg_red.setProgress(Color.red(color_dbg));
                sb_bg_green.setProgress(Color.green(color_dbg));
                sb_bg_blue.setProgress(Color.blue(color_dbg));
                sb_bg_alpha.setProgress(Color.alpha(color_dbg));
            }
        }

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar == sb_font_red || seekBar == sb_font_green || seekBar == sb_font_blue || seekBar == sb_font_alpha ){
            int color = Color.argb(sb_font_alpha.getProgress(), sb_font_red.getProgress(), sb_font_green.getProgress(), sb_font_blue.getProgress());

            if(type.equals(TYPE_CUR)){
                tv_cur_effect.setTextColor(color);
            }
            else{
                tv_day_effect.setTextColor(color);
            }
        }
        else{
            int color = Color.argb(sb_bg_alpha.getProgress(), sb_bg_red.getProgress(), sb_bg_green.getProgress(), sb_bg_blue.getProgress());
            if(type.equals(TYPE_CUR)){
                tv_cur_effect.setBackgroundColor(color);
            }
            else{
                tv_day_effect.setBackgroundColor(color);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        if(v == btn_cancel){
            dismiss();
        }
        else if(v == btn_save) {
            if(type != null){
                if(type.equals(TYPE_CUR)){
                    int color = Color.argb(sb_font_alpha.getProgress(), sb_font_red.getProgress(), sb_font_green.getProgress(), sb_font_blue.getProgress());
                    settingPreference.saveCurrentFont(color);
                    color = Color.argb(sb_bg_alpha.getProgress(), sb_bg_red.getProgress(), sb_bg_green.getProgress(), sb_bg_blue.getProgress());
                    settingPreference.saveCurrentBG(color);
                }
                else{
                    int color = Color.argb(sb_font_alpha.getProgress(), sb_font_red.getProgress(), sb_font_green.getProgress(), sb_font_blue.getProgress());
                    settingPreference.saveDayListFont(color);
                    color = Color.argb(sb_bg_alpha.getProgress(), sb_bg_red.getProgress(), sb_bg_green.getProgress(), sb_bg_blue.getProgress());
                    settingPreference.saveDayListBG(color);
                }
            }
            dismiss();
        }
        else if(v == btn_font){
            Random random = new Random();
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            int alpha = random.nextInt(56) + 200;

            Utils.Log(red+","+green+","+blue+","+alpha);

            sb_font_red.setProgress(red);
            sb_font_green.setProgress(green);
            sb_font_blue.setProgress(blue);
            sb_font_alpha.setProgress(alpha);

            int color = Color.argb(alpha, red, green, blue);
            if(type.equals(TYPE_CUR))
                tv_cur_effect.setTextColor(color);
            else
                tv_day_effect.setTextColor(color);
        }
        else if(v == btn_bg){
            Random random = new Random();
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            int alpha = random.nextInt(100) + 20;

            Utils.Log(red+","+green+","+blue+","+alpha);

            sb_bg_red.setProgress(red);
            sb_bg_green.setProgress(green);
            sb_bg_blue.setProgress(blue);
            sb_bg_alpha.setProgress(alpha);

            int color = Color.argb(alpha, red, green, blue);
            if(type.equals(TYPE_CUR))
                tv_cur_effect.setBackgroundColor(color);
            else
                tv_day_effect.setBackgroundColor(color);
        }
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
