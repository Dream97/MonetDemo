package com.rick.monetdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rick.monet.Monet;
import com.rick.monet.utils.ImageEngine;

public class MainActivity extends AppCompatActivity {

    private Button mBtnOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnOpen = findViewById(R.id.btn_open);
        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Monet.from(MainActivity.this)
                        .choose()
                        .setEngine(new GlideEngine())
                        .start(1);
            }
        });
    }
}
