package com.willemgeo.szdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class syrPopActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syr_pop);

        context = this;
        //选择按钮
        ((Button) this.findViewById(R.id.save_btn)).setOnClickListener(save_btn_Click);
        ((Button) this.findViewById(R.id.back_btn)).setOnClickListener(back_btn_Click);

    }

    /**
     * 选择点按钮
     */
    private View.OnClickListener save_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
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
