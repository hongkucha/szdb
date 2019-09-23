package com.willemgeo.szdb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.willemgeo.szdb.adapter.GridAsyncAdapter;
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.dao.ImgDao;
import com.willemgeo.szdb.utils.DBHelper;
import com.willemgeo.szdb.utils.GsonUtil;
import com.willemgeo.szdb.utils.OKHttpUtils;
import com.willemgeo.szdb.utils.ProgressListener;
import com.willemgeo.szdb.utils.impl.UIProgressListener;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.willemgeo.szdb.base.Constants.CT_URL_SERVER;
import static com.willemgeo.szdb.base.Constants.CT_URL_UPLOAD;
import static com.willemgeo.szdb.base.Constants.CT_URL_UPLOAD_DONE;
import static com.willemgeo.szdb.utils.DBConfig.GetServerUrl;


/**
 * Created by pjh on 2019/9/15.
 */

public class GridActivity extends Activity {


    Context context;
    ProgressBar bar;
    static int CountUpload = 0;


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
            bar = findViewById(R.id.progressbar);

            dbHelper = new DBHelper(getApplicationContext());
            ImgDao dao = dbHelper.createImgDao();
            lstAdapter = dao.findIsNotUpload();
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
            try {

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
                bar.setProgress(0);
                if(imgs!=null && imgs.size()>0) {

                    v.setEnabled(false);
                    CountUpload = imgs.size();
                    upload(files, imgs);
                }
           }catch (Exception ex){
               Log.e("upload",ex.getMessage()+"");
               v.setEnabled(true);
           }
        }
    };


	private void upload(List<String> files , final List<Img>imgs) {


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

                //ui层回调,设置当前上传的进度值
                int progress = (int) ((100 * bytesWrite) / contentLength);

                bar.setProgress(progress);

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
                //
                findViewById(R.id.btnupdate).setEnabled(true);
            }
        };

        String url = CT_URL_SERVER + CT_URL_UPLOAD;

        StringBuffer StrImg = new StringBuffer();
        for (Img img: imgs
             ) {
            String str = "";
            str += ( "uid:"+img.getUid()+",");
            str+=("dbrmc:"+img.getDbrmc()+",");
            str+=("imgtype:"+img.getImgtype()+",");
            str+=("imgpath:"+img.getImgpath()+",");
            str+=("zjhm:"+img.getZjhm()+",");
            str+=("cjbm:"+img.getCjbm()+",");
            str+=("xjbm:"+img.getXjbm()+",");
            str+=("createtime:"+(img.getCreateTime()==null?0:img.getCreateTime().getTime())+",");
            str+=("x:"+img.getX()+",");
            str+=("y:"+img.getY());
            str+="&&&&";
            StrImg.append(str);
        }



        //开始Post请求,上传文件
        OKHttpUtils.doPostRequest(url, files, StrImg.toString(), uiProgressRequestListener, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    uploadResult=0;
                    handler.post(result);
                    Log.i("TAG", "error------> " + e.getMessage());
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i("TAG", "success---->"+response.body().string());

                //上传完成后执行标记已上传
                OKHttpUtils.doFindRequest(CT_URL_SERVER+ CT_URL_UPLOAD_DONE, imgs,new Callback(){

                    @Override
                    public void onFailure(Call call, IOException e) {
                        uploadResult=0;
                        handler.post(result);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String lst =  response.body().string();
                        uploadResult=1;
                        uploadUidsResult =  GsonUtil.GsonToList(lst+"",String.class);
                        handler.post(result);
                    }
                });



            }


        });

    }

    int uploadResult = -1;
	List<String>uploadUidsResult = new LinkedList<>();
    final Handler handler = new Handler();
    final Runnable result = new Runnable() {
        @Override
        public void run() {
            switch (uploadResult){
                case 0:
                    try{
                        Toast.makeText(getApplicationContext(),"无法连接服务器，上传失败。",Toast.LENGTH_SHORT).show(); }
                    catch (Exception e1){
                        e1.printStackTrace();
                    }
                    break;
                case 1:

                    try{


                        if( uploadUidsResult.size() != CountUpload){
                            Toast.makeText(getApplicationContext(),"无法连接服务器，上传失败。",Toast.LENGTH_SHORT).show();

                        }else {
                            ImgDao dao = dbHelper.createImgDao();
                            for (String uid : uploadUidsResult) {
                                dao.markIsUpload(uid);
                            }


                            Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_LONG).show();

                            finish();

                        }

                    }catch (Exception ex){
                        Log.e("标记已上传",""+ex.getMessage());
                    }
                    break;
            }
            findViewById(R.id.btnupdate).setEnabled(true);
        }
    };



//    MyHandler mHandler = new MyHandler(this);
//     class MyHandler extends Handler {
//        WeakReference<GridActivity> mActivity;
//        MyHandler(GridActivity activity) {
//            mActivity = new WeakReference<GridActivity>(activity); }
//        @Override public void handleMessage(Message msg) {
//            GridActivity theActivity = mActivity.get();
//            switch (msg.what) {
//                case 0x0001://Toast
//
//                    theActivity.findViewById(R.id.btnupdate).setEnabled(true);
//                    Message msg1 =new Message();
//                    msg1.what = 0;
//                    handler.sendMessage(msg1);
//
//                    break;
//                case 0x0002:
//
//
//                    try{
//                        List<String> uids = GsonUtil.GsonToList(msg.obj+"",String.class);
//                        Message msg2 =new Message();
//                        if( uids.size() != CountUpload){
//                            msg2.what = 0;
//                        }else {
//                            ImgDao dao = theActivity.dbHelper.createImgDao();
//                            for (String uid : uids) {
//                                dao.markIsUpload(uid);
//                            }
//                            msg2.what = 1;
//                        }
//                        handler.sendMessage(msg2);
//
//                    }catch (Exception ex){
//                        Log.e("标记已上传",""+ex.getMessage());
//                    }
//                    finally {
//
//                    }
//
//                    theActivity.findViewById(R.id.btnupdate).setEnabled(true);
//                    break;
//            }
//
//
//        }
//    };


}
