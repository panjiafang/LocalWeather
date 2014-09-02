package com.rqpw.weather.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.db.SettingPreference;

import net.youmi.android.offers.PointsManager;

/**
 * Created by Pan Jiafang on 2014/8/27.
 */
public class ScoreDialog extends Dialog {

    private Button btn_cancel;
    private TextView tv_score;

    private Activity context;
    private SettingPreference settingPreference;

    public ScoreDialog(Activity context) {
        super(context, R.style.dialog);

        this.context = context;

        init();
    }

    private void init() {

        setContentView(R.layout.dialog_score);

        btn_cancel = (Button) findViewById(R.id.dialog_score_cancel);
        tv_score = (TextView) findViewById(R.id.dialog_score_tv_score);

        settingPreference = new SettingPreference(context);

        int myPointBalance = PointsManager.getInstance(context).queryPoints();
        tv_score.setText(""+myPointBalance);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
