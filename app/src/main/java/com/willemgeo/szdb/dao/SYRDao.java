package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.willemgeo.szdb.bean.DBH;
import com.willemgeo.szdb.bean.JTCY;
import com.willemgeo.szdb.bean.SYR;
import com.willemgeo.szdb.utils.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SYRDao {
    private static String TAG = JTCY.class.getSimpleName();
    private final DBHelper dbHelper;

    public SYRDao(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void addSYR(SYR syr){
        try {
            Dao<SYR,Integer> dao= dbHelper.getDao(JTCY.class);
            dao.create((syr));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SYR> getAll(){
        try {
            Dao<SYR,Integer> dao= dbHelper.getDao(SYR.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"getAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<SYR>();
        }
    }

    public void deleteById(int id) {
        try {
            Dao<SYR,Integer> dao= dbHelper.getDao(SYR.class);
            DeleteBuilder<SYR,Integer> deleteBuilder =  dao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            int t = deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG,"addBookMark"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void addAll(List<SYR> markList) {
        try {
            Dao<SYR,Integer> dao= dbHelper.getDao(SYR.class);
            dao.create(markList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
