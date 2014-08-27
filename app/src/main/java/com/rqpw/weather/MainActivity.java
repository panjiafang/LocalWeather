package com.rqpw.weather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rqpw.weather.db.CityPreference;
import com.rqpw.weather.db.DBHelper;
import com.rqpw.weather.db.SettingPreference;
import com.rqpw.weather.entity.CityEntity;
import com.rqpw.weather.fragment.Weather;
import com.rqpw.weather.view.AddingActionView;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends FragmentActivity {

    private ViewPager viewpager;
    private FragmentPagerAdapter pagerAdapter;

    private TextView actionview_tv;
    private EditText actionview_et;
    private CityEntity cityEntity;

    private ArrayList<String> fragments;

    private FragmentManager fragmentManager;

    private HashMap<String, Weather> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        viewpager.setOffscreenPageLimit(7);

        FragmentManager.enableDebugLogging(true);

        fragmentManager = getSupportFragmentManager();
        fragments = new ArrayList<String>();
        fragmentMap = new HashMap<String, Weather>();

        addAlert();

//        getCitys();

        pagerAdapter = new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragmentMap.get(fragments.get(i));
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Weather weather = fragmentMap.get(fragments.get(position));
                if(!weather.isAdded())
                    fragmentManager.beginTransaction().add(container.getId(), weather, fragments.get(position)).commit();
                return weather;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                Weather weather = (Weather)object;
                fragments.remove(weather.area);
                fragmentMap.remove(weather.area);
                fragmentManager.beginTransaction().remove(weather).commit();
            }

            @Override
            public int getItemPosition(Object object) {
                if(!fragments.contains(((Weather)object).area))
                    return POSITION_NONE;
                else
                    return fragments.indexOf(((Weather)object).area);
            }
        };

        viewpager.setAdapter(pagerAdapter);

        SettingPreference settingPreference = new SettingPreference(this);
        String app_bg_path = settingPreference.getAppBGPicPath();
        if(app_bg_path.equals("")){
            int appbg = settingPreference.getAppBG();
            viewpager.setBackgroundColor(appbg);
        }
        else{
            Uri picUri = Uri.parse(app_bg_path);
            try {
                InputStream picIS = this.getContentResolver().openInputStream(picUri);
                Drawable bitmapDrawable = BitmapDrawable.createFromStream(picIS, null);
                viewpager.setBackgroundDrawable(bitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

        getCitys();
        if(pagerAdapter != null)
            pagerAdapter.notifyDataSetChanged();

        updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
    }

    public void addAlert(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);
        Intent intent = new Intent("com.rqpw.weather.cn.clear");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        if(calendar.getTimeInMillis() < System.currentTimeMillis())
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY*7, AlarmManager.INTERVAL_DAY*7, pendingIntent);
        else
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
    }

    public void addCity(String city){

        CityPreference cityPreference = new CityPreference(this);
        boolean result = cityPreference.addCity(city);
        if(result){
            Weather weather = new Weather();
            weather.setWeatherInfo(city);
            fragments.add(city);
            fragmentMap.put(city, weather);
            pagerAdapter.notifyDataSetChanged();
            viewpager.setCurrentItem(fragments.size() - 1);
        }
        else{
            Toast.makeText(this, "该城市已存在!", Toast.LENGTH_LONG).show();;
        }
    }

    public void getCitys(){
        CityPreference preference = new CityPreference(this);
        String arrayStr = preference.getCity();
        try {
            JSONArray array = new JSONArray(arrayStr);
            fragments.clear();
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String city = obj.getString("city");
                Weather weather = new Weather();
                weather.setWeatherInfo(city);
                fragments.add(city);
                fragmentMap.put(city, weather);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateUI(){
        for(int i = 0; i < fragments.size(); i++){
            Weather weather = fragmentMap.get(fragments.get(i));
            if(weather.isAdded())
                weather.updateUI();
        }
        SettingPreference settingPreference = new SettingPreference(this);
        String app_bg_path = settingPreference.getAppBGPicPath();
        if(app_bg_path.equals("")){
            int appbg = settingPreference.getAppBG();
            viewpager.setBackgroundColor(appbg);
        }
        else{
            Uri picUri = Uri.parse(app_bg_path);
            try {
                InputStream picIS = this.getContentResolver().openInputStream(picUri);
                Drawable bitmapDrawable = BitmapDrawable.createFromStream(picIS, null);
                viewpager.setBackgroundDrawable(bitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCitys(String area){
        fragments.remove(area);
        pagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_add);
        AddingActionView actionView = (AddingActionView) MenuItemCompat.getActionView(menuItem);
        actionview_et = actionView.et;
        actionview_tv = actionView.tv;
        actionview_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = actionview_et.getText().toString();
                String[] strs = str.split(",");
                if(str.trim().length() != 0 && strs.length > 2){
                    str = DBHelper.getInstance(MainActivity.this).getAreaCode(strs[0], strs[1], strs[2]);
                    if(!str.equals("")){
                        addCity(str);
                        actionview_et.setText("");
                        MenuItemCompat.collapseActionView(menuItem);
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "请输入城市拼音首字母,从结果中选择城市", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            if(fragmentManager.findFragmentByTag("setting") == null){
//                fragmentTransaction = fragmentManager.beginTransaction();
//                Setting fragment = new Setting();
//                fragmentTransaction.addToBackStack("setting");
//                fragmentTransaction.add(R.id.main_layout, fragment, "setting").commit();
//                return true;
//            }
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
