package com.willemgeo.szdb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.willemgeo.szdb.adapter.GirdAdapter;
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.dao.ImgDao;
import com.willemgeo.szdb.utils.DBHelper;

import java.util.List;


/**
 * Created by pjh on 2019/9/15.
 */

public class GridActivity extends Activity {

    DBHelper dbHelper ;



    public GridActivity() {
        super();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_activity);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //region 变量
    GridView mGridView;//网格视图
    Button mBtnUpdate;//上传按钮

    //endregion

    //初始化
    private void init() {
        mGridView = findViewById(R.id.imggrid);
        mBtnUpdate = findViewById(R.id.btnupdate);

        dbHelper = new DBHelper(getApplicationContext());
        ImgDao dao = dbHelper.createImgDao();
        List<Img> lst = dao.findAll();
        GirdAdapter adapter = new GirdAdapter(getApplicationContext() ,lst);
        mGridView.setAdapter(adapter);
    }




}
