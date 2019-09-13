package com.willemgeo.szdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.willemgeo.szdb.bean.DBH;
import com.willemgeo.szdb.bean.JTCY;

public class EditActivity extends AppCompatActivity {

    public Context context;
    public DBH dbh = new DBH();

    private static final int JTCY_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        context = this;
        //选择按钮
        ((Button) this.findViewById(R.id.camera_btn)).setOnClickListener(camera_btn_Click);

        //选择按钮
        ((Button) this.findViewById(R.id.gzxsr_add_btn)).setOnClickListener(gzxsr_add_btn_Click);
        ((Button) this.findViewById(R.id.gzxsr_delete_btn)).setOnClickListener(gzxsr_delete_btn_Click);

        //选择按钮
        ((Button) this.findViewById(R.id.jtcy_add_btn)).setOnClickListener(jtcy_add_btn_Click);
        ((Button) this.findViewById(R.id.jtcy_delete_btn)).setOnClickListener(jtcy_delete_btn_Click);

        //选择按钮
        ((Button) this.findViewById(R.id.jybz_add_btn)).setOnClickListener(jybz_add_btn_Click);
        ((Button) this.findViewById(R.id.jybz_delete_btn)).setOnClickListener(jybz_delete_btn_Click);

        //选择按钮
        ((Button) this.findViewById(R.id.syr_add_btn)).setOnClickListener(syr_add_btn_Click);
        ((Button) this.findViewById(R.id.syr_delete_btn)).setOnClickListener(syr_delete_btn_Click);

        //选择按钮
        ((Button) this.findViewById(R.id.tdsr_add_btn)).setOnClickListener(tdsr_add_btn_Click);
        ((Button) this.findViewById(R.id.tdsr_delete_btn)).setOnClickListener(tdsr_delete_btn_Click);

        //选择按钮
        ((Button) this.findViewById(R.id.ylbz_add_btn)).setOnClickListener(ylbz_add_btn_Click);
        ((Button) this.findViewById(R.id.ylbz_delete_btn)).setOnClickListener(ylbz_delete_btn_Click);

        //选择按钮
        ((Button) this.findViewById(R.id.zcxsr_add_btn)).setOnClickListener(zcxsr_add_btn_Click);
        ((Button) this.findViewById(R.id.zcxsr_delete_btn)).setOnClickListener(zcxsr_delete_btn_Click);

    }

    /**
     * 激活拍照
     */
    private View.OnClickListener camera_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            //Intent intent = new Intent(context, CameraActivity.class);
            //context.startActivity(intent);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 123);
            }
        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener jtcy_add_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, jtcyPopActivity.class);
            ((Activity) context).startActivityForResult(intent,JTCY_CODE);
        }
    };

    /**
     * 家庭成员删除
     */
    private View.OnClickListener jtcy_delete_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener gzxsr_add_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, gzxsrPopActivity.class);
            context.startActivity(intent);
        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener gzxsr_delete_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener jybz_add_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, jybzPopActivity.class);
            context.startActivity(intent);
        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener jybz_delete_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener syr_add_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, syrPopActivity.class);
            context.startActivity(intent);
        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener syr_delete_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    /**
     * 土地收入
     */
    private View.OnClickListener tdsr_add_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, tdsrPopActivity.class);
            context.startActivity(intent);
        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener tdsr_delete_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    /**
     * 土地收入
     */
    private View.OnClickListener ylbz_add_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, ylbzPopActivity.class);
            context.startActivity(intent);
        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener ylbz_delete_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    /**
     * 土地收入
     */
    private View.OnClickListener zcxsr_add_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, zcxsrPopActivity.class);
            context.startActivity(intent);
        }
    };

    /**
     * 家庭成员添加
     */
    private View.OnClickListener zcxsr_delete_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == 1) {
            if (requestCode == JTCY_CODE) {
            /*.setText(data.getStringExtra("as"));
            this.dbh.JtcyList.add();*/
                JTCY d= (JTCY)getIntent().getSerializableExtra("data");
                this.dbh.JtcyList.add(d);
            }
        }
    }
}
