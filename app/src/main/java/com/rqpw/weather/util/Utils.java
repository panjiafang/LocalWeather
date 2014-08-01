package com.rqpw.weather.util;

import android.content.Context;
import android.util.Log;

import com.rqpw.weather.db.DBHelper;
import com.rqpw.weather.db.WeatherExpress;
import com.rqpw.weather.db.WindPreferrence;
import com.rqpw.weather.entity.CityEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Pan Jiafang on 2014/7/23.
 */
public class Utils {

    public static void Log(String message){
        Log.e("pw", message);
    }

    public static void initData(Context context){
        try {
            DBHelper dbHelper = DBHelper.getInstance(context);
            if(!dbHelper.hasCity()){
                Utils.Log("read assets file");
                InputStream is = context.getAssets().open("result.csv");
                InputStreamReader isr = new InputStreamReader(is, "gbk");
                BufferedReader br = new BufferedReader(isr);
                String str;
                String[] strs;
                ArrayList<CityEntity> citys = new ArrayList<CityEntity>();
                CityEntity entity;

                while((str = br.readLine()) != null){
                    strs = str.split(",");

                    entity = new CityEntity();
                    entity.province = strs[0];
                    entity.areacode = strs[1];
                    entity.city = strs[2];
                    entity.town = strs[4];
                    entity.city_py = strs[5];
                    entity.town_py = strs[6];
                    citys.add(entity);
                }
                dbHelper.saveCitys(citys);
                br.close();
                isr.close();


                //添加其他数据
                WeatherExpress express = new WeatherExpress(context);
                express.init();

                WindPreferrence windPreferrence = new WindPreferrence(context);
                windPreferrence.init();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}
