package com.willemgeo.szdb.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.willemgeo.szdb.bean.DBH;
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.bean.JTCY;
import com.willemgeo.szdb.bean.SYR;
import com.willemgeo.szdb.dao.DBHDao;
import com.willemgeo.szdb.dao.ImgDao;
import com.willemgeo.szdb.dao.JTCYDao;
import com.willemgeo.szdb.dao.SYRDao;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.willemgeo.szdb.base.Constants.CT_DATA_PATH;
import static com.willemgeo.szdb.base.Constants.CT_DATA_PATH_DB;

/**
 * Created by duboqun on 2019/6/3.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static boolean isUpdate = false;
    private static DBHelper instance = null;
    private static String fileRoute = Environment.getExternalStorageDirectory().getPath() +CT_DATA_PATH+CT_DATA_PATH_DB;
    private static final int DATABASE_VERSION = 9;
    private static String dbName = "mydb.db";

    private Map<String, Dao> daos = new HashMap<>();

    public static String getDatabaseFilePath(){return fileRoute + dbName;};

    public DBHelper(Context context) {
        super(context, fileRoute +"/"+ dbName, null, DATABASE_VERSION);

        Log.e("DBHelper",fileRoute+"/"+ dbName);
        Log.e("DbFile.exists",(new File(fileRoute +"/"+ dbName)).exists()+"");

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DBH.class);
            TableUtils.createTable(connectionSource, JTCY.class);
            TableUtils.createTable(connectionSource, SYR.class);
            TableUtils.createTable(connectionSource, Img.class);
        } catch (SQLException e) {
            Log.e("DBHelper.onCreate",""+ e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            isUpdate = true;
            TableUtils.dropTable(connectionSource, DBH.class, true);
            TableUtils.dropTable(connectionSource, JTCY.class, true);
            TableUtils.dropTable(connectionSource, SYR.class, true);
            TableUtils.dropTable(connectionSource, Img.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e("DBHelper.onUpgrade",""+ e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = (Dao) super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    public DBHDao createDBHDao(){
        return  new DBHDao(this);
    }
    public JTCYDao createJTCYDao(){
        return  new JTCYDao(this);
    }
    public SYRDao createSYRDao(){
        return  new SYRDao(this);
    }
   /* public GZXSR createSketchDao(){
        return  new SketchDao(this);
    }*/

   //pjh
    public ImgDao createImgDao(){
        return new ImgDao(this);
    }
}
