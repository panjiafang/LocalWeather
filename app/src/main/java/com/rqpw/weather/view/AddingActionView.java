package com.rqpw.weather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.adapter.Adapter_AutoCompleteView;
import com.rqpw.weather.entity.CityEntity;

/**
 * Created by Pan Jiafang on 2014/7/21.
 */
public class AddingActionView extends RelativeLayout {

    public AutoCompleteTextView et;
    public TextView tv;
    public CityEntity selectCity;
    private Adapter_AutoCompleteView adapter;
    private Context context;

    public AddingActionView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AddingActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AddingActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_adding, null);
        et = (AutoCompleteTextView) view.findViewById(R.id.view_adding_et);
        tv = (TextView) view.findViewById(R.id.view_adding_tv);

        adapter = new Adapter_AutoCompleteView(context);
        et.setAdapter(adapter);
        et.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectCity = (CityEntity) adapter.getItem(position);
                et.setText(selectCity.province+","+selectCity.city+","+selectCity.town);
            }
        });

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        addView(view, params);
    }
}
