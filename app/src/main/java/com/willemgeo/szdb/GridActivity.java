package com.willemgeo.szdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.willemgeo.szdb.adapter.GridAsyncAdapter;
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.dao.ImgDao;
import com.willemgeo.szdb.utils.DBHelper;
import com.willemgeo.szdb.utils.OKHttpUtils;
import com.willemgeo.szdb.utils.ProgressListener;
import com.willemgeo.szdb.utils.impl.UIProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.willemgeo.szdb.base.Constants.CT_URL_SERVER;
import static com.willemgeo.szdb.base.Constants.CT_URL_UPLOAD;
import static com.willemgeo.szdb.utils.DBConfig.GetServerUrl;


/**
 * Created by pjh on 2019/9/15.
 */

public class GridActivity extends Activity {


    Context context;

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
        context = getApplicationContext();
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
        try {
            mGridView = findViewById(R.id.imggrid);
            mBtnUpdate = findViewById(R.id.btnupdate);

            dbHelper = new DBHelper(getApplicationContext());
            ImgDao dao = dbHelper.createImgDao();
            lstAdapter = dao.findAll();
             adapter = new GridAsyncAdapter(getApplicationContext(), mGridView, lstAdapter);
            mGridView.setAdapter(adapter);

            mGridView.setOnItemLongClickListener(itemLongClickListener);
            mBtnUpdate.setOnClickListener(listener);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    static int itemPosition = 0;
    List<Img> lstAdapter ;
    GridAsyncAdapter adapter;
    AlertDialog alertDialog1;
    AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            itemPosition = position;

            DeleteItemAndReflashGird();


            return false;
        }
    };

    void DeleteItemAndReflashGird(){
        try {
            Img img = (Img) mGridView.getAdapter().getItem(itemPosition);
            ImgDao imgDao = dbHelper.createImgDao();
            imgDao.deleteById((int) img.getId());

            String filePath = Environment.getExternalStorageDirectory().getPath()
                    + "/" + img.getImgpath();
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            if (lstAdapter != null && lstAdapter.size() > 0) {
                lstAdapter.remove(itemPosition);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            Toast.makeText(context,"删除完毕",Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

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

        String url = CT_URL_SERVER+ CT_URL_UPLOAD;
        String urlServerFile = GetServerUrl();
        if(urlServerFile!= null && !urlServerFile.isEmpty()){

            url = urlServerFile + CT_URL_UPLOAD;
        }


        //开始Post请求,上传文件
        OKHttpUtils.doPostRequest(url, files,imgs, uiProgressRequestListener, new Callback() {
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
