package com.willemgeo.szdb.utils;

/**
 * Created by Administrator on 2019/9/17.
 */


import android.util.Log;

import com.willemgeo.szdb.base.Constants;
import com.willemgeo.szdb.bean.Img;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.willemgeo.szdb.utils.DBConfig.GetLicense;

/**
 * Created by 周旭 on 2017/1/18.
 * OKHttp工具类(上传，下载文件)
 */

public class OKHttpUtils {

    private static OkHttpClient client;

    /**
     * 创建一个OkHttpClient的对象的单例
     * @return
     */
    private synchronized static OkHttpClient getOkHttpClientInstance() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    //设置连接超时等属性,不设置可能会报异常
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(3600, TimeUnit.SECONDS)
                    .writeTimeout(3600, TimeUnit.SECONDS);

            client = builder.build();
        }
        return client;
    }


    /**
     * 获取文件MimeType
     *
     * @param filename
     * @return
     */
    private static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }


    /**
     * 获得Request实例(不带进度)
     * @param url
     * @return
     */
    private static Request getRequest(String url, List<String> fileNames) {
        Request.Builder builder = new Request.Builder();
//        builder.url(url)
//                .post(getRequestBody(fileNames));
        return builder.build();
    }


    /**
     * 获得Request实例(带进度)
     * @param url
     * @return
     */
    private static Request getRequest(String url, List<String> fileNames,String imgs, ProgressListener uiProgressRequestListener) {
        Request.Builder builder = new Request.Builder();
        RequestBody body = OKHttpUtils.getRequestBody(fileNames,imgs);
        ProgressRequestBody progressRequestBody= ProgressHelper.addProgressRequestListener( body, uiProgressRequestListener);
        builder.url(url)
                .post(progressRequestBody);
        return builder.build();
    }


    /**
     * 通过上传的文件的完整路径生成RequestBody
     * @param fileNames 完整的文件路径
     * @return
     */
    private static RequestBody getRequestBody(List<String> fileNames,String imgs) {
        //创建MultipartBody.Builder，用于添加请求的数据
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < fileNames.size(); i++) { //对文件进行遍历
            File file = new File(fileNames.get(i)); //生成文件
            //根据文件的后缀名，获得文件类型
            String fileType = getMimeType(file.getName());
            builder.addFormDataPart( //给Builder添加上传的文件
                    "image",  //请求的名字
                    file.getName(), //文件的文字，服务器端用来解析的f
                    RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
            );
        }

            builder.addFormDataPart("imgs", imgs);

        try {
            builder.addFormDataPart("license", Constants.imei); //GetLicense()+"");//增加上传验证码
        }catch (Exception ex){
            Log.e("license",ex.getMessage()+"");
        }
        return builder.build(); //根据Builder创建请求
    }

    /**
     * 根据url，发送异步Post请求(带进度)
     * @param url 提交到服务器的地址
     * @param fileNames 完整的上传的文件的路径名
     * @param callback OkHttp的回调接口
     */
    public static void doPostRequest(String url, List<String> fileNames, String imgs, ProgressListener uiProgressRequestListener, Callback callback) {
        Request request= getRequest(url,fileNames,imgs,uiProgressRequestListener);
        OkHttpClient oc = getOkHttpClientInstance();

        Call call =oc.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 根据url，发送异步Post请求(不带进度)
     * @param url 提交到服务器的地址
     * @param fileNames 完整的上传的文件的路径名
     * @param callback OkHttp的回调接口
     */
    public static void doPostRequest(String url, List<String> fileNames, Callback callback) {
        Call call = getOkHttpClientInstance().newCall(getRequest(url,fileNames));
        call.enqueue(callback);
    }

    //获取字符串
    public static String getString(Response response) throws IOException {
        if (response != null && response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }


    public static void doFindRequest(String url ,List<Img>imgs , Callback callback){

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        for (Img img:imgs
             ) {
            bodyBuilder.addFormDataPart("uids",img.getUid());
        }
        MultipartBody myBody = bodyBuilder.build();

        Request.Builder requestbuilder = new Request.Builder();
        requestbuilder.url(url).post(myBody);
        Request request = requestbuilder.build() ;

        Call call = getOkHttpClientInstance().newCall(request);
        call.enqueue(callback);
    }
}
