package com.willemgeo.szdb.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.dao.ImgDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static com.willemgeo.szdb.base.Constants.CT_DATA_PATH;
import static com.willemgeo.szdb.base.Constants.CT_DATA_PATH_IMG;

/**
 * Created by lwy on 2018/7/4.
 */
public class BitmapUtil {
    /**
     * 根据图片路径获取本地图片的Bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapByUrl(String url) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(url);
            bitmap = BitmapFactory.decodeStream(fis);

        } catch (FileNotFoundException e) {
            Log.d("BitmapUtil", e.toString());
            bitmap = null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fis = null;
            }
        }

        return bitmap;
    }

    /**
     * bitmap旋转90度
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createRotateBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            try {
                m.postRotate(90);
                Bitmap bmp2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
                bitmap.recycle();
                bitmap = bmp2;
            } catch (Exception ex) {
                System.out.print("创建图片失败！" + ex);
            }
        }
        return bitmap;
    }

    public static Bitmap getBitmapByUri(Uri uri, ContentResolver cr) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cr
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            Log.d("BitmapUtil", e.toString());
            bitmap = null;
        }
        return bitmap;
    }


    //pjh
    /**
     * 根据Img存储Bitmap
     */
    public static Img saveBitmapInfo(Img info ,Bitmap bitmap,DBHelper db){
        FileOutputStream fileOutputStream = null;
        String path = "";
        String name = "";
        File imgFile = null;
        try{
                boolean shiwu = true;
                //保存图片
                /*try {
                    xdPath = CT_DATA_PATH + CT_DATA_PATH_IMG +"/"+ info.getXjbm() + "/" + info.getCjbm() + "/"
                            + info.getZjhm() + "/" + info.getImgtype();
                    path = Environment.getExternalStorageDirectory().getPath() + xdPath;

                    name = info.getId() + ".jpg";
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                     imgFile = new File(path + "/" + name);
                    fileOutputStream = new FileOutputStream(imgFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }catch (Exception ex){
                    shiwu = false;
                }finally {
                    if(fileOutputStream!=null){
                        fileOutputStream.close();
                    }
                }*/
                //存储信息到数据库
                if(!shiwu){
                    if(imgFile != null && imgFile.exists()){
                        imgFile.delete();
                    }
                }else {
                    try {
                        ImgDao dao = db.createImgDao();
                        info.setCreateTime(new Date());
                        dao.addImg(info);
                    }catch (Exception ex){
                        if(imgFile != null && imgFile.exists()){
                            imgFile.delete();
                        }
                    }

                }

                return info;


        }catch (Exception ex){

            ex.printStackTrace();
            return  null;

        }
    }
}
