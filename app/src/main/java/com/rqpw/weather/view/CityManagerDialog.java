package com.rqpw.weather.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.adapter.Adapter_SettingCity;
import com.rqpw.weather.db.CityPreference;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Pan Jiafang on 2014/8/27.
 */
public class CityManagerDialog extends Dialog {

    private Button btn_cancel;
    private ListView listView;
    private TextView tv_none;

    private Activity context;

    public CityManagerDialog(Activity context) {
        super(context, R.style.dialog);

        this.context = context;

        init();
    }

    private void init() {

        setContentView(R.layout.dialog_citymgmt);

        btn_cancel = (Button) findViewById(R.id.dialog_city_cancel);
        listView = (ListView) findViewById(R.id.dialog_city_listview);
        tv_none = (TextView) findViewById(R.id.dialog_tv_none);

        CityPreference cityPreference = new CityPreference(context);
        String cityStr = cityPreference.getCity();
        if(!cityStr.equals("")){
            try {
                JSONArray array = new JSONArray(cityStr);
                Adapter_SettingCity adapter_settingCity = new Adapter_SettingCity(context, array);

                if(array.length() > 0)
                    tv_none.setVisibility(View.GONE);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                listView.setAdapter(adapter_settingCity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
