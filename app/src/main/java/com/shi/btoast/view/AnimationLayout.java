package com.shi.btoast.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class AnimationLayout extends LinearLayout implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator valueAnimator;
    private long animDuration = 2000;
    private View child;
    private int childHeight;
    private int preValue = 0;
    private boolean firstLayout = true;

    public AnimationLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AnimationLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public AnimationLayout(Context context) {
        super(context);
    }

    private void init(Context context){
        valueAnimator = ValueAnimator.ofInt();
        valueAnimator.setDuration(animDuration);
        setBackgroundColor(Color.BLUE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!firstLayout){
            return;
        }
        firstLayout =false;
        childHeight = getMeasuredHeight();
        child = getChildAt(0);
        if (child == null) {
            throw new IllegalStateException("AnimationLayout has no child!");
        }
        child.layout(left,-72,right,0);
        valueAnimator.setIntValues(0,childHeight);
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        Log.d("VY", "value: " + value);
        child.offsetTopAndBottom(value - preValue);
        preValue = value;
    }
}
