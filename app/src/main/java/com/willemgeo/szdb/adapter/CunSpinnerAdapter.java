package com.willemgeo.szdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.willemgeo.szdb.R;
import com.willemgeo.szdb.bean.cun;
import com.willemgeo.szdb.bean.xian;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */

public class CunSpinnerAdapter extends BaseAdapter {

    List<cun> mList;
    Context context;
    private LayoutInflater mLayoutInflater;

    public CunSpinnerAdapter(Context context, List<cun> mList) {
        super();
        this.mList = mList;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.mList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.sy_spinner_item, null);
            ((TextView)convertView.findViewById(R.id.sy_spinner_text)).setText(this.mList.get(position).CunName);
            convertView.setTag(this.mList.get(position));
        }
        return convertView;
    }
}
