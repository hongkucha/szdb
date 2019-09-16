package com.willemgeo.szdb.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.willemgeo.szdb.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.willemgeo.szdb.base.Constants.*;

/**
 * Created by pjh on 2019/9/16.
 */

public class DBConfig {

    private static DBConfig instance;
    public static void setInstance(DBConfig obj){
        instance = obj;
    }
    public static DBConfig getInstance(){
        return instance;
    }

    public boolean IsInstance(){
        return instance != null  ;
    }


    Context mContext;
    ConfigHelper configHelper;
    String DbConfigName = "config.db";
    String CacheFile = "";
    public DBConfig (Context context){
        mContext = context;

        CacheFile = context.getFilesDir() +"/"+ DbConfigName;

        try {
            FileUtil.assets2SDCard(context, DbConfigName, CacheFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        configHelper =  new ConfigHelper (context);
    }




    class ConfigHelper extends OrmLiteSqliteOpenHelper {

        public ConfigHelper(Context context){
            super(context, CacheFile , null, ORMSQLITE_DATABASE_VERSION);

        }
        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        }

    }

    //乡镇行政区
    public Map<String,String> getXJQY(){
        Map<String,String> lst = new LinkedHashMap<>();
        if(configHelper!= null){

            Cursor cursor = configHelper.getReadableDatabase().rawQuery("select DM ,MC from XZQY where DM like '%000' and DM not like '%000000'",null);
            if(cursor .getCount()>0) {
                while (cursor.moveToNext()) {

                    try {
                        String dm = cursor.getString(0);
                        String mc = cursor.getString(1);
                        lst.put(dm, mc);

                    } catch (Exception ex) {
                        Log.e("getXJQY", "" + ex.getMessage());
                    }
                }
            }
            cursor.close();
        }
        return lst;
    }

    //村级行政区
    public Map<String,String> getCJQY(){
        Map<String,String> lst = new LinkedHashMap<>();
        if(configHelper!= null){

            Cursor cursor = configHelper.getReadableDatabase().rawQuery("select DM ,MC from XZQY where DM not like '%000'",null);
            if(cursor .getCount()>0) {
                while (cursor.moveToNext()) {

                    try {
                        String dm = cursor.getString(0);
                        String mc = cursor.getString(1);
                        lst.put(dm, mc);

                    } catch (Exception ex) {
                        Log.e("getCJQY", "" + ex.getMessage());
                    }
                }
            }
            cursor.close();
        }
        return lst;
    }


}
