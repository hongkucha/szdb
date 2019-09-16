package com.willemgeo.szdb.utils;

/**
 * Created by Administrator on 2019/9/16.
 */


import android.util.Log;

import com.willemgeo.szdb.bean.Img;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.HttpVersion;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.FormBodyPart;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.ContentBody;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.CoreProtocolPNames;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;


public class UploadServerUtils {

//
//    public static String uploadLogFile(String uploadUrl, String filePath, String folderPath) {
//        String result = null;
//        try {
//            HttpClient hc = new DefaultHttpClient();
//            hc.getParams().setParameter(
//                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//            HttpPost hp = new HttpPost(uploadUrl);
//            File file = new File(filePath);
//            final MultipartEntityBuilder entity = MultipartEntityBuilder.create();
//            ContentBody contentBody = new FileBody(file);
//            entity.addPart("file", contentBody);
//            entity.addPart("folderPath", new StringBody(folderPath, Charset.forName("UTF-8")));
//            hp.setEntity(entity);
//            HttpResponse hr = hc.execute(hp);
//            HttpEntity he = hr.getEntity();
//            int statusCode = hr.getStatusLine().getStatusCode();
//            if (statusCode != HttpStatus.SC_OK){
//                throw new Exception("网络异常："+statusCode);
//            }
//
//
//            result = EntityUtils.toString(he, HTTP.UTF_8);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("TAG", "文件上传失败！上传文件为：" + filePath);
//            Log.e("TAG", "报错信息toString：" + e.toString());
//        }
//        return result;
//    }
//
//    public static String uploadLogFiles(String uploadUrl, List<String> filePaths, String folderPath, List<Img> lstImg, String license) {
//        String result = null;
//        try {
//            HttpClient hc = new DefaultHttpClient();
//
////            hc.getParams().setParameter(
////                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//            HttpPost hp = new HttpPost(uploadUrl);
//
//            final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//
//
//            builder.setCharset(Charset.forName("utf-8"));//设置请求的编码格式
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
//            int count=0;
//            for (String filePath : filePaths) {
//                File file = new File(filePath);
//                builder.addBinaryBody("file"+count, file);
//                count++;
//            }
//            builder.addTextBody("folderPath", folderPath);
//            builder.addTextBody("license",license);
//
//
//            entity.addPart("folderPath", new StringBody(folderPath, Charset.forName("UTF-8")));
//            for (Img img : lstImg) {
//                String strImg = GsonUtil.BeanToJson(img);
//                ContentBody contentBody = new StringBody(strImg, Charset.forName("UTF-8"));
//                entity.addPart("imgs", contentBody);
//            }
//            entity.addPart("license",new StringBody(license,Charset.forName("UTF-8")));
//
//            hp.setEntity(entity);
//            HttpResponse hr = hc.execute(hp);
//            HttpEntity he = hr.getEntity();
//            int statusCode = hr.getStatusLine().getStatusCode();
//            if (statusCode != HttpStatus.SC_OK)
//                throw new Exception("网络异常："+statusCode);
//            result = EntityUtils.toString(he, HTTP.UTF_8);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("TAG", "文件上传失败！上传文件为：" + filePaths.toString());
//            Log.e("TAG", "报错信息toString：" + e.toString());
//        }
//        return result;
//    }
//

}