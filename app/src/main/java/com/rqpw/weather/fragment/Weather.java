package com.rqpw.weather.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rqpw.weather.R;
import com.rqpw.weather.adapter.Adapter_WeatherListview;
import com.rqpw.weather.db.CityPreference;
import com.rqpw.weather.db.DBHelper;
import com.rqpw.weather.db.SettingPreference;
import com.rqpw.weather.db.WeatherExpress;
import com.rqpw.weather.db.WeatherPreference;
import com.rqpw.weather.db.WindPreferrence;
import com.rqpw.weather.network.NetWorkUtil;
import com.rqpw.weather.network.WeatherKit;
import com.rqpw.weather.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Pan Jiafang on 2014/7/14.
 */
public class Weather extends Fragment implements View.OnClickListener {

    private TextView tv_temp, tv_desc, tv_shidu, tv_wind, tv_time, tv_area;
    private ImageView iv_sync;
    private ListView listView;
    private ProgressBar progressBar;

    private RelativeLayout layout_current;

    private ProgressBar progressBar_load;

    private GetCurInfoTask task;

    public String area;
    private SettingPreference settingPreference;
    private WeatherPreference weatherPreference;
    private WeatherExpress weatherExpress;
    private WindPreferrence windPreferrence;

    private Adapter_WeatherListview adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, null);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        init(view);

        return view;
    }

    private void init(View view){
        tv_temp = (TextView) view.findViewById(R.id.weather_tv_temp);
        tv_desc = (TextView) view.findViewById(R.id.weather_tv_desc);
        tv_shidu = (TextView) view.findViewById(R.id.weather_tv_shidu);
        tv_wind = (TextView) view.findViewById(R.id.weather_tv_wind);
        tv_time = (TextView) view.findViewById(R.id.weather_tv_time);
        tv_area = (TextView) view.findViewById(R.id.weather_tv_area);
        iv_sync = (ImageView) view.findViewById(R.id.weather_iv_sync);
        iv_sync.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.weather_listview);
        layout_current = (RelativeLayout) view.findViewById(R.id.weather_layout_current);
        progressBar = (ProgressBar) view.findViewById(R.id.weather_area_progressbar);
        progressBar_load = (ProgressBar) view.findViewById(R.id.weather_progressbar_load);

        settingPreference = new SettingPreference(getActivity());
        weatherPreference = new WeatherPreference(getActivity());
        weatherExpress = new WeatherExpress(getActivity());
        windPreferrence = new WindPreferrence(getActivity());

        int curbg = settingPreference.getCurrentBG();
        int daylistbg = settingPreference.getDayListBG();
        int color = settingPreference.getColor();

        tv_temp.setTextColor(color);
        tv_desc.setTextColor(color);
        tv_shidu.setTextColor(color);
        tv_wind.setTextColor(color);
        tv_time.setTextColor(color);
        tv_area.setTextColor(color);

        layout_current.setBackgroundColor(curbg);
        listView.setBackgroundColor(daylistbg);

        task = new GetCurInfoTask();
        task.execute(area);

        new Get3dWeatherInfo().execute(area);
    }

    public void setWeatherInfo(String area){
        this.area = area;
    }

    public void updateUI(){
        settingPreference = new SettingPreference(getActivity());
        int curbg = settingPreference.getCurrentBG();
        int daylistbg = settingPreference.getDayListBG();
        int color = settingPreference.getColor();

        tv_temp.setTextColor(color);
        tv_desc.setTextColor(color);
        tv_shidu.setTextColor(color);
        tv_wind.setTextColor(color);
        tv_time.setTextColor(color);
        tv_area.setTextColor(color);

        adapter = (Adapter_WeatherListview) listView.getAdapter();
        if(adapter != null)
            adapter.notifyDataSetChanged();

        layout_current.setBackgroundColor(curbg);
        listView.setBackgroundColor(daylistbg);
    }

    @Override
    public void onClick(View v) {
        if(v == iv_sync){
            progressBar_load.setVisibility(View.VISIBLE);
            task = new GetCurInfoTask();
            task.execute(area);

            new Get3dWeatherInfo().execute(area);
        }
    }

    private class GetCurInfoTask extends AsyncTask<String, String, String>{
        private String area;
        @Override
        protected String doInBackground(String... params) {
            area = params[0];
            return NetWorkUtil.getWeatherInfo(area, WeatherKit.Observe);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar_load.setVisibility(View.GONE);

            if(!isCancelled() && result != null){
                Utils.Log(result);

                try {
                    JSONObject obj = new JSONObject(result);

                    weatherPreference.saveWeather(area, obj.toString());

                    if(obj.has("l")){
                        obj = obj.getJSONObject("l");

                        tv_temp.setText(obj.getString("l1")+"°");

                        tv_shidu.setText("湿度:"+obj.getString("l2"));

                        String direct = obj.getString("l4");
                        String speed = "风力"+obj.getString("l3")+"级";
                        direct = windPreferrence.getWind(direct);

                        if(direct.equals("")) {
                            windPreferrence.init();
                            direct = windPreferrence.getWind(direct);
                        }
                        tv_wind.setText(direct + " " + speed);

                        tv_time.setText("更新时间  "+ obj.getString("l7"));

                        tv_area.setText(DBHelper.getInstance(getActivity()).getCityName(area));
                        progressBar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Get3dWeatherInfo extends AsyncTask<String, String, String>{
        private String area;
        @Override
        protected String doInBackground(String... params) {
            area = params[0];
            return NetWorkUtil.getWeatherInfo(area, WeatherKit.Forecast3d);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(!isCancelled() && result != null){
                Utils.Log(result);

                day3data(result);
            }
        }
    }

    private void day3data(String result){
        try {
            JSONObject obj = new JSONObject(result);
            if(obj.has("f")){
                obj = obj.getJSONObject("f");
                String time = obj.getString("f0");
                JSONArray array = obj.getJSONArray("f1");

                //当天情况
                obj = array.getJSONObject(0);
                String dayexpress = obj.getString("fa");//白天气象
                String nightexpress = obj.getString("fb");//夜间气象

                tv_desc.setText(dayexpress.equals("") ? weatherExpress.getExpress(nightexpress) : weatherExpress.getExpress(dayexpress));

                adapter = new Adapter_WeatherListview(getActivity(), array, time);
                listView.setAdapter(adapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
