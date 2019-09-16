package com.willemgeo.szdb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.willemgeo.szdb.R;
import com.willemgeo.szdb.bean.Img;

import java.io.File;
import java.util.List;

/**
 * Created by pjh on 2019/9/16.
 */

public class GirdAdapter extends BaseAdapter {

    List<Img> lst;
    Context context;


    public GirdAdapter(Context applicationContext, List<Img> lst) {
        this.lst = lst;
        context = applicationContext;
    }


    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = View.inflate(context, R.layout.sy_grid_item, null);
        }
        try {

            Img img = lst.get(position);
            TextView tvName = convertView.findViewById(R.id.text_name_c);
            tvName.setText(img.getDbrmc());
            TextView tvZjhm = convertView.findViewById(R.id.text_zjhm_c);
            tvZjhm.setText(img.getZjhm());
            TextView tvType = convertView.findViewById(R.id.text_type_c);
            tvType.setText(img.getImgtype());
            ImageView imageView = convertView.findViewById(R.id.image);

            String imgPath  = Environment.getExternalStorageDirectory().getPath() + "/" + img.getImgpath();
            File file = new File(imgPath);
            Bitmap bitmap = null;
            if(file.isFile() && file.exists()){
                bitmap = BitmapFactory.decodeFile(imgPath);
            }else {
                bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.timg);
            }
            imageView.setImageBitmap(bitmap);
        }catch (Exception ex){
            Log.e("GirdAdapter",""+ex.getMessage());
        }
        return convertView;
    }
}
