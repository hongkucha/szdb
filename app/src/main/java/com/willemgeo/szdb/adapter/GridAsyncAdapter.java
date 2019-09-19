package com.willemgeo.szdb.adapter;

/**
 * Created by Administrator on 2019/9/17.
 */

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.willemgeo.szdb.R;
import com.willemgeo.szdb.bean.Img;


public class GridAsyncAdapter extends BaseAdapter  {

    List<Img> lst;
    Context context;
    private ViewHolder holder;
    private GridView gridView;

    // 存放全部的URL
    public static String[] urls;
    private RequestImageByAsyncTask asyncReqImage;
    public GridAsyncAdapter(Context context,GridView gridView,List<Img> lst  ) {
        // 数据
        this.lst = lst;
        this.context = context;
        this.gridView = gridView;

        //第二步：初始化
        asyncReqImage=new RequestImageByAsyncTask(gridView);

        this.urls = new String[lst.size()];
        for (int i = 0; i < urls.length; i++) {
            if((lst.get(i).getImgpath()+"").isEmpty()){
                urls[i] = lst.get(i).getUid();
            }
            else {
                urls[i] = Environment.getExternalStorageDirectory().getPath() + File.separator + lst.get(i).getImgpath();
            }
        }

        gridView.setOnScrollListener( scrollLisenter);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Img img = lst.get(position);

        if(img.getDbrmc().equalsIgnoreCase("111")){
            String str = "";
        }
        // 加载 布局 并 设置 布局
        holder = new ViewHolder();
        convertView = View.inflate(context, R.layout.sy_grid_item, null);
        holder.list_image = (ImageView) convertView .findViewById(R.id.image);
        holder.list_tvname = (TextView) convertView .findViewById(R.id.text_name_c);
        holder.list_tvzjhm = (TextView) convertView.findViewById(R.id.text_zjhm_c);
        holder.list_imgtype = (TextView) convertView.findViewById(R.id.text_type_c);
        // 设置图片
        holder.list_image.setScaleType(ScaleType.FIT_XY);
        holder.list_image.setImageResource(R.drawable.timg);
        holder.list_tvname.setText(img.getDbrmc());
        holder.list_tvzjhm.setText(img.getZjhm());
        holder.list_imgtype.setText(img.getImgtype());
        /**
         * 第一步:给图片设置Tag
         */
        holder.list_image.setTag(urls[position]);

        // ReqeustImageByThread reqeustImage = new
        // ReqeustImageByThread(ifo.getPic(),
        // holder.list_image);
        // reqeustImage.start();

//		RequestImageByRunnable requestImageByRunnable = new RequestImageByRunnable(
//				ifo.getPic(), holder.list_image);
//		requestImageByRunnable.RequestImage();

        return convertView;
    }

    class ViewHolder {
        public ImageView list_image;
        public TextView list_tvname, list_tvzjhm, list_imgtype;
    }

    private int start;
    private int end;
    //第一次加载
    private boolean isFrist=true;

//
//    private void recycleBitmapCaches(int fromPosition,int toPosition){
//        Bitmap delBitmap = null;
//        for(int del=fromPosition;del<toPosition;del++){
//            delBitmap = ImageCache.getinstance().getCache()
//            if(delBitmap != null){
//                //如果非空则表示有缓存的bitmap，需要清理
//                Log.e("recycleBitmapCaches", "release position:"+ del);
//                //从缓存中移除该del->bitmap的映射
//                gridviewBitmapCaches.remove(mList.get(del));
//                delBitmap.recycle();
//                delBitmap = null;
//            }
//        }
//    }
    private OnScrollListener scrollLisenter = new OnScrollListener() {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 状态改变
        // 停止滑动的时候
        if (SCROLL_STATE_IDLE == scrollState) {
            // 停止滑动 ，开始加载数据
            // 如果在 滑动的时候加载数据，可能发生卡顿
            asyncReqImage.ReqImage(start, end);

        } else {
            // 滑动时，取消加载
            asyncReqImage.Reqcancel();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 位置控制
        start = firstVisibleItem;
        end = firstVisibleItem + visibleItemCount;
        /**
         * 两个条件：
         * isFrist： 判断第一次加载；
         * visibleItemCount ：可用item的数量 大于0 时 进行第一次加载；
         */
        if(isFrist&&visibleItemCount>0){
            asyncReqImage.ReqImage(start, end);
            isFrist=false;
        }


    }
};
}