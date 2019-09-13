package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.willemgeo.szdb.bean.DBH;
import com.willemgeo.szdb.utils.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHDao {
    private static String TAG = DBH.class.getSimpleName();
    private final DBHelper dbHelper;

    public DBHDao(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void addDBH(DBH dbh){
        try {
            Dao<DBH,Integer> dao= dbHelper.getDao(DBH.class);
            dao.create((dbh));
        } catch (SQLException e) {
            Log.e(TAG,"addDBH"+e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DBH> getAll(){
        try {
            Dao<DBH,Integer> dao= dbHelper.getDao(DBH.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"getAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<DBH>();
        }
    }

    public void deleteById(int id) {
        try {
            Dao<DBH,Integer> dao= dbHelper.getDao(DBH.class);
            DeleteBuilder<DBH,Integer> deleteBuilder =  dao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            int t = deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG,"addBookMark"+e.getMessage());
            e.printStackTrace();
        }
    }
}
