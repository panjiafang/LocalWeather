package com.rqpw.weather.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rqpw.weather.entity.CityEntity;

import java.util.ArrayList;

/**
 * Created by Pan Jiafang on 2014/7/29.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "weather.db";
    private static int DB_VERSION = 6;

    private SQLiteDatabase sqLiteDatabase;

    private static DBHelper dbHelper = null;

    private DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context){
        if(dbHelper == null)
            dbHelper = new DBHelper(context);
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists citys(");
        sb.append("id Integer primary key autoincrement,");
        sb.append("province varchar(50),");
        sb.append("areacode varchar(20),");
        sb.append("city varchar(50),");
        sb.append("town varchar(50),");
        sb.append("city_py varchar(20),");
        sb.append("town_py varchar(20)");
        sb.append(")");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.delete("citys", null, null);
    }

    public void saveCitys(ArrayList<CityEntity> citys){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        String sql;
        for (CityEntity entity : citys){
            sql = "insert into citys(province, areacode, city, town, city_py, town_py) values(";
            sql += "'"+entity.province +"',";
            sql += "'"+entity.areacode+"',";
            sql += "'"+entity.city+"',";
            sql += "'"+entity.town+"',";
            sql += "'"+entity.city_py+"',";
            sql += "'"+entity.town_py +"')";
            sqLiteDatabase.execSQL(sql);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public boolean hasCity(){
        int count;
        sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "select * from citys";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        count = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return count > 1 ? true : false;
    }

    public ArrayList<CityEntity> getCityByPY(String py){

        ArrayList<CityEntity> citys = new ArrayList<CityEntity>();
        CityEntity cityEntity;
        sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "select * from citys where town_py like '%"+py+"%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()){
            cityEntity = new CityEntity();
            cityEntity.province = cursor.getString(cursor.getColumnIndex("province"));
            cityEntity.areacode = cursor.getString(cursor.getColumnIndex("areacode"));
            cityEntity.city = cursor.getString(cursor.getColumnIndex("city"));
            cityEntity.town = cursor.getString(cursor.getColumnIndex("town"));
            cityEntity.city_py = cursor.getString(cursor.getColumnIndex("city_py"));
            cityEntity.town_py = cursor.getString(cursor.getColumnIndex("town_py"));
            citys.add(cityEntity);
        }
        cursor.close();
        sqLiteDatabase.close();
        return citys;
    }

    public String getAreaCode(String province, String city, String town){
        String areacode = "";
        sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "select areacode from citys where province = '"+province+"' and city = '"+city+"' and town = '"+town+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()){
            areacode = cursor.getString(cursor.getColumnIndex("areacode"));
        }
        cursor.close();
        sqLiteDatabase.close();
        return areacode;
    }

    public String getCityName(String areacode){
        String cityname = "";
        sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "select city, town from citys where areacode = '"+areacode+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()){
            String city = cursor.getString(0);
            cityname = city +"," +cursor.getString(1);
        }
        cursor.close();
        sqLiteDatabase.close();
        return cityname;
    }
}
