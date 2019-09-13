package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.willemgeo.szdb.bean.DBH;
import com.willemgeo.szdb.bean.JTCY;
import com.willemgeo.szdb.utils.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JTCYDao {
    private static String TAG = JTCY.class.getSimpleName();
    private final DBHelper dbHelper;

    public JTCYDao(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void addJTCY(JTCY jtcy){
        try {
            Dao<JTCY,Integer> dao= dbHelper.getDao(JTCY.class);
            dao.create((jtcy));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JTCY> getAll(){
        try {
            Dao<JTCY,Integer> dao= dbHelper.getDao(JTCY.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"getAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<JTCY>();
        }
    }

    public void deleteById(int id) {
        try {
            Dao<JTCY,Integer> dao= dbHelper.getDao(JTCY.class);
            DeleteBuilder<JTCY,Integer> deleteBuilder =  dao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            int t = deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG,"addBookMark"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void addAll(List<JTCY> markList) {
        try {
            Dao<JTCY,Integer> dao= dbHelper.getDao(JTCY.class);
            dao.create(markList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
