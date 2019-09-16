package com.willemgeo.szdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.willemgeo.szdb.adapter.XianSpinnerAdapter;
import com.willemgeo.szdb.bean.cun;
import com.willemgeo.szdb.bean.xian;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Context context;

    public Spinner xian_spinner;
    public Spinner cun_spinner;
    public EditText dbr_txt;
    public EditText sfz_txt;
    public int selectType;
    public List<xian> xianList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

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
     * 初始化数据
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
    }

    /**
     * 初始化界面元素
     */
    private void initView(){
        XianSpinnerAdapter xianAdapter = new XianSpinnerAdapter(MainActivity.this,xianList);
        /*mSpinnerSelf.setAdapter(adapter);
        mSpinnerSelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ToastUtil.showShort(instance,((CarBean)adapter.getItem(pos)).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
    }

    /**
     * 选择点按钮
     */
    private View.OnClickListener camera_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            final String[] item = { "户口本", "身份证", "个人申请书" ,"核对承诺书" ,"入户调查表" ,"残疾证" ,"病例" ,"病例" ,"有折账号","土地使用证","其他证明","建档立卡户证明"};
            AlertDialog.Builder singleDialog = new AlertDialog.Builder(MainActivity.this);
            singleDialog.setIcon(R.mipmap.ic_launcher_round);
            singleDialog.setTitle("单选 AlertDialog");
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
}
