package com.rqpw.weather.network;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * 中国天气网 WebAPI
 *
 * <pre>
 * http://open.weather.com.cn/data/?areaid=""&type=""&date=
 * ""&appid=""&key=".urlencode($key);
 * </pre>
 *
 * @author Vity
 */
public class WeatherKit {

    /**
     * 预警
     */
    // @Deprecated
    // public static final String Alarm = "alarm";

    /**
     * 实况
     */
    public static final String Observe = "observe";

    /**
     * 指数
     */
    public static final String Index = "index";

    /**
     * 常规预报（24小时）
     */
    public static final String Forecast3d = "forecast3d";

    /**
     * APP ID: xxx
     */
    static final String APP_ID = "e2b9fd";

    /**
     * 完整APP_ID
     */
    static final String FULL_APP_ID = "e2b9fd8302e80cbc";

    /**
     * PRIVATE KEY
     */
    static final String PRIVATE_KEY = "5a420e_SmartWeatherAPI_1e8f7db";

    /**
     * 固定访问地址
     */
    static final String API_URL = "http://open.weather.com.cn/data/";

    /**
     * PUBLIC KEY 模板
     */
    static final String PUBLIC_KEY_TMP = API_URL + "?areaid=%s&type=%s&date=%s&appid=%s";

    static final String REQUEST_URL = PUBLIC_KEY_TMP + "&key=%s";

    /**
     * 获取key参数
     *
     * @return
     */
    static String getKey(String areaId, String type, String data) {
        String publicKey = String.format(PUBLIC_KEY_TMP, areaId, type, data, FULL_APP_ID);
        try {
            return URLEncoder.encode(new String(Base64.encode(HmacSHA1Encrypt(publicKey, PRIVATE_KEY), Base64.NO_WRAP)), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取客户端时间
     *
     * @return
     */
    static String getDate(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmm").format(date);
    }

    /**
     * 加密数据
     *
     * hash_hmac('sha1',$public_key,$priva te_key,TRUE)
     *
     * @param encryptText
     * @param encryptKey
     * @return
     * @throws Exception
     */
    static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes("UTF-8");
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        return mac.doFinal(encryptText.getBytes("UTF-8"));
    }

    /**
     * 获取天气信息（当日）
     *
     * @param areacode
     * @return
     */
    public static String getWeatherInfo(String areacode, String type) {
        return getWeatherInfo(areacode, type, new Date());
    }

    /**
     * 获取天气信息
     *
     * @param areacode
     * @param date
     * @return
     */
    public static String getWeatherInfo(String areacode, String type,  Date date) {
        String dateStr = getDate(date);
        String url = String.format(REQUEST_URL, areacode, type, dateStr, APP_ID, getKey(areacode, type, dateStr));
        System.out.println("weather_url:::::::::::::::::::::" + url);
        return url;
    }
}
