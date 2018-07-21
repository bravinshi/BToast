package com.shi.btoast;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    private View target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        target = findViewById(R.id.view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .layoutGravity(BToast.LAYOUT_GRAVITY_LEFT)
                        .animate(true)
                        .sameLength(true)
                        .target(target)
                        .text("测试文字")
                        .show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .layoutGravity(BToast.LAYOUT_GRAVITY_RIGHT)
                        .relativeGravity(BToast.RELATIVE_GRAVITY_START)
                        .animate(true)
                        .sameLength(true)
                        .target(target)
                        .text("测试文字")
                        .show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("请输入名称")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_RIGHT)
                        .target(target)
                        .show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("字")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_TOP)
                        .target(target)
                        .show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("测试文字")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_LEFT)
                        .target(target)
                        .show();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("测试文字")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM)
                        .target(target)
                        .show();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("请输入名称")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_RIGHT)
                        .target(target)
                        .sameLength(true)
                        .show();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("请输入名称")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_TOP)
                        .target(target)
                        .sameLength(true)
                        .show();
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("测试文字")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_LEFT)
                        .target(target)
                        .sameLength(true)
                        .show();
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("测试文字")
                        .layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM)
                        .target(target)
                        .sameLength(true)
                        .show();
            }
        });

    }
}
