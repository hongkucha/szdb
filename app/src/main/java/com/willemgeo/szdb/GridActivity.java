package com.willemgeo.szdb;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;


/**
 * Created by pjh on 2019/9/15.
 */

public class GridActivity extends Activity {

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
        



    }




}
