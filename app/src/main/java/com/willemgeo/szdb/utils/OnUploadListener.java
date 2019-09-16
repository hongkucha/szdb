package com.willemgeo.szdb.utils;

/**
 * Created by Administrator on 2019/9/15.
 */

public interface OnUploadListener {//主线程回调
    void onAllSuccess();
    void onAllFailed();
    void onThreadProgressChange(int position,int percent);
    void onThreadFinish(int position);
    void onThreadInterrupted(int position);
}
