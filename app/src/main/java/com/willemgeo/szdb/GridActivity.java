package com.willemgeo.szdb;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.willemgeo.szdb.adapter.GirdAdapter;
import com.willemgeo.szdb.base.Constants;
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.dao.ImgDao;
import com.willemgeo.szdb.utils.DBHelper;
import com.willemgeo.szdb.utils.FileUtil;
import com.willemgeo.szdb.utils.OKHttpUtils;
import com.willemgeo.szdb.utils.ProgressListener;
import com.willemgeo.szdb.utils.UploadServerUtils;
import com.willemgeo.szdb.utils.impl.UIProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.willemgeo.szdb.base.Constants.CT_URL_SERVER;
import static com.willemgeo.szdb.base.Constants.CT_URL_UPLOAD;


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
    DBHelper dbHelper ;
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
        mBtnUpdate.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ImgDao dao = dbHelper.createImgDao();
            List<Img> imgs = dao.findIsNotUpload();
            List<String> files = new LinkedList<>();
            for(Img img : imgs){
                String filePath = Environment.getExternalStorageDirectory().getPath()+ "/"+img.getImgpath();
                File file = new File(filePath);
                if(file.exists()){
                    files.add(filePath);
                }
            }
            //UploadServerUtils.uploadLogFiles(CT_URL_SERVER+Constants.CT_URL_UPLOAD,files,"/MICE/IMG",imgs,"1234");
           try {
               upload(files,imgs);
           }catch (Exception ex){
               Log.e("upload",ex.getMessage()+"");
           }
        }
    };
	private void upload(List<String> files ,List<Img>imgs) {
        //这个是非ui线程回调，不可直接操作UI
        final ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgress(long bytesWrite, long contentLength, boolean done) {
                Log.i("TAG", "bytesWrite:" + bytesWrite);
                Log.i("TAG", "contentLength" + contentLength);
                Log.i("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.i("TAG", "done:" + done);
                Log.i("TAG", "================================");
            }
        };


        //这个是ui线程回调，可直接操作UI
        UIProgressListener uiProgressRequestListener = new UIProgressListener() {
            @Override
            public void onUIProgress(long bytesWrite, long contentLength, boolean done) {
                Log.i("TAG", "bytesWrite:" + bytesWrite);
                Log.i("TAG", "contentLength" + contentLength);
                Log.i("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.i("TAG", "done:" + done);
                Log.i("TAG", "================================");
                //ui层回调,设置当前上传的进度值
                int progress = (int) ((100 * bytesWrite) / contentLength);
                //uploadProgress.setProgress(progress);
                //uploadTV.setText("上传进度值：" + progress + "%");
            }

            //上传开始
            @Override
            public void onUIStart(long bytesWrite, long contentLength, boolean done) {
                super.onUIStart(bytesWrite, contentLength, done);
                Toast.makeText(getApplicationContext(),"开始上传",Toast.LENGTH_SHORT).show();
            }

            //上传结束
            @Override
            public void onUIFinish(long bytesWrite, long contentLength, boolean done) {
                super.onUIFinish(bytesWrite, contentLength, done);
                //uploadProgress.setVisibility(View.GONE); //设置进度条不可见
                Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_SHORT).show();

            }
        };


        //开始Post请求,上传文件
        OKHttpUtils.doPostRequest(CT_URL_SERVER+ CT_URL_UPLOAD, files,imgs, uiProgressRequestListener, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "error------> "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("TAG", "success---->"+response.body().string());
            }


        });

    }

}
