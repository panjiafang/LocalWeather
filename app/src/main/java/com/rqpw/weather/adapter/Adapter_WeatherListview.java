package com.rqpw.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.db.SettingPreference;
import com.rqpw.weather.db.WeatherExpress;
import com.rqpw.weather.db.WindPreferrence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Pan Jiafang on 2014/7/14.
 */
public class Adapter_WeatherListview extends BaseAdapter {

    private Context context;
    private JSONArray data;
    private String time;

    private WeatherExpress weatherExpress;
    private WindPreferrence windPreferrence;

    private SettingPreference settingPreference;

    private SimpleDateFormat in;
    private SimpleDateFormat out;

    private String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    private Calendar calendar;

    public Adapter_WeatherListview(Context context, JSONArray data, String time){
        this.context = context;
        this.data = data;
        this.time = time;
        weatherExpress = new WeatherExpress(context);
        windPreferrence = new WindPreferrence(context);
        settingPreference = new SettingPreference(context);
        in = new SimpleDateFormat("yyyyMMddHHmm");
        out = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static ViewHolder viewHolder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_weather_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.item_weather_lisview_time);
            viewHolder.tv_day = (TextView) convertView.findViewById(R.id.item_weather_tv_day);
            viewHolder.tv_night = (TextView) convertView.findViewById(R.id.item_weather_tv_night);
            viewHolder.tv_day_degree = (TextView) convertView.findViewById(R.id.item_weather_listview_daydegress);
            viewHolder.tv_day_express = (TextView) convertView.findViewById(R.id.item_weather_listview_dayexpress);
            viewHolder.tv_day_wind = (TextView) convertView.findViewById(R.id.item_weather_listview_daywind);
            viewHolder.tv_night_degree = (TextView) convertView.findViewById(R.id.item_weather_listview_nightdegress);
            viewHolder.tv_night_express = (TextView) convertView.findViewById(R.id.item_weather_listview_nightexpress);
            viewHolder.tv_night_wind = (TextView) convertView.findViewById(R.id.item_weather_listview_nightwind);
            viewHolder.layout_day = (TableRow) convertView.findViewById(R.id.item_weather_listview_row_day);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            JSONObject obj = data.getJSONObject(position);

            String dayexpress = obj.getString("fa");//白天气象
            String nightexpress = obj.getString("fb");//夜间气象
            String daydegree = obj.getString("fc");//白天温度
            String nightdegree = obj.getString("fd");//夜间温度
            String daywind = obj.getString("fe");//白天风向
            String nightwind = obj.getString("ff");//夜间风向
            String daywindspeed = obj.getString("fg");//白天风速
            String nightwindspeed = obj.getString("fh");//夜间风速

            if(position == 0)
                viewHolder.layout_day.setVisibility(View.GONE);
            else
                viewHolder.layout_day.setVisibility(View.VISIBLE);

            int color = settingPreference.getDayListFont();

            calendar = Calendar.getInstance();
            try {
                calendar.setTime(in.parse(time));
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + position);
                String time = out.format(calendar.getTime());
                int week = calendar.get(Calendar.DAY_OF_WEEK);
                viewHolder.tv_date.setText(time+" "+weeks[week - 1]);
                viewHolder.tv_date.setTextColor(color);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            viewHolder.tv_day_degree.setText(daydegree+"°");
            viewHolder.tv_day_express.setText(weatherExpress.getExpress(dayexpress));
            viewHolder.tv_day_wind.setText(windPreferrence.getWind(daywind)+daywindspeed+"级");
            viewHolder.tv_night_degree.setText(nightdegree+"°");
            viewHolder.tv_night_express.setText(weatherExpress.getExpress(nightexpress));
            viewHolder.tv_night_wind.setText(windPreferrence.getWind(nightwind) + nightwindspeed + "级");

            viewHolder.tv_day_degree.setTextColor(color);
            viewHolder.tv_day_express.setTextColor(color);
            viewHolder.tv_day_wind.setTextColor(color);
            viewHolder.tv_night_degree.setTextColor(color);
            viewHolder.tv_night_express.setTextColor(color);
            viewHolder.tv_night_wind.setTextColor(color);
            viewHolder.tv_day.setTextColor(color);
            viewHolder.tv_night.setTextColor(color);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder{
        TextView tv_date, tv_day_degree, tv_day_express, tv_day_wind, tv_night_degree, tv_night_express, tv_night_wind, tv_day, tv_night;
        TableRow layout_day;
    }

}
