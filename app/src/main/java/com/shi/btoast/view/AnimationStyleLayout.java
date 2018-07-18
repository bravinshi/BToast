package com.shi.btoast.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * created by bravin on 2018/7/18.
 */
public class AnimationStyleLayout extends RelativeLayout
        implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator valueAnimator;

    private long animDuration = 800;

    private View child;

    private int preValue = 0;

    private boolean firstLayout = true;

    private int animateGravity = 0;

    public static final int GRAVITY_LEFT = 1;
    public static final int GRAVITY_TOP = 2;
    public static final int GRAVITY_RIGHT = 3;
    public static final int GRAVITY_BOTTOM = 4;

    private int radius;
    private int style = STYLE_FILLET;

    public static final int STYLE_FILLET = 1;
    public static final int STYLE_RECTANGLE = 2;

    private static float[] radiusArr = new float[8];
    private int tintColor;

    public AnimationStyleLayout(Context context) {
        super(context);
    }

    public AnimationStyleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationStyleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
