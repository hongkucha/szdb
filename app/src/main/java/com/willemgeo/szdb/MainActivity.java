package com.willemgeo.szdb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import com.willemgeo.szdb.bean.Img;
import com.willemgeo.szdb.bean.cun;
import com.willemgeo.szdb.bean.xian;
import com.willemgeo.szdb.dao.ImgDao;
import com.willemgeo.szdb.service.GpsService;
import com.willemgeo.szdb.utils.BitmapUtil;
import com.willemgeo.szdb.utils.DBConfig;
import com.willemgeo.szdb.utils.DBHelper;
import com.willemgeo.szdb.utils.JIdCardUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private Button btnGrid;
    private String picName;
    private String picPath;
    private DBHelper db;
    private boolean startGps = false;

    private static String TAG = MainActivity.class.getSimpleName();

    private Double latitude = Double.NaN;
    private Double longitude = Double.NaN;
    private Location location = null;
    private GpsReceiver gpsReceiver = new GpsReceiver();

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

        btnGrid = findViewById(R.id.grid_btn);
        btnGrid.setOnClickListener(grid_btn_click);

        initGpsService();

        initData();

        initView();

        try {
            db = new DBHelper(getApplicationContext());
            ImgDao img = db.createImgDao();
            List<Img> lst = img.findAll();
            DBConfig.setInstance(new DBConfig(getApplicationContext()));
            DBConfig config = DBConfig.getInstance();
            Map map = config.getCJQY();
            Map mapX = config.getXJQY();

            String ss ="";
        }catch (Exception ex){
            Log.e("ORM",""+ex.getMessage());
        }

    }

    /**
     * 初始化GPS服务
     */
    private void initGpsService() {
        android.util.Log.i(TAG, "initGpsService");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            // 返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1001);
        } else {
            startGps = true;
            Intent gpsService = new Intent(MainActivity.this, GpsService.class);
            MainActivity.this.startService(gpsService);
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.esri.androidStatistics.service.GpsService");
            MainActivity.this.registerReceiver(gpsReceiver, filter);
        }

    }

    /**
     * 初始化数据和文件目录
     */
    private void initData(){
        xianList = new ArrayList<xian>();

        xian x = new xian("绥中镇","211421100");
        cun c = new cun("绥中镇","211421100000");
        x.cunList.add(c);
        c = new cun("和平居委会","211421100001");
        x.cunList.add(c);
        c = new cun("兴隆居委会","211421100002");
        x.cunList.add(c);
        c = new cun("东大居委会","211421100003");
        x.cunList.add(c);
        c = new cun("西大居委会","211421100004");
        x.cunList.add(c);
        c = new cun("城内东居委会","211421100005");
        x.cunList.add(c);
        c = new cun("城内西居委会","211421100006");
        x.cunList.add(c);
        c = new cun("西关居委会","211421100007");
        x.cunList.add(c);
        c = new cun("文化居委会","211421100008");
        x.cunList.add(c);
        c = new cun("西山居委会","211421100009");
        x.cunList.add(c);
        c = new cun("新兴居委会","211421100010");
        x.cunList.add(c);
        c = new cun("工人居委会","211421100011");
        x.cunList.add(c);
        c = new cun("路南居委会","211421100012");
        x.cunList.add(c);
        c = new cun("工人社区","211421100013");
        x.cunList.add(c);
        c = new cun("和平社区","211421100014");
        x.cunList.add(c);
        c = new cun("内东社区","211421100015");
        x.cunList.add(c);
        c = new cun("文化社区","211421100016");
        x.cunList.add(c);
        c = new cun("西关社区","211421100017");
        x.cunList.add(c);
        c = new cun("西山社区","211421100018");
        x.cunList.add(c);
        c = new cun("兴隆社区","211421100019");
        x.cunList.add(c);
        c = new cun("和平居委会","211421100020");
        x.cunList.add(c);

        xianList.add(x);

        x = new xian("永安堡乡","211421211");
        c = new cun("永安堡乡","211421211000");
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

        x = new xian("王宝镇","211421114");
        c = new cun("王宝镇","211421114000");
        x.cunList.add(c);
        c = new cun("王宝村","211421114200");
        x.cunList.add(c);
        c = new cun("北偏坡村","211421114201");
        x.cunList.add(c);
        c = new cun("黄罗山村","211421114202");
        x.cunList.add(c);
        c = new cun("张呆子村","211421114203");
        x.cunList.add(c);
        c = new cun("西潘村","211421114204");
        x.cunList.add(c);
        c = new cun("西孙村","211421114205");
        x.cunList.add(c);
        c = new cun("平房村","211421114206");
        x.cunList.add(c);
        c = new cun("娄家屯村","211421114207");
        x.cunList.add(c);
        c = new cun("吕贡村","211421114208");
        x.cunList.add(c);
        c = new cun("王汉村","211421114209");
        x.cunList.add(c);
        c = new cun("揣家村","211421114210");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("万家镇","211421104");
        c = new cun("万家镇","211421104000");
        x.cunList.add(c);
        c = new cun("万家镇居委会","211421104001");
        x.cunList.add(c);
        c = new cun("王家庄村","211421104200");
        x.cunList.add(c);
        c = new cun("新民村","211421104201");
        x.cunList.add(c);
        c = new cun("甘家村","211421104202");
        x.cunList.add(c);
        c = new cun("老户村","211421104203");
        x.cunList.add(c);
        c = new cun("赵乡村","211421104204");
        x.cunList.add(c);
        c = new cun("周家村","211421104205");
        x.cunList.add(c);
        c = new cun("杨家屯村","211421104206");
        x.cunList.add(c);
        c = new cun("贺家屯村","211421104207");
        x.cunList.add(c);
        c = new cun("孟家村","211421104208");
        x.cunList.add(c);
        c = new cun("金丝村","211421104209");
        x.cunList.add(c);
        c = new cun("万家村","211421104210");
        x.cunList.add(c);
        c = new cun("北邱洼村","211421104211");
        x.cunList.add(c);
        c = new cun("万寿寺村","211421104212");
        x.cunList.add(c);
        c = new cun("老军屯村","211421104213");
        x.cunList.add(c);
        c = new cun("苏家村","211421104214");
        x.cunList.add(c);
        c = new cun("滨海街","211421104215");
        x.cunList.add(c);
        c = new cun("止锚湾村","211421104216");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("沙河镇","211421115");
        c = new cun("沙河镇","211421115000");
        x.cunList.add(c);
        c = new cun("大台山社区","211421115001");
        x.cunList.add(c);
        c = new cun("沙河村","211421115200");
        x.cunList.add(c);
        c = new cun("马家河村","211421115201");
        x.cunList.add(c);
        c = new cun("沙岭杨村","211421115202");
        x.cunList.add(c);
        c = new cun("江家岭村","211421115203");
        x.cunList.add(c);
        c = new cun("张胡岭村","211421115204");
        x.cunList.add(c);
        c = new cun("冯万村","211421115205");
        x.cunList.add(c);
        c = new cun("三台子村","211421115206");
        x.cunList.add(c);
        c = new cun("前周村","211421115207");
        x.cunList.add(c);
        c = new cun("沙河西村","211421115208");
        x.cunList.add(c);
        c = new cun("南山村","211421115209");
        x.cunList.add(c);
        c = new cun("项家村","211421115210");
        x.cunList.add(c);
        c = new cun("宋家沟村","211421115211");
        x.cunList.add(c);
        c = new cun("金沟村","211421115212");
        x.cunList.add(c);
        c = new cun("横河子村","211421115213");
        x.cunList.add(c);
        c = new cun("叶家村","211421115214");
        x.cunList.add(c);
        c = new cun("板桥村","211421115215");
        x.cunList.add(c);
        c = new cun("营盘山村","211421115216");
        x.cunList.add(c);
        c = new cun("叶大屯村","211421115217");
        x.cunList.add(c);
        c = new cun("狗河城村","211421115218");
        x.cunList.add(c);
        c = new cun("马蹄沟村","211421115219");
        x.cunList.add(c);
        c = new cun("桃树沟村","211421115220");
        x.cunList.add(c);
        c = new cun("小官帽村","211421115221");
        x.cunList.add(c);
        c = new cun("大台山","211421115222");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("秋子沟乡","211421208");
        c = new cun("秋子沟乡","211421208000");
        x.cunList.add(c);
        c = new cun("东山根村","211421208200");
        x.cunList.add(c);
        c = new cun("西山村","211421208201");
        x.cunList.add(c);
        c = new cun("八家子村","211421208202");
        x.cunList.add(c);
        c = new cun("梁杖子村","211421208203");
        x.cunList.add(c);
        c = new cun("时杖子村","211421208204");
        x.cunList.add(c);
        c = new cun("谭杖子村","211421208205");
        x.cunList.add(c);
        c = new cun("腰岭子村","211421208206");
        x.cunList.add(c);
        c = new cun("大杨树沟村","211421208207");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("前卫镇","211421107");
        c = new cun("前卫镇","211421107000");
        x.cunList.add(c);
        c = new cun("前卫镇居委会","211421107001");
        x.cunList.add(c);
        c = new cun("兴卫居委会","211421115200");
        x.cunList.add(c);
        c = new cun("马家河村","211421115201");
        x.cunList.add(c);
        c = new cun("沙岭杨村","211421115202");
        x.cunList.add(c);
        c = new cun("江家岭村","211421115203");
        x.cunList.add(c);
        c = new cun("张胡岭村","211421115204");
        x.cunList.add(c);
        c = new cun("冯万村","211421115205");
        x.cunList.add(c);
        c = new cun("三台子村","211421115206");
        x.cunList.add(c);
        c = new cun("前周村","211421115207");
        x.cunList.add(c);
        c = new cun("沙河西村","211421115208");
        x.cunList.add(c);
        c = new cun("南山村","211421115209");
        x.cunList.add(c);
        c = new cun("项家村","211421115210");
        x.cunList.add(c);
        c = new cun("宋家沟村","211421115211");
        x.cunList.add(c);
        c = new cun("金沟村","211421115212");
        x.cunList.add(c);
        c = new cun("横河子村","211421115213");
        x.cunList.add(c);
        c = new cun("叶家村","211421115214");
        x.cunList.add(c);
        c = new cun("板桥村","211421115215");
        x.cunList.add(c);
        c = new cun("营盘山村","211421115216");
        x.cunList.add(c);
        c = new cun("叶大屯村","211421115217");
        x.cunList.add(c);
        c = new cun("狗河城村","211421115218");
        x.cunList.add(c);
        c = new cun("马蹄沟村","211421115219");
        x.cunList.add(c);
        c = new cun("桃树沟村","211421115220");
        x.cunList.add(c);
        c = new cun("小官帽村","211421115221");
        x.cunList.add(c);
        c = new cun("大台山","211421115222");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("前所镇","211421105");
        c = new cun("前所镇","211421105000");
        x.cunList.add(c);
        c = new cun("前所居委会","211421105001");
        x.cunList.add(c);
        c = new cun("前所村","211421105200");
        x.cunList.add(c);
        c = new cun("牛羊沟村","211421105201");
        x.cunList.add(c);
        c = new cun("大柳村","211421105202");
        x.cunList.add(c);
        c = new cun("何家村","211421105203");
        x.cunList.add(c);
        c = new cun("东杨家村","211421105204");
        x.cunList.add(c);
        c = new cun("小松岭村","211421105205");
        x.cunList.add(c);
        c = new cun("杨家村","211421105206");
        x.cunList.add(c);
        c = new cun("大赵村","211421105207");
        x.cunList.add(c);
        c = new cun("洪家村","211421105208");
        x.cunList.add(c);
        c = new cun("韩家屯村","211421105209");
        x.cunList.add(c);
        c = new cun("大架子村","211421105210");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("明水满族乡","211421207");
        c = new cun("明水满族乡","211421207000");
        x.cunList.add(c);
        c = new cun("明水村","211421207200");
        x.cunList.add(c);
        c = new cun("平河子村","211421207201");
        x.cunList.add(c);
        c = new cun("东洼子村","211421207202");
        x.cunList.add(c);
        c = new cun("盘龙沟村","211421207203");
        x.cunList.add(c);
        c = new cun("下屯村","211421207204");
        x.cunList.add(c);
        c = new cun("张富沟村","211421207205");
        x.cunList.add(c);
        c = new cun("杨杖子村","211421207206");
        x.cunList.add(c);
        c = new cun("姚家村","211421207207");
        x.cunList.add(c);
        c = new cun("四间房村","211421207208");
        x.cunList.add(c);
        c = new cun("祝家沟村","211421207209");
        x.cunList.add(c);
        c = new cun("小杨树沟村","211421207210");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("李家堡乡","211421212");
        c = new cun("李家堡乡","211421212000");
        x.cunList.add(c);
        c = new cun("李家堡村","211421212200");
        x.cunList.add(c);
        c = new cun("阎家岭村","211421212201");
        x.cunList.add(c);
        c = new cun("上荆村","211421212202");
        x.cunList.add(c);
        c = new cun("下荆村","211421212203");
        x.cunList.add(c);
        c = new cun("石牌坊村","211421212204");
        x.cunList.add(c);
        c = new cun("常家沟村","211421212205");
        x.cunList.add(c);
        c = new cun("大堡子村","211421212206");
        x.cunList.add(c);
        c = new cun("新堡子村","211421212207");
        x.cunList.add(c);
        c = new cun("张家场村","211421212208");
        x.cunList.add(c);
        c = new cun("马营子村","211421212209");
        x.cunList.add(c);
        c = new cun("边门村","211421212210");
        x.cunList.add(c);
        c = new cun("铁厂堡村","211421212211");
        x.cunList.add(c);
        c = new cun("老虎圈村","211421212212");
        x.cunList.add(c);
        c = new cun("娄家沟村","211421212213");
        x.cunList.add(c);
        c = new cun("秋皮沟村","211421212214");
        x.cunList.add(c);
        c = new cun("北石门村","211421212215");
        x.cunList.add(c);
        c = new cun("王凤台社区","211421212216");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("宽帮满族镇","211421102");
        c = new cun("宽帮满族镇","211421102000");
        x.cunList.add(c);
        c = new cun("宽帮居委会","211421102001");
        x.cunList.add(c);
        c = new cun("宽帮村","211421102200");
        x.cunList.add(c);
        c = new cun("项屯村","211421102201");
        x.cunList.add(c);
        c = new cun("西台山村","211421102202");
        x.cunList.add(c);
        c = new cun("头道河村","211421102203");
        x.cunList.add(c);
        c = new cun("窝棚沟村","211421102204");
        x.cunList.add(c);
        c = new cun("黑鱼沟村","211421102205");
        x.cunList.add(c);
        c = new cun("大粟屯村","211421102206");
        x.cunList.add(c);
        c = new cun("大河西村","211421102207");
        x.cunList.add(c);
        c = new cun("胜利村","211421102208");
        x.cunList.add(c);
        c = new cun("杨树沟村","211421102209");
        x.cunList.add(c);
        c = new cun("大房身村","211421102210");
        x.cunList.add(c);
        c = new cun("棒槌沟村","211421102211");
        x.cunList.add(c);
        c = new cun("观音沟村","211421102212");
        x.cunList.add(c);
        c = new cun("西边村","211421102213");
        x.cunList.add(c);
        c = new cun("西岔沟村","211421102214");
        x.cunList.add(c);
        c = new cun("东岔沟村","211421102215");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("加碑岩乡","211421210");
        c = new cun("加碑岩乡","211421210000");
        x.cunList.add(c);
        c = new cun("东梢村","211421210200");
        x.cunList.add(c);
        c = new cun("旧关村","211421210201");
        x.cunList.add(c);
        c = new cun("王台村","211421210202");
        x.cunList.add(c);
        c = new cun("楼房村","211421210203");
        x.cunList.add(c);
        c = new cun("沟口村","211421210204");
        x.cunList.add(c);
        c = new cun("黄土梁子村","211421210205");
        x.cunList.add(c);
        c = new cun("李大庄村","211421210206");
        x.cunList.add(c);
        c = new cun("黄木杖子村","211421210207");
        x.cunList.add(c);
        c = new cun("王家店村","211421210208");
        x.cunList.add(c);
        c = new cun("南骆驼洞村","211421210209");
        x.cunList.add(c);
        c = new cun("三岔子村","211421210210");
        x.cunList.add(c);
        c = new cun("毛杖子村","211421210211");
        x.cunList.add(c);
        c = new cun("张杖子村","211421210212");
        x.cunList.add(c);
        c = new cun("窝岭村","211421210213");
        x.cunList.add(c);
        c = new cun("上房子村","211421210214");
        x.cunList.add(c);
        c = new cun("骆杖子村","211421210215");
        x.cunList.add(c);
        c = new cun("于杖子村","211421210216");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("荒地满族镇","211421108");
        c = new cun("荒地满族镇","211421108000");
        x.cunList.add(c);
        c = new cun("荒地居委会","211421108001");
        x.cunList.add(c);
        c = new cun("东荒村","211421108200");
        x.cunList.add(c);
        c = new cun("西荒地村","211421108201");
        x.cunList.add(c);
        c = new cun("桑园村","211421108202");
        x.cunList.add(c);
        c = new cun("榆林村","211421108203");
        x.cunList.add(c);
        c = new cun("西李村","211421108204");
        x.cunList.add(c);
        c = new cun("东李村","211421108205");
        x.cunList.add(c);
        c = new cun("大郑村","211421108206");
        x.cunList.add(c);
        c = new cun("尚家村","211421108207");
        x.cunList.add(c);
        c = new cun("牛心屯村","211421108208");
        x.cunList.add(c);
        c = new cun("前马屯村","211421108209");
        x.cunList.add(c);
        c = new cun("拴科村","211421108210");
        x.cunList.add(c);
        c = new cun("孙相村","211421108211");
        x.cunList.add(c);
        c = new cun("郭家屯村","211421108212");
        x.cunList.add(c);
        c = new cun("西台屯村","211421108213");
        x.cunList.add(c);
        c = new cun("杨保屯村","211421108214");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("塔山屯镇","211421109");
        c = new cun("塔山屯镇","211421109000");
        x.cunList.add(c);
        c = new cun("塔山居委会","211421109001");
        x.cunList.add(c);
        c = new cun("塔山村","211421109200");
        x.cunList.add(c);
        c = new cun("桃花村","211421109201");
        x.cunList.add(c);
        c = new cun("张白谷村","211421109202");
        x.cunList.add(c);
        c = new cun("西王村","211421109203");
        x.cunList.add(c);
        c = new cun("大钟鼓村","211421109204");
        x.cunList.add(c);
        c = new cun("潘家村","211421109205");
        x.cunList.add(c);
        c = new cun("丁家村","211421109206");
        x.cunList.add(c);
        c = new cun("大冯村","211421109207");
        x.cunList.add(c);
        c = new cun("许家村","211421109208");
        x.cunList.add(c);
        c = new cun("东白村","211421109209");
        x.cunList.add(c);
        c = new cun("四方村","211421109210");
        x.cunList.add(c);
        c = new cun("大南铺村","211421109211");
        x.cunList.add(c);
        c = new cun("白龙滩村","211421109212");
        x.cunList.add(c);
        c = new cun("山角子村","211421109213");
        x.cunList.add(c);
        c = new cun("香宝村","211421109214");
        x.cunList.add(c);
        c = new cun("梁家村","211421109215");
        x.cunList.add(c);
        c = new cun("大施宝村","211421109216");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("葛家满族乡","211421202");
        c = new cun("葛家满族乡","211421202000");
        x.cunList.add(c);
        c = new cun("广裕店村","211421202200");
        x.cunList.add(c);
        c = new cun("木锨沟村","211421202201");
        x.cunList.add(c);
        c = new cun("石棚沟村","211421202202");
        x.cunList.add(c);
        c = new cun("小盘岭村","211421202203");
        x.cunList.add(c);
        c = new cun("葛家屯村","211421202204");
        x.cunList.add(c);
        c = new cun("平台子村","211421202205");
        x.cunList.add(c);
        c = new cun("新房子村","211421202206");
        x.cunList.add(c);
        c = new cun("高立沟村","211421202207");
        x.cunList.add(c);
        c = new cun("黄土坡村","211421202208");
        x.cunList.add(c);
        c = new cun("二道房子村","211421202209");
        x.cunList.add(c);
        c = new cun("高鹦村","211421202210");
        x.cunList.add(c);
        c = new cun("陈家屯村","211421202211");
        x.cunList.add(c);
        c = new cun("小西台子村","211421202212");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("高台镇","211421113");
        c = new cun("高台镇","211421113000");
        x.cunList.add(c);
        c = new cun("高台堡村","211421113200");
        x.cunList.add(c);
        c = new cun("金屯村","211421113201");
        x.cunList.add(c);
        c = new cun("水口村","211421113202");
        x.cunList.add(c);
        c = new cun("洼甸村","211421113203");
        x.cunList.add(c);
        c = new cun("二道岭村","211421113204");
        x.cunList.add(c);
        c = new cun("胡家村","211421113205");
        x.cunList.add(c);
        c = new cun("前朱岭村","211421113206");
        x.cunList.add(c);
        c = new cun("腰古城寨村","211421113207");
        x.cunList.add(c);
        c = new cun("后古城寨村","211421113208");
        x.cunList.add(c);
        c = new cun("三道村","211421113209");
        x.cunList.add(c);
        c = new cun("小三道沟村","211421113210");
        x.cunList.add(c);
        c = new cun("牛彦沟村","211421113211");
        x.cunList.add(c);
        c = new cun("万陈村","211421113212");
        x.cunList.add(c);
        c = new cun("穆家沟村","211421113213");
        x.cunList.add(c);
        c = new cun("北赵村","211421113214");
        x.cunList.add(c);
        c = new cun("卢屯村","211421113215");
        x.cunList.add(c);
        c = new cun("老家村","211421113216");
        x.cunList.add(c);
        c = new cun("黑水台村","211421113217");
        x.cunList.add(c);
        c = new cun("马路岭村","211421113218");
        x.cunList.add(c);
        c = new cun("项木庄村","211421113219");
        x.cunList.add(c);
        c = new cun("莲花池村","211421113220");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("高岭镇","211421106");
        c = new cun("高岭镇","211421106000");
        x.cunList.add(c);
        c = new cun("高岭居委会","211421106001");
        x.cunList.add(c);
        c = new cun("高岭村","211421106200");
        x.cunList.add(c);
        c = new cun("四方台村","211421106201");
        x.cunList.add(c);
        c = new cun("上甸子村","211421106202");
        x.cunList.add(c);
        c = new cun("兴隆店村","211421106203");
        x.cunList.add(c);
        c = new cun("杨总村","211421106204");
        x.cunList.add(c);
        c = new cun("祝总村","211421106205");
        x.cunList.add(c);
        c = new cun("王岗台村","211421106206");
        x.cunList.add(c);
        c = new cun("高家洼村","211421106207");
        x.cunList.add(c);
        c = new cun("陡坡台村","211421106208");
        x.cunList.add(c);
        c = new cun("大松岭村","211421106209");
        x.cunList.add(c);
        c = new cun("贺家村","211421106210");
        x.cunList.add(c);
        c = new cun("赵家屯村","211421106211");
        x.cunList.add(c);
        c = new cun("大历蝗村","211421106212");
        x.cunList.add(c);
        c = new cun("小历蝗村","211421106213");
        x.cunList.add(c);
        c = new cun("照山嘴村","211421106214");
        x.cunList.add(c);
        c = new cun("沙锅子村","211421106215");
        x.cunList.add(c);
        c = new cun("老爷庙村","211421106216");
        x.cunList.add(c);
        c = new cun("杏树屯村","211421106217");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("高甸子满族乡","211421204");
        c = new cun("高甸子满族乡","211421204000");
        x.cunList.add(c);
        c = new cun("高甸子村","211421204200");
        x.cunList.add(c);
        c = new cun("古城子村","211421204201");
        x.cunList.add(c);
        c = new cun("獐子沟村","211421204202");
        x.cunList.add(c);
        c = new cun("大獐子沟村","211421204203");
        x.cunList.add(c);
        c = new cun("转子沟村","211421204204");
        x.cunList.add(c);
        c = new cun("袁家屯村","211421204205");
        x.cunList.add(c);
        c = new cun("马鞍山村","211421204206");
        x.cunList.add(c);
        c = new cun("朱仙屯村","211421204207");
        x.cunList.add(c);
        c = new cun("苏兰村","211421204208");
        x.cunList.add(c);
        c = new cun("冯家村","211421204209");
        x.cunList.add(c);
        c = new cun("长岭子村","211421204210");
        x.cunList.add(c);
        c = new cun("陈荫沟村","211421204211");
        x.cunList.add(c);
        c = new cun("狼洞子村","211421204212");
        x.cunList.add(c);
        c = new cun("赵屯村","211421204213");
        x.cunList.add(c);
        c = new cun("西赵屯村","211421204214");
        x.cunList.add(c);
        c = new cun("刘把屯村","211421106214");
        x.cunList.add(c);
        c = new cun("沙锅子村","211421204215");
        x.cunList.add(c);
        c = new cun("糜子沟村","211421204216");
        x.cunList.add(c);
        c = new cun("顺山堡村","211421204217");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("范家满族乡","211421206");
        c = new cun("范家满族乡","211421206000");
        x.cunList.add(c);
        c = new cun("范家村","211421206200");
        x.cunList.add(c);
        c = new cun("朱家村","211421206201");
        x.cunList.add(c);
        c = new cun("邱家村","211421206202");
        x.cunList.add(c);
        c = new cun("薛家村","211421206203");
        x.cunList.add(c);
        c = new cun("条石沟村","211421206204");
        x.cunList.add(c);
        c = new cun("鲍庄子村","211421206205");
        x.cunList.add(c);
        c = new cun("小胡口村","211421206206");
        x.cunList.add(c);
        c = new cun("钩鱼石村","211421206207");
        x.cunList.add(c);
        c = new cun("五台子村","211421206208");
        x.cunList.add(c);
        c = new cun("平川营村","211421206209");
        x.cunList.add(c);
        c = new cun("涝豆沟村","211421206210");
        x.cunList.add(c);
        c = new cun("弓箭沟村","211421206211");
        x.cunList.add(c);
        c = new cun("银庄子村","211421206212");
        x.cunList.add(c);
        c = new cun("弯土墙村","211421206213");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("大王庙满族镇","211421103");
        c = new cun("大王庙满族镇","211421103000");
        x.cunList.add(c);
        c = new cun("大王庙居委会","211421103001");
        x.cunList.add(c);
        c = new cun("大王庙村","211421103200");
        x.cunList.add(c);
        c = new cun("大黄羊沟村","211421103201");
        x.cunList.add(c);
        c = new cun("小黄羊沟村","211421103202");
        x.cunList.add(c);
        c = new cun("李金屯村","211421103203");
        x.cunList.add(c);
        c = new cun("黄土坎村","211421103204");
        x.cunList.add(c);
        c = new cun("石山沟村","211421103205");
        x.cunList.add(c);
        c = new cun("吴二沟村","211421103206");
        x.cunList.add(c);
        c = new cun("慈愍庵村","211421103207");
        x.cunList.add(c);
        c = new cun("水泉沟村","211421103208");
        x.cunList.add(c);
        c = new cun("砬子山村","211421103209");
        x.cunList.add(c);
        c = new cun("魏家屯村","211421103210");
        x.cunList.add(c);
        c = new cun("西双山村","211421103211");
        x.cunList.add(c);
        c = new cun("东双山村","211421103212");
        x.cunList.add(c);
        c = new cun("黄家屯村","211421103213");
        x.cunList.add(c);
        c = new cun("毛家沟村","211421103214");
        x.cunList.add(c);
        c = new cun("孤山子村","211421103215");
        x.cunList.add(c);
        c = new cun("高家岭村","211421103216");
        x.cunList.add(c);
        c = new cun("叶红旗村","211421103217");
        x.cunList.add(c);
        c = new cun("东沟村","211421103218");
        x.cunList.add(c);
        c = new cun("车道岭村","211421103219");
        x.cunList.add(c);
        c = new cun("山嘴子村","211421103220");
        x.cunList.add(c);
        c = new cun("红庙子村","211421103221");
        x.cunList.add(c);
        c = new cun("双龙沟村","211421103222");
        x.cunList.add(c);
        c = new cun("高杖子村","211421103223");
        x.cunList.add(c);
        c = new cun("烧锅杖子村","211421103224");
        x.cunList.add(c);
        c = new cun("专杖子村","211421103225");
        x.cunList.add(c);
        xianList.add(x);

        x = new xian("城郊乡","211421217");
        c = new cun("城郊乡","211421217000");
        x.cunList.add(c);
        c = new cun("肖家村","211421217200");
        x.cunList.add(c);
        c = new cun("城内东村","211421217201");
        x.cunList.add(c);
        c = new cun("西关村","211421217202");
        x.cunList.add(c);
        c = new cun("韩家洼村","211421217203");
        x.cunList.add(c);
        c = new cun("兴隆村","211421217204");
        x.cunList.add(c);
        c = new cun("西园子村","211421217205");
        x.cunList.add(c);
        c = new cun("香坊村","211421217206");
        x.cunList.add(c);
        c = new cun("东园子村","211421217207");
        x.cunList.add(c);
        c = new cun("周石村","211421217210");
        x.cunList.add(c);
        c = new cun("马圈村","211421217211");
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
    final String[] item = { "户口本", "身份证", "个人申请书" ,"核对承诺书" ,"入户调查表" ,"残疾证" ,"病例" ,"有折账号","土地使用证","其他证明","建档立卡户证明"};
    /**
     * 选择点按钮
     */
    private View.OnClickListener camera_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            try {

            /*获取低保人信息和身份证信息*/
                dbr = dbr_txt.getText().toString();
                sfz = sfz_txt.getText().toString();
                if(dbr.trim().isEmpty() || sfz.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"请先填写身份信息。",Toast.LENGTH_SHORT).show();
                    return;
                }
                JIdCardUtils utils = new JIdCardUtils();
                if(utils.validate(sfz.trim()) == false){
                    Toast.makeText(getApplicationContext(),"身份证格式存在错误！",Toast.LENGTH_SHORT).show();
                    return;
                }

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
                        String url = fileRoute + "/" + selectedXianCode + "/" + selectedCunCode + "/" + sfz;
                        File file = new File(url);
                        if (!file.exists())
                            file.mkdirs();

                    /*判断照片类型路径层级是否存在*/
                        url = url + "/" + item[selectType];
                        file = new File(url);
                        if (!file.exists())
                            file.mkdirs();

                    /*开启拍照*/
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                        picName = java.util.UUID.randomUUID().toString();
                        picPath = CT_DATA_PATH + CT_DATA_PATH_IMG + "/" + selectedXianCode + "/" + selectedCunCode + "/" + sfz + "/" + item[selectType] + "/" + picName + ".jpg";
                        //Uri uri = Uri.fromFile(new File(url+"/"+picName+".jpg"));//根据图片路径生成一个uri
                        File photo = new File(url + "/" + picName + ".jpg");

                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", photo);

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);//设置相机拍照图片保存的位置

                        ((Activity) context).startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }
                });
                singleDialog.show();
            }catch (Exception ex){
                ex.printStackTrace();
            }
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
    /**
     * 查看img list 按钮
     */
    private View.OnClickListener grid_btn_click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(getApplicationContext(),GridActivity.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE  && resultCode!= 0) {

            Img img = new Img();
            img.setUid(picName);
            try {
                img.setDbrmc(dbr);
            } catch (Exception ex) {
            }
            try {
                img.setCjbm(selectedCunCode);
            } catch (Exception ex) {
            }
            try {
                img.setXjbm(selectedXianCode);
            } catch (Exception ex) {
            }
            try {
                img.setZjhm(sfz);
            } catch (Exception ex) {
            }
            try {
                img.setImgpath(picPath);
            } catch (Exception ex) {
            }
            try {
                img.setImgtype(item[selectType]);
            } catch (Exception ex) {
            }
            try {
                img.setIsupload(false);
            } catch (Exception ex) {
            }
            try {
                img.setX(latitude);
            } catch (Exception ex) {
            }
            try {
                img.setY(longitude);
            } catch (Exception ex) {
            }
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                img.setCreateTime(date);
            } catch (Exception ex) {
            }

            //开始存储信息
            BitmapUtil.saveBitmapInfo(img, null, db);
        }

        if(requestCode == 1001){
            startGps = true;
            Intent gpsService = new Intent(MainActivity.this, GpsService.class);
            MainActivity.this.startService(gpsService);
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.esri.androidStatistics.service.GpsService");
            MainActivity.this.registerReceiver(gpsReceiver, filter);
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
                Toast.makeText(this,"监督检查需要拍照录音权限",Toast.LENGTH_SHORT).show();
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


    public class GpsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            android.util.Log.i(TAG, "GpsReceiver.onReceive");
            Bundle bundle = intent.getExtras();
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            location = bundle.getParcelable("Location");
        }
    }

    long timems = 0;
    @Override
    public void onBackPressed() {


            if(Math.abs(timems - new Date().getTime())>2000) {
                Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                timems = new Date().getTime();
            }else {
                super.onBackPressed();
            }
    }
}
