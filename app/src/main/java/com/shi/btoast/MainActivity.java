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
    private View target;
    private View target1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        target = findViewById(R.id.view);
        target1 = findViewById(R.id.view1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("测试文字")
                        .animate(true)
                        .animationGravity(BToast.ANIMATION_GRAVITY_LEFT)
                        .textColor(Color.WHITE)
                        .show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("测试文字")
                        .textColor(Color.WHITE)
                        .target(target)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_TOP)
                        .relativeGravity(BToast.RELATIVE_GRAVITY_CENTER)
                        .sameLength(true)
                        .show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("常常的文字字字字")
                        .textColor(Color.WHITE)
                        .target(target1)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_LEFT)
                        .relativeGravity(BToast.RELATIVE_GRAVITY_CENTER)
                        .sameLength(true)
                        .show();
            }
        });

    }
}
