package com.willemgeo.szdb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        //选择按钮
        ((Button) this.findViewById(R.id.add_new_btn)).setOnClickListener(camera_btn_Click);

    }

    /**
     * 选择点按钮
     */
    private View.OnClickListener camera_btn_Click = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, EditActivity.class);
            context.startActivity(intent);
        }
    };
}
