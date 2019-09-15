package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.willemgeo.szdb.bean.DBH;
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.utils.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjh on 2019/9/15.
 */

public class ImgDao {

    private static String TAG = Img.class.getSimpleName();
    private final DBHelper dbHelper;

    public ImgDao(DBHelper db){
        this.dbHelper = db;
    }

    public List<Img> findAll(){
        try {
            Dao<Img,Integer> dao= dbHelper.getDao(Img.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"findAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<Img>();
        }
    }

    public List<Img> findByCun(String cjbm){

        try {
            Dao<Img,Integer> dao= dbHelper.getDao(Img.class);
            return dao.queryForEq("cjbm",cjbm);
        } catch (SQLException e) {
            Log.e(TAG,"findByCun"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<Img>();
        }
    }
    public Img findByZjbm(String zjbm){
        try {
            Dao<Img,Integer> dao= dbHelper.getDao(Img.class);
            List<Img> lst = dao.queryForEq("zjbm",zjbm);
            if(lst.size()>0){
                return lst.get(1);
            }else {
                return null;
            }
        } catch (SQLException e) {
            Log.e(TAG,"findByCun"+e.getMessage());
            e.printStackTrace();
            return  null;
        }

    }

    public void addImg(Img info){
        try {
            Dao<Img,Integer> dao= dbHelper.getDao(Img.class);
            dao.create(info);
        } catch (SQLException e) {
            Log.e(TAG,"addImg"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            Dao<Img,Integer> dao= dbHelper.getDao(Img.class);
            DeleteBuilder<Img,Integer> deleteBuilder =  dao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            int t = deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(TAG,"deleteImg"+e.getMessage());
            e.printStackTrace();
        }
    }


}
