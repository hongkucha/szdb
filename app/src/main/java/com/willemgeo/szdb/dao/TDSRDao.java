package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.willemgeo.szdb.bean.JTCY;
import com.willemgeo.szdb.bean.SYR;
import com.willemgeo.szdb.bean.TDSR;
import com.willemgeo.szdb.utils.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TDSRDao {
    private static String TAG = JTCY.class.getSimpleName();
    private final DBHelper dbHelper;

    public TDSRDao(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void addTDSR(TDSR tdsr){
        try {
            Dao<TDSR,Integer> dao= dbHelper.getDao(TDSR.class);
            dao.create((tdsr));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TDSR> getAll(){
        try {
            Dao<TDSR,Integer> dao= dbHelper.getDao(TDSR.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"getAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<TDSR>();
        }
    }

    public void deleteById(int id) {
        try {
            Dao<TDSR,Integer> dao= dbHelper.getDao(TDSR.class);
            DeleteBuilder<TDSR,Integer> deleteBuilder =  dao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            int t = deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG,"addBookMark"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void addAll(List<TDSR> markList) {
        try {
            Dao<TDSR,Integer> dao= dbHelper.getDao(TDSR.class);
            dao.create(markList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
