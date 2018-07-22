package com.shi.bravin.app;

import android.os.Looper;
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

        findViewById(R.id.btn21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .radius(0)
                        .show();
            }
        });

        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.error(v.getContext())
                        .text("this is text")
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

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .animate(true)
                        .show();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_RIGHT)
                        .target(target1)
                        .show();
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_TOP)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_LEFT)
                        .target(target1)
                        .show();
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_RIGHT)
                        .sameLength(true)
                        .target(target1)
                        .show();
            }
        });

        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_TOP)
                        .sameLength(true)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_LEFT)
                        .sameLength(true)
                        .target(target1)
                        .show();
            }
        });

        findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM)
                        .sameLength(true)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .animationGravity(BToast.ANIMATION_GRAVITY_LEFT)
                        .animate(true)
                        .show();
            }
        });

        findViewById(R.id.btn15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .animationGravity(BToast.ANIMATION_GRAVITY_RIGHT)
                        .animate(true)
                        .show();
            }
        });

        findViewById(R.id.btn16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .animationGravity(BToast.ANIMATION_GRAVITY_BOTTOM)
                        .animate(true)
                        .show();
            }
        });

        findViewById(R.id.btn17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .relativeGravity(BToast.RELATIVE_GRAVITY_START)
                        .sameLength(true)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .relativeGravity(BToast.RELATIVE_GRAVITY_CENTER)
                        .sameLength(true)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .relativeGravity(BToast.RELATIVE_GRAVITY_END)
                        .sameLength(true)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .relativeGravity(BToast.RELATIVE_GRAVITY_END)
                        .sameLength(true)
                        .animate(true)
                        .target(target)
                        .show();
            }
        });

        findViewById(R.id.btn22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.success(v.getContext())
                        .text(R.string.text_test_content)
                        .show();
            }
        });


        findViewById(R.id.btn23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BToast.warning(v.getContext())
                        .text(R.string.text_test_content)
                        .tag(1)
                        .show();
            }
        });
    }
}
