package com.willemgeo.szdb.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ImageView;

import com.willemgeo.szdb.MainActivity;
import com.willemgeo.szdb.R;


public class RequestImageByAsyncTask {

    /**
     * AsyncTask 实现异步加载
     *
     */
    private GridView gridView;

    /**
     * 初始化 基础地址
     */
    //private final static String baseUrl = "http://192.168.75.1:8081/GoodsServers/";

    /**
     * 存放 所有的异步任务：当滑动的时候，停止加载；当停止滑动的时候，加载数据；为了避免卡顿
     */
    private List<ReqImageAsyncTask> asyncTasks = new ArrayList<RequestImageByAsyncTask.ReqImageAsyncTask>();

    /**
     * GridView 初始化
     *
     * @param gridView
     */
    public RequestImageByAsyncTask(GridView gridView) {
        // 初始化 listview ：作用 通过 listview 来 获取 ImageView的值
        this.gridView = gridView;

    }

    /**
     * 添加加载任务
     *
     * @param start
     * @param end
     */
    public void ReqImage(int start, int end) {

        for (int i = start; i < end; i++) {

            try {
                String url = GridAsyncAdapter.urls[i];

                ImageView imageView = (ImageView) gridView.findViewWithTag(url);

                ReqImageAsyncTask asyncTask = new ReqImageAsyncTask(imageView);
                asyncTask.execute(url);

                asyncTasks.add(asyncTask);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    /**
     * 取消请求任务
     *
     */
    public void Reqcancel() {

        for (ReqImageAsyncTask reqImageAsyncTask : asyncTasks) {
            if (!reqImageAsyncTask.isCancelled()) {
                reqImageAsyncTask.cancel(true);
            }
        }

    }

    /**
     * 根据路径 获得图片
     *
     * @param picpath
     * @return
     */
    private Bitmap getBitmap(String picpath) {

        //压缩打开
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picpath, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcWidth > 100 && srcWidth > 100) {
            inSampleSize  =(int)(srcWidth /100);
        } else if(srcWidth <100  && srcHeight >100  ){
            inSampleSize  = (int)(srcHeight /100);
        }

        if(inSampleSize  <=0){
            inSampleSize  =1;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(picpath, options);
        return bitmap;
    }

    /**
     * 请求图片 异步任务
     *
     * @author yuan
     *
     */
    class ReqImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView Img;

        public ReqImageAsyncTask(ImageView view) {
            // 初始化
            this.Img = view;

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // 请求图片

            String ReqUrl = params[0];

            Bitmap bitmap = null;

            ImageCache imageCache = ImageCache.getinstance();

            if(new File(ReqUrl).exists()) {

                if (imageCache.isKey(ReqUrl)) {
                    // 如果 存在 ，去内存中的
                    bitmap = imageCache.getCache(ReqUrl);

                } else {
                    // 否则 ，请求 图片
                    bitmap = getBitmap(ReqUrl);

                    imageCache.setCache(ReqUrl, bitmap);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // 得到图片
            if (bitmap != null) {
                Img.setImageBitmap(bitmap);
            } else {

                Img.setImageResource(R.drawable.timg);
            }
        }

    }

}