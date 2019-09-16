package com.willemgeo.szdb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.willemgeo.szdb.adapter.CunSpinnerAdapter;
import com.willemgeo.szdb.adapter.XianSpinnerAdapter;
import com.willemgeo.szdb.base.BaseResult;
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.bean.JTCY;
import com.willemgeo.szdb.bean.cun;
import com.willemgeo.szdb.bean.xian;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.willemgeo.szdb.base.Constants.CT_DATA_PATH;
import static com.willemgeo.szdb.base.Constants.CT_DATA_PATH_IMG;

public class MainActivity extends AppCompatActivity {

    public Context context;

    public Spinner xian_spinner;
    public Spinner cun_spinner;
    public EditText dbr_txt;
    public EditText sfz_txt;
    public String dbr="";
    public String sfz="";
    public int selectType;
    public List<xian> xianList;

    public String selectedXianName;
    public String selectedXianCode;
    public String selectedCunName;
    public String selectedCunCode;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10013;
    private static String fileRoute = Environment.getExternalStorageDirectory().getPath() + CT_DATA_PATH + CT_DATA_PATH_IMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        /*申请权限*/
        getPermissions();

        xian_spinner = (Spinner) this.findViewById(R.id.xian_name);
        cun_spinner = (Spinner) this.findViewById(R.id.cun_name);
        dbr_txt = (EditText) this.findViewById(R.id.dbr_name);
        sfz_txt = (EditText) this.findViewById(R.id.sfz_number);

        //拍照按钮
        ((Button) this.findViewById(R.id.camera_btn)).setOnClickListener(camera_btn_Click);

        //保存上传按钮
        ((Button) this.findViewById(R.id.save_btn)).setOnClickListener(save_btn_Click);

        initData();

        initView();
    }

    /**
     * 初始化数据和文件目录
     */
    private void initData(){
        xianList = new ArrayList<xian>();

        xian x = new xian("永安堡乡","211421211");
        cun c = new cun("永安堡乡","211421211000");
        x.cunList.add(c);
        c = new cun("大甸子村","211421211200");
        x.cunList.add(c);
        c = new cun("西沟村","211421211201");
        x.cunList.add(c);
        c = new cun("立根台村","211421211202");
        x.cunList.add(c);
        c = new cun("花户庄村","211421211203");
        x.cunList.add(c);
        c = new cun("杨树村","211421211204");
        x.cunList.add(c);
        c = new cun("车场沟村","211421211205");
        x.cunList.add(c);
        c = new cun("边外村","211421211206");
        x.cunList.add(c);
        c = new cun("石匣口村","211421211207");
        x.cunList.add(c);
        c = new cun("永安堡村","211421211208");
        x.cunList.add(c);
        c = new cun("小山口村","211421211209");
        x.cunList.add(c);
        c = new cun("北河村","211421211210");
        x.cunList.add(c);
        c = new cun("獐狼铳村","211421211211");
        x.cunList.add(c);
        c = new cun("张家房村","211421211212");
        x.cunList.add(c);
        c = new cun("塔子沟","211421211213");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("小庄子镇","211421116");
        c = new cun("小庄子镇","211421116000");
        x.cunList.add(c);
        c = new cun("鄢家屯村","211421116200");
        x.cunList.add(c);
        c = new cun("石官村","211421116201");
        x.cunList.add(c);
        c = new cun("新庄子村","211421116202");
        x.cunList.add(c);
        c = new cun("大李金屯村","211421116203");
        x.cunList.add(c);
        c = new cun("小李村","211421116204");
        x.cunList.add(c);
        c = new cun("马家洼村","211421116205");
        x.cunList.add(c);
        c = new cun("团山子村","211421116206");
        x.cunList.add(c);
        c = new cun("盐锅村","211421116207");
        x.cunList.add(c);
        c = new cun("盐场村","211421116208");
        x.cunList.add(c);
        c = new cun("大渔场村","211421116209");
        x.cunList.add(c);
        c = new cun("宝仓村","211421116210");
        x.cunList.add(c);
        c = new cun("叶大村","211421116211");
        x.cunList.add(c);
        c = new cun("海泉村","211421116212");
        x.cunList.add(c);
        c = new cun("海泉西村","211421116213");
        x.cunList.add(c);
        c = new cun("彭家村","211421116214");
        x.cunList.add(c);
        c = new cun("永安村","211421116215");
        x.cunList.add(c);
        c = new cun("前永安寨村","211421116216");
        x.cunList.add(c);
        c = new cun("小庄子村","211421116217");
        x.cunList.add(c);
        c = new cun("凌家村","211421116218");
        x.cunList.add(c);
        c = new cun("孤家子村","211421116219");
        x.cunList.add(c);
        c = new cun("打雀庄子村","211421116220");
        x.cunList.add(c);
        c = new cun("张安村","211421116221");
        x.cunList.add(c);
        c = new cun("二河口","211421116222");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("西平坡满族乡","211421201");
        c = new cun("西平坡满族乡","211421201000");
        x.cunList.add(c);
        c = new cun("西平坡村","211421201200");
        x.cunList.add(c);
        c = new cun("东平坡村","211421201201");
        x.cunList.add(c);
        c = new cun("黑水汀村","211421201202");
        x.cunList.add(c);
        c = new cun("七里沟村","211421201203");
        x.cunList.add(c);
        c = new cun("岳家店村","211421201204");
        x.cunList.add(c);
        c = new cun("大路沟村","211421201205");
        x.cunList.add(c);
        c = new cun("康家壕村","211421201206");
        x.cunList.add(c);
        c = new cun("炉砟沟村","211421201207");
        x.cunList.add(c);
        c = new cun("南平坡村","211421201208");
        x.cunList.add(c);
        c = new cun("沙金沟村","211421201209");
        x.cunList.add(c);
        c = new cun("苇子沟村","211421201210");
        x.cunList.add(c);
        c = new cun("土头山村","211421201211");
        x.cunList.add(c);
        c = new cun("白杨沟村","211421201212");
        x.cunList.add(c);
        c = new cun("龙潭沟村","211421201213");
        x.cunList.add(c);
        c = new cun("上士头山村","211421201214");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("西甸子镇","211421101");
        c = new cun("西甸子镇","211421101000");
        x.cunList.add(c);
        c = new cun("西甸子居委会","211421101001");
        x.cunList.add(c);
        c = new cun("西甸子村","211421101200");
        x.cunList.add(c);
        c = new cun("小李屯村","211421101201");
        x.cunList.add(c);
        c = new cun("涝豆洼村","211421101202");
        x.cunList.add(c);
        c = new cun("白庙子村","211421101203");
        x.cunList.add(c);
        c = new cun("东庄村","211421101204");
        x.cunList.add(c);
        c = new cun("马圈子村","211421101205");
        x.cunList.add(c);
        c = new cun("安马堡村","211421101206");
        x.cunList.add(c);
        c = new cun("北甸子村","211421101207");
        x.cunList.add(c);
        c = new cun("北杨家村","211421101208");
        x.cunList.add(c);
        c = new cun("水泉子村","211421101209");
        x.cunList.add(c);
        c = new cun("皇姑村","211421101210");
        x.cunList.add(c);
        c = new cun("户尚屯村","211421101211");
        x.cunList.add(c);
        c = new cun("凉水村","211421101212");
        x.cunList.add(c);
        c = new cun("大石桥村","211421101213");
        x.cunList.add(c);
        c = new cun("双李村","211421101214");
        x.cunList.add(c);
        c = new cun("莲蓬洼村","211421101215");
        x.cunList.add(c);
        c = new cun("罗三家村","211421101216");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("网户满族乡","211421214");
        c = new cun("网户满族乡","211421214000");
        x.cunList.add(c);
        c = new cun("朴家村","211421214200");
        x.cunList.add(c);
        c = new cun("凉水河村","211421214201");
        x.cunList.add(c);
        c = new cun("侯家村","211421214202");
        x.cunList.add(c);
        c = new cun("西大甸子村","211421214203");
        x.cunList.add(c);
        c = new cun("河东村","211421214204");
        x.cunList.add(c);
        c = new cun("马山界村","211421214205");
        x.cunList.add(c);
        c = new cun("李哈村","211421214206");
        x.cunList.add(c);
        c = new cun("柴家村","211421214207");
        x.cunList.add(c);
        c = new cun("新立村","211421214208");
        x.cunList.add(c);
        c = new cun("孙家村","211421214209");
        x.cunList.add(c);
        c = new cun("程家村","211421214210");
        x.cunList.add(c);
        c = new cun("张监村","211421214211");
        x.cunList.add(c);
        c = new cun("小前村","211421214212");
        x.cunList.add(c);
        c = new cun("陈家村","211421214213");
        x.cunList.add(c);
        c = new cun("大宫帽村","211421214214");
        x.cunList.add(c);
        xianList.add(x);


        File file = new File(fileRoute);
        if (!file.exists())
            file.mkdirs();
        //创建所有目录
        for(int i = 0;i<xianList.size();i++)
        {
            xian x1 = xianList.get(i);
            file = new File(fileRoute+"/"+x1.XianCode);
            if (!file.exists())
                file.mkdirs();

            for(int j = 0;j<x1.cunList.size();j++)
            {
                cun c1 = x1.cunList.get(j);

                File file1 = new File(fileRoute+"/"+x1.XianCode+"/"+c1.CunCode);
                if (!file1.exists())
                    file1.mkdirs();
            }
        }
    }

    /**
     * 初始化界面元素
     */
    private int xian_position;
    private void initView(){
        final XianSpinnerAdapter xianAdapter = new XianSpinnerAdapter(context,xianList);
        xian_spinner.setAdapter(xianAdapter);
        xian_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedXianName = ((xian)xianAdapter.getItem(pos)).XianName.toString();
                selectedXianCode = ((xian)xianAdapter.getItem(pos)).XianCode.toString();
                xian_position = pos;

                final CunSpinnerAdapter cunAdapter = new CunSpinnerAdapter(MainActivity.this,((xian)xianAdapter.getItem(pos)).cunList);
                cun_spinner.setAdapter(cunAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        cun_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                List<cun> cunList = ((xian)xianAdapter.getItem(xian_position)).cunList;
                selectedCunName = ((cun)cunList.get(pos)).CunName.toString();
                selectedCunCode = ((cun)cunList.get(pos)).CunCode.toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

    /**
     * 选择点按钮
     */
    private View.OnClickListener camera_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            /*获取低保人信息和身份证信息*/
            dbr = dbr_txt.getText().toString();
            sfz = sfz_txt.getText().toString();

            final String[] item = { "户口本", "身份证", "个人申请书" ,"核对承诺书" ,"入户调查表" ,"残疾证" ,"病例" ,"有折账号","土地使用证","其他证明","建档立卡户证明"};
            AlertDialog.Builder singleDialog = new AlertDialog.Builder(MainActivity.this);
            singleDialog.setIcon(R.mipmap.ic_launcher_round);
            singleDialog.setTitle("选择拍照类型");
            singleDialog.setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectType = which;
                }
            });
            singleDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "你选择了：" + item[selectType], Toast.LENGTH_SHORT).show();

                    /*判断身份证路径层级是否存在*/
                    String url = fileRoute+"/"+selectedXianCode+"/"+selectedCunCode+"/"+sfz;
                    File file = new File(url);
                    if (!file.exists())
                        file.mkdirs();

                    /*判断照片类型路径层级是否存在*/
                    url = url+"/"+item[selectType];
                    file = new File(url);
                    if (!file.exists())
                        file.mkdirs();

                    /*开启拍照*/
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri = Uri.fromFile(new File(url+"/1.png"));//根据图片路径生成一个uri
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);//设置相机拍照图片保存的位置
                    ((Activity) context).startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            });
            singleDialog.show();

        }
    };

    /**
     * 保存上传按钮
     */
    private View.OnClickListener save_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            Img img = new Img();
            img.setDbrmc(dbr);
            img.setCjbm(selectedCunName);

        }
    }


    /**
     * 申请权限
     */
    //权限申请自定义码
    private final int GET_PERMISSION_REQUEST = 100;
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.RECORD_AUDIO)
                            == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                            == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this,"监督检查需要拍照录音权限",Toast.LENGTH_LONG).show();
                //不具有获取权限，需要进行权限申请
                this.requestPermissions( new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {

                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected byte[] toByteArray(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {

        }
        return out.toByteArray();
    }

    /**
     * 压缩图片
     *
     * @param bitmap   源图片
     * @param width    想要的宽度
     * @param height   想要的高度
     * @param isAdjust 是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     */
    protected Bitmap reduce(Bitmap bitmap, int width, int height, boolean isAdjust) {
        // 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            return bitmap;
        }
        // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode);
        // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
        float sx = new BigDecimal(width).divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN).floatValue();
        float sy = new BigDecimal(height).divide(new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN).floatValue();
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);
            sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
