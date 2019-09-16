package com.willemgeo.szdb.utils;

/**
 * Created by pjh on 2019/9/15.
 */

import android.app.Activity;

import com.willemgeo.szdb.bean.UploadBean;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class UploadFile implements Runnable {


    private String fileName;//本地文件的参数：// 文件名

    private OnThreadResultListener listener;//任务线程回调接口
    private int percent=0;//进度
    private Activity mContext;

    private ArrayList<UploadBean> list;

    int index = 0;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public UploadFile(Activity mContext, int index, ArrayList<UploadBean> list, OnThreadResultListener listener){
        this.list = list;
        this.listener=listener;
        this.mContext = mContext;
        this.index = index;
    }

    @Override
    public void run() {

        //上传线程
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(percent < 0){
                    listener.onInterrupted();
                }else if(percent < 100){
                    percent += 1;
                    listener.onProgressChange(percent);
                }else if(percent >= 100){
                    percent = 0;
                    this.cancel();
                    //继续执行线程
                    countDownLatch.countDown();
                    listener.onFinish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 100);

        try {
            //等待
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}