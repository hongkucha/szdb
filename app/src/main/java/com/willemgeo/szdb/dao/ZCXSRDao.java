package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.willemgeo.szdb.bean.JTCY;
import com.willemgeo.szdb.bean.TDSR;
import com.willemgeo.szdb.bean.ZCXSR;
import com.willemgeo.szdb.utils.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZCXSRDao {
    private static String TAG = JTCY.class.getSimpleName();
    private final DBHelper dbHelper;

    public ZCXSRDao(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void addZCXSR(ZCXSR zcxsr){
        try {
            Dao<ZCXSR,Integer> dao= dbHelper.getDao(ZCXSR.class);
            dao.create((zcxsr));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ZCXSR> getAll(){
        try {
            Dao<ZCXSR,Integer> dao= dbHelper.getDao(ZCXSR.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"getAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<ZCXSR>();
        }
    }

    public void deleteById(int id) {
        try {
            Dao<ZCXSR,Integer> dao= dbHelper.getDao(ZCXSR.class);
            DeleteBuilder<ZCXSR,Integer> deleteBuilder =  dao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            int t = deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG,"addBookMark"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void addAll(List<ZCXSR> markList) {
        try {
            Dao<ZCXSR,Integer> dao= dbHelper.getDao(ZCXSR.class);
            dao.create(markList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
