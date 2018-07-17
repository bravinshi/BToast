package com.shi.btoast;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("这是一段测试文字")
                        .textColor(Color.WHITE)
                        .animationGravity(BToast.ANIMATION_GRAVITY_RIGHT)
                        .animate(true)
                        .show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text("这是一段测试文字")
                        .textColor(Color.WHITE)
                        .target(btn1)
                        .show();
            }
        });

    }
}
