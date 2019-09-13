package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.willemgeo.szdb.bean.GZXSR;
import com.willemgeo.szdb.bean.JTCY;
import com.willemgeo.szdb.utils.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GZXSRDao {
    private static String TAG = JTCY.class.getSimpleName();
    private final DBHelper dbHelper;

    public GZXSRDao(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void addGZXSR(GZXSR gzxsr){
        try {
            Dao<GZXSR,Integer> dao= dbHelper.getDao(GZXSR.class);
            dao.create((gzxsr));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<GZXSR> getAll(){
        try {
            Dao<GZXSR,Integer> dao= dbHelper.getDao(GZXSR.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"getAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<GZXSR>();
        }
    }

    public void deleteById(int id) {
        try {
            Dao<GZXSR,Integer> dao= dbHelper.getDao(GZXSR.class);
            DeleteBuilder<GZXSR,Integer> deleteBuilder =  dao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            int t = deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG,"addBookMark"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void addAll(List<GZXSR> markList) {
        try {
            Dao<GZXSR,Integer> dao= dbHelper.getDao(GZXSR.class);
            dao.create(markList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
