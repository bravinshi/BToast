package com.shi.bravin.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bravin.btoast.BToast;

public class MainActivity extends AppCompatActivity {

    private View target;
    private View target1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        target = findViewById(R.id.target);
        target1 = findViewById(R.id.target1);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .show();
            }
        });

        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.error(v.getContext())
                        .text("这是测试文字")
                        .show();
            }
        });

        findViewById(R.id.btn11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.info(v.getContext())
                        .text(R.string.text_test_content)
                        .show();
            }
        });

        findViewById(R.id.btn12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.normal(v.getContext())
                        .text(R.string.text_test_content)
                        .show();
            }
        });

        findViewById(R.id.btn13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.warning(v.getContext())
                        .text(R.string.text_test_content)
                        .show();
            }
        });
    }
}
