package com.rqpw.weather.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.rqpw.weather.R;
import com.rqpw.weather.db.SettingPreference;

/**
 * Created by Pan Jiafang on 2014/8/27.
 */
public class WeixinSettingDialog extends Dialog {

    private Button btn_cancel;
    private CheckBox checkBox;

    private Activity context;
    private SettingPreference settingPreference;

    public WeixinSettingDialog(Activity context) {
        super(context, R.style.dialog);

        this.context = context;

        init();
    }

    private void init() {

        setContentView(R.layout.dialog_weixin);

        btn_cancel = (Button) findViewById(R.id.dialog_score_cancel);
        checkBox = (CheckBox) findViewById(R.id.dialog_weixin_cbox);

        settingPreference = new SettingPreference(context);
        checkBox.setChecked(settingPreference.getShare2Weixin());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingPreference.setShare2Weixin(isChecked);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
