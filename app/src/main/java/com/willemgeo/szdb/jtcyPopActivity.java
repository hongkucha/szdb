package com.willemgeo.szdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.willemgeo.szdb.bean.JTCY;

public class jtcyPopActivity extends AppCompatActivity {

    public Context context;
    public EditText xmtxt;
    public EditText ydbrgxtxt;
    public EditText sfzhtxt;
    public EditText srqktxt;
    public EditText stzktxt;
    public EditText qtqktxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jtcy_pop);

        context = this;
        //选择按钮
        ((Button) this.findViewById(R.id.save_btn)).setOnClickListener(save_btn_Click);
        ((Button) this.findViewById(R.id.back_btn)).setOnClickListener(back_btn_Click);
        xmtxt = (EditText) this.findViewById(R.id.xm);
        ydbrgxtxt = (EditText) this.findViewById(R.id.ydbhgx);
        sfzhtxt = (EditText) this.findViewById(R.id.sfzh);
        srqktxt = (EditText) this.findViewById(R.id.srqk);
        stzktxt = (EditText) this.findViewById(R.id.stzk);
        qtqktxt = (EditText) this.findViewById(R.id.qtqk);

    }

    /**
     * 选择点按钮
     */
    private View.OnClickListener save_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            JTCY jtcy = new JTCY();

            jtcy.setXm(xmtxt.getText().toString());
            jtcy.setYdbhgx(ydbrgxtxt.getText().toString());
            jtcy.setSfzh(sfzhtxt.getText().toString());
            jtcy.setSrqk(srqktxt.getText().toString());
            jtcy.setStzk(stzktxt.getText().toString());
            jtcy.setQtzk(qtqktxt.getText().toString());

            Intent intent = new Intent(context, jtcyPopActivity.class);
            intent.putExtra("data", jtcy);
            setResult(1,intent);
            finish();
        }
    };

    /**
     * fanhui
     */
    private View.OnClickListener back_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            finish();
        }
    };
}
