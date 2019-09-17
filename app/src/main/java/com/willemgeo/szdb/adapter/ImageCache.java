package com.willemgeo.szdb.adapter;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {

    /**
     * 缓存类 LruCache
     */
    private static  LruCache<String,Bitmap> imgCache;

    /**
     * 实例
     */
    private static ImageCache instance;

    /**
     * 判断key 是否存在
     */
    private static List<String> lruKeys;


    public ImageCache() {
        // 初始化 ，拿可用 内存的8分之一
        int maxMemory=(int) Runtime.getRuntime().maxMemory();
        int cacheMemory=maxMemory/8;
        imgCache=new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 必须重写此方法，计算bitmap的大小
                return value.getRowBytes()*value.getHeight();
            }
        };
        lruKeys=new ArrayList<String>();
    }

    /**
     * 单例模式 创建 ImageCache 缓存类
     * @return
     */
    public static ImageCache getinstance(){
        if(instance==null){
            instance=new ImageCache();
            return instance;
        }else{
            return instance;
        }
    }


    /**
     * 添加缓存
     * @param key
     * @param bitmap
     */
    public  void setCache(String key,Bitmap bitmap){
        try {
            imgCache.put(key, bitmap);
            lruKeys.add(key);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 得到缓存
     * @param key
     * @return
     */
    public  Bitmap getCache(String key){
        return imgCache.get(key);
    }

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    public  boolean isKey(String key){
        return lruKeys.contains(key);
    }


}