package com.willemgeo.szdb.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
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

    private ImgDao(DBHelper db){
        this.dbHelper = db;
    }

    List<Img> findAll(){
        try {
            Dao<Img,Integer> dao= dbHelper.getDao(Img.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,"findAll"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<Img>();
        }
    }

    List<Img> findByCun(String cjbm){

        try {
            Dao<Img,Integer> dao= dbHelper.getDao(Img.class);
            return dao.queryForEq("cjbm",cjbm);
        } catch (SQLException e) {
            Log.e(TAG,"findByCun"+e.getMessage());
            e.printStackTrace();
            return  new ArrayList<Img>();
        }
    }
    Img findByZjbm(String zjbm){
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





}
