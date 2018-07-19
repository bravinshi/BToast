package com.shi.btoast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shi.btoast.view.AnimationLayout;
import com.shi.btoast.view.StyleLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * created by bravin on 2018/6/7.
 */
public class BToast {

    private static Application app;

    private static Handler subThreadHandler;

    private static final ArrayList<ToastDesc> toasts = new ArrayList<>();

    private static final int SHOW_TOAST = 1;

    public static final int LEVEL_CAN_REMOVE = 1;

    public static final int LEVEL_HOLD = 2;

    private static boolean canNotify = true;

    private static final int DURATION_SHORT = 2000;
    private static final int DURATION_LONG = 3500;

    private static final int DEFAULT_ANIMATION_DURATION = 800;

    @ColorInt
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    @ColorInt
    private static int ERROR_COLOR = Color.parseColor("#D50000");
    @ColorInt
    private static int INFO_COLOR = Color.parseColor("#3F51B5");
    @ColorInt
    private static int SUCCESS_COLOR = Color.parseColor("#388E3C");
    @ColorInt
    private static int WARNING_COLOR = Color.parseColor("#FFA900");
    @ColorInt
    private static int NORMAL_COLOR = Color.parseColor("#353A3E");

    public static final int ANIMATION_GRAVITY_LEFT = 10;
    public static final int ANIMATION_GRAVITY_TOP = 20;
    public static final int ANIMATION_GRAVITY_RIGHT = 30;
    public static final int ANIMATION_GRAVITY_BOTTOM = 40;

    public static final int LAYOUT_GRAVITY_LEFT = 1;
    public static final int LAYOUT_GRAVITY_TOP = 2;
    public static final int LAYOUT_GRAVITY_RIGHT = 3;
    public static final int LAYOUT_GRAVITY_BOTTOM = 4;

    public static final int STYLE_FILLET = 100;
    public static final int STYLE_RECTANGLE = 200;

    public static final int RELATIVE_GRAVITY_START = 1;
    public static final int RELATIVE_GRAVITY_END = 2;
    public static final int RELATIVE_GRAVITY_CENTER = 3;

    private static WeakReference<View> currentToastView;

    private static Runnable removeViewRunnable;

    private BToast() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(final Application app) {
        BToast.app = app;
        subThreadHandler = new SubThreadHandler();
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());
        runAgentThread();// 启动代理线程

        removeViewRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentToastView != null && currentToastView.get() != null) {
                    View view = currentToastView.get();
                    WindowManager windowManager =
                            (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
                    windowManager.removeView(view);
                }
            }
        };

    }

    private static void runAgentThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    synchronized (toasts) {
                        if (toasts.size() > 0) {
                            ToastDesc toastDesc = toasts.remove(toasts.size() - 1);
                            Message message = Message.obtain();
                            message.what = SHOW_TOAST;
                            message.obj = toastDesc;
                            subThreadHandler.sendMessage(message);
                            canNotify = false;
                            try {
                                toasts.wait(toastDesc.duration ==
                                        Toast.LENGTH_SHORT ? DURATION_SHORT : DURATION_LONG);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            canNotify = true;
                            try {
                                toasts.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

    private static class SubThreadHandler extends Handler {
        @SuppressLint("WrongConstant")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SHOW_TOAST) {
                ToastDesc toastDesc = (ToastDesc) msg.obj;
                View view = toastDesc.getTarget();
                if (view == null) {
                    if (toastDesc.animate) {
                        showAnimationToast(toastDesc);
                    } else {
                        showStaticToast(toastDesc);
                    }
                } else {
                    View toastLayout = createToastLayout(view, toastDesc);
                    WindowManager.LayoutParams lp
                            = createWindowManagerLayoutParams(view, toastLayout, toastDesc);

                    WindowManager windowManager = (WindowManager) view.getContext()
                            .getSystemService(Context.WINDOW_SERVICE);

                    windowManager.addView(toastLayout, lp);

                    currentToastView = new WeakReference<>(toastLayout);

                    view.postDelayed(removeViewRunnable, 3500);
                }
            }
        }
    }

    private static View createToastLayout(View target, ToastDesc toastDesc) {
        if (toastDesc.animate) {
            if (toastDesc.sameLength){
                AnimationLayout animationLayout = new AnimationLayout(target.getContext());
                StyleLayout toastLayout = (StyleLayout) (LayoutInflater.from(app)
                        .inflate(R.layout.toast_layout_no_animation_style, null));
            }else {
                View toastLayout = LayoutInflater.from(app)
                        .inflate(R.layout.toast_layout_animate, null);
                final AnimationLayout animationLayout = toastLayout.findViewById(R.id.al_layout);
                final StyleLayout styleLayout = toastLayout.findViewById(R.id.toast_content);
                setAnimationStyle(animationLayout, toastDesc);
                applyStyle(styleLayout, toastDesc);
                return toastLayout;
            }
        } else {
            if (toastDesc.sameLength) {
                StyleLayout toastLayout = (StyleLayout) (LayoutInflater.from(app)
                        .inflate(R.layout.toast_layout_no_animation_style, null));
                // 内层的view无需关注style，由外层的ViewGroup代理
                toastLayout.setStyle(StyleLayout.STYLE_RECTANGLE);
                StyleLayout parent = new StyleLayout(target.getContext());
                RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);
                int gravityRule;
                if (toastDesc.layoutGravity == LAYOUT_GRAVITY_TOP
                        || toastDesc.layoutGravity == LAYOUT_GRAVITY_BOTTOM) {
                    if (toastDesc.relativeGravity == RELATIVE_GRAVITY_START) {
                        gravityRule = RelativeLayout.ALIGN_PARENT_START;
                    }else if (toastDesc.relativeGravity == RELATIVE_GRAVITY_END){
                        gravityRule = RelativeLayout.ALIGN_PARENT_END;
                    }else {
                        gravityRule = RelativeLayout.CENTER_HORIZONTAL;
                    }
                }else if (toastDesc.layoutGravity == LAYOUT_GRAVITY_LEFT){
                    if (toastDesc.relativeGravity == RELATIVE_GRAVITY_START) {
                        gravityRule = RelativeLayout.ALIGN_PARENT_TOP;
                    }else if (toastDesc.relativeGravity == RELATIVE_GRAVITY_END){
                        gravityRule = RelativeLayout.ALIGN_PARENT_BOTTOM;
                    }else {
                        gravityRule = RelativeLayout.CENTER_VERTICAL;
                    }
                }else {
                    if (toastDesc.relativeGravity == RELATIVE_GRAVITY_START) {
                        gravityRule = RelativeLayout.ALIGN_PARENT_TOP;
                    }else if (toastDesc.relativeGravity == RELATIVE_GRAVITY_END){
                        gravityRule = RelativeLayout.ALIGN_PARENT_BOTTOM;
                    }else {
                        gravityRule = RelativeLayout.CENTER_VERTICAL;
                    }
                }
                rlp.addRule(gravityRule);
                parent.addView(toastLayout, rlp);
                // ViewGroup代理style
                applyStyle(parent , toastDesc);
                return parent;
            } else {
                // 无动画  not sameLength
                StyleLayout toastLayout = (StyleLayout) (LayoutInflater.from(app)
                        .inflate(R.layout.toast_layout_no_animation_style, null));
                applyStyle(toastLayout, toastDesc);
                // 无需用RelativeLayout包裹
                if (toastDesc.relativeGravity == RELATIVE_GRAVITY_START){
                    return toastLayout;
                }
                RelativeLayout parent = new RelativeLayout(target.getContext());
                RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);
                int gravityRule;
                if (toastDesc.relativeGravity == RELATIVE_GRAVITY_END){
                    gravityRule = RelativeLayout.ALIGN_PARENT_BOTTOM;
                }else {
                    gravityRule = RelativeLayout.CENTER_VERTICAL;
                }
                rlp.addRule(gravityRule);
                parent.addView(toastLayout, rlp);

                return parent;
            }
        }

        return null;
    }

    private static WindowManager.LayoutParams createWindowManagerLayoutParams(
            View target, View content, ToastDesc toastDesc) {

        ViewGroup con = (ViewGroup) content;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.format = PixelFormat.TRANSPARENT;

        final int[] viewLocation = new int[2];
        target.getLocationInWindow(viewLocation);

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.START | Gravity.TOP;
        if (toastDesc.sameLength) {
            switch (toastDesc.layoutGravity) {
                case LAYOUT_GRAVITY_LEFT:
                    int measureSpecL = View.MeasureSpec.makeMeasureSpec(target.getMeasuredHeight() + toastDesc.offsetH, View.MeasureSpec.EXACTLY);
                    // measure is necessary
                    content.measure(ViewGroup.LayoutParams.WRAP_CONTENT,
                            measureSpecL);
                    lp.x = viewLocation[0] - con.getChildAt(0).getMeasuredWidth() + toastDesc.offsetX;
                    lp.y = viewLocation[1] + toastDesc.offsetY;
                    lp.height = target.getMeasuredHeight() + toastDesc.offsetH;
                    break;
                case LAYOUT_GRAVITY_TOP:
                    // measure is necessary
                    int measureSpecT = View.MeasureSpec.makeMeasureSpec(target.getMeasuredWidth()
                            + toastDesc.offsetW, View.MeasureSpec.EXACTLY);
                    content.measure(measureSpecT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.x = viewLocation[0] + toastDesc.offsetX;
                    lp.y = viewLocation[1] - content.getMeasuredHeight() + toastDesc.offsetY;
                    lp.width = target.getMeasuredWidth() + toastDesc.offsetW;
                    break;
                case LAYOUT_GRAVITY_RIGHT:

                    lp.x = viewLocation[0] + target.getMeasuredWidth() + toastDesc.offsetX;
                    lp.y = viewLocation[1] + toastDesc.offsetY;
                    lp.height = target.getMeasuredHeight() + toastDesc.offsetH;
                    break;
                case LAYOUT_GRAVITY_BOTTOM:
                    lp.x = viewLocation[0] + toastDesc.offsetX;
                    lp.y = viewLocation[1] + target.getMeasuredHeight() + toastDesc.offsetY;
                    lp.width = target.getMeasuredWidth() + toastDesc.offsetW;
                    break;
            }
        } else {
            switch (toastDesc.layoutGravity) {
                case LAYOUT_GRAVITY_LEFT:
                    content.measure(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    Log.d("VY", content.getMeasuredHeight() + " / " + content.getMeasuredWidth());
                    lp.x = viewLocation[0] - con.getChildAt(0).getMeasuredWidth() + toastDesc.offsetX;
                    Log.d("VY", "lp.x: " + lp.x);
                    lp.y = viewLocation[1] + toastDesc.offsetY;
                    if (toastDesc.relativeGravity != RELATIVE_GRAVITY_START){
                        lp.height = target.getMeasuredHeight() + toastDesc.offsetH;
                    }
                    break;
                case LAYOUT_GRAVITY_TOP:
                    content.measure(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.x = viewLocation[0] + toastDesc.offsetX;
                    lp.y = viewLocation[1] - content.getMeasuredHeight() + toastDesc.offsetY;
                    if (toastDesc.relativeGravity != RELATIVE_GRAVITY_START){
                        lp.width = target.getMeasuredWidth() + toastDesc.offsetW;
                    }
                    break;
                case LAYOUT_GRAVITY_RIGHT:
                    lp.x = viewLocation[0] + target.getMeasuredWidth() + toastDesc.offsetX;
                    lp.y = viewLocation[1] + toastDesc.offsetY;
                    if (toastDesc.relativeGravity != RELATIVE_GRAVITY_START){
                        lp.height = target.getMeasuredHeight() + toastDesc.offsetH;
                    }
                    break;
                case LAYOUT_GRAVITY_BOTTOM:
                    lp.x = viewLocation[0] + toastDesc.offsetX;
                    lp.y = viewLocation[1] + target.getMeasuredHeight() + toastDesc.offsetY;
                    if (toastDesc.relativeGravity != RELATIVE_GRAVITY_START){
                        lp.width = target.getMeasuredWidth() + toastDesc.offsetW;
                    }
                    break;
            }
        }

        return lp;
    }

    private static void showStaticToast(ToastDesc toastDesc) {
        StyleLayout toastLayout = (StyleLayout) (LayoutInflater.from(app)
                .inflate(R.layout.toast_layout_no_animation_style, null));

        applyStyle(toastLayout, toastDesc);

        showToast(toastLayout);
    }

    private static void showToast(View toastView) {
        Toast toast = new Toast(app);
        toast.setView(toastView);
        toast.show();
    }

    private static void setAnimationStyle(AnimationLayout animationLayout, ToastDesc toastDesc){
        int gravity;
        switch (toastDesc.animationGravity) {
            case ANIMATION_GRAVITY_LEFT:
                gravity = AnimationLayout.GRAVITY_LEFT;
                break;
            case ANIMATION_GRAVITY_TOP:
                gravity = AnimationLayout.GRAVITY_TOP;
                break;
            case ANIMATION_GRAVITY_RIGHT:
                gravity = AnimationLayout.GRAVITY_RIGHT;
                break;
            case ANIMATION_GRAVITY_BOTTOM:
                gravity = AnimationLayout.GRAVITY_BOTTOM;
                break;
            default:
                throw new IllegalArgumentException("animateToast must set animate gravity!");
        }

        animationLayout.setAnimateGravity(gravity);
        animationLayout.setAnimDuration(toastDesc.animationDuration);
    }

    private static void showAnimationToast(ToastDesc toastDesc) {
        View toastLayout = LayoutInflater.from(app)
                .inflate(R.layout.toast_layout_animate, null);

        final AnimationLayout animationLayout = toastLayout.findViewById(R.id.al_layout);
        final StyleLayout styleLayout = toastLayout.findViewById(R.id.toast_content);

        setAnimationStyle(animationLayout, toastDesc);

        applyStyle(styleLayout, toastDesc);

        showToast(toastLayout);
    }

    private static void applyStyle(StyleLayout styleLayout, ToastDesc toastDesc) {
        final ImageView toastIcon = styleLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = styleLayout.findViewById(R.id.toast_text);
        // icon
        if (!toastDesc.showIcon || toastDesc.iconId == 0) {
            toastIcon.setVisibility(View.GONE);
        } else {
            toastIcon.setImageResource(toastDesc.iconId);
        }
        // text
        if (!BToastUtils.isTrimEmpty(toastDesc.text)) {
            toastTextView.setText(toastDesc.text);
        } else if (toastDesc.textRes != 0) {
            toastTextView.setText(toastDesc.textRes);
        } else {
            throw new IllegalArgumentException("BToast must has one of text or textRes!");
        }
        // textColor
        if (toastDesc.textColor != 0) {
            toastTextView.setTextColor(toastDesc.textColor);
        }
        // textSize
        if (toastDesc.textSize != 0) {
            toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, toastDesc.textSize);
        }
        // style tintColor
        styleLayout.setStyle(toastDesc.style == STYLE_RECTANGLE ?
                StyleLayout.STYLE_RECTANGLE : StyleLayout.STYLE_FILLET);
        styleLayout.setTintColor(toastDesc.tintColor);
    }

    /**
     * 移除某一个Context中提交的可移除的toast
     *
     * @param context Context类名
     */
    private static void remove(Context context) {
        remove(context.getClass().getSimpleName(), true);
    }

    /**
     * 移除某一个Context中提交的可移除的toast
     *
     * @param context         Context 类名
     * @param removeHoldToast 是否移除HOLDER等级的toast
     */
    private static void remove(Context context, boolean removeHoldToast) {
        remove(context.getClass().getSimpleName(), removeHoldToast);
    }

    /**
     * 移除某一个Context中提交的可移除的toast
     *
     * @param name Context类名
     */
    private static void remove(final String name, final boolean removeHoldToast) {
        synchronized (toasts) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                toasts.removeIf(new Predicate<ToastDesc>() {
                    @Override
                    public boolean test(ToastDesc toastDesc) {
                        return toastDesc.className.equals(name) &&
                                (removeHoldToast || toastDesc.level == LEVEL_CAN_REMOVE);
                    }
                });
            } else {
                Set<ToastDesc> removeSet = new HashSet<>();
                for (ToastDesc entity : toasts) {
                    if (entity.className.equals(name) &&
                            (removeHoldToast || entity.level == LEVEL_CAN_REMOVE)) {
                        removeSet.add(entity);
                    }
                }
                if (removeSet.size() > 0) {
                    toasts.removeAll(removeSet);
                }
            }
        }
    }

    /**
     * 移除所有可移除的toast
     */
    public static void remove(final boolean removeHoldToast) {
        synchronized (toasts) {
            if (removeHoldToast) {
                toasts.clear();
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                toasts.removeIf(new Predicate<ToastDesc>() {
                    @Override
                    public boolean test(ToastDesc entity) {
                        return entity.level == LEVEL_CAN_REMOVE;
                    }
                });
            } else {
                Set<ToastDesc> removeSet = new HashSet<>();
                for (ToastDesc entity : toasts) {
                    if (entity.level == LEVEL_CAN_REMOVE) {
                        removeSet.add(entity);
                    }
                }
                if (removeSet.size() > 0) {
                    toasts.removeAll(removeSet);
                }
            }
        }
    }

    private static void addToast(ToastDesc toastDesc) {
        synchronized (toasts) {
            remove(toastDesc.className, false);
            toasts.add(toastDesc);
            if (canNotify)
                toasts.notifyAll();
        }
    }

    public static ToastDesc normal(Context context) {
        return new ToastDesc(context.getClass().getSimpleName(), NORMAL_COLOR);
    }

    public static ToastDesc warning(Context context) {
        return new ToastDesc(context.getClass().getSimpleName(),
                WARNING_COLOR, R.drawable.ic_warning_outline_white);
    }

    public static ToastDesc info(Context context) {
        return new ToastDesc(context.getClass().getSimpleName(),
                INFO_COLOR, R.drawable.ic_info_outline_white_48dp);
    }

    public static ToastDesc success(Context context) {
        return new ToastDesc(context.getClass().getSimpleName(),
                SUCCESS_COLOR, R.drawable.ic_check_white_48dp);
    }

    public static ToastDesc error(Context context) {
        return new ToastDesc(context.getClass().getSimpleName(),
                ERROR_COLOR, R.drawable.ic_error_outline_white_48dp);
    }

    public static ToastDesc custom(Context context) {
        return new ToastDesc(context.getClass().getSimpleName());
    }

    static final class ToastDesc {
        private String className;

        private CharSequence text;

        private int textRes = 0;

        private int duration = DURATION_SHORT;

        private int level = LEVEL_CAN_REMOVE;

        @ColorInt
        private int tintColor = 0;

        @DrawableRes
        private int iconId = 0;

        private boolean showIcon = true;

        private boolean animate = false;

        private int animationGravity = BToast.ANIMATION_GRAVITY_TOP;
        @ColorInt
        private int textColor = DEFAULT_TEXT_COLOR;

        private WeakReference<View> target = null;

        private int textSize = 0;

        private int style = STYLE_FILLET;

        private int layoutGravity = LAYOUT_GRAVITY_BOTTOM;

        private int relativeGravity = RELATIVE_GRAVITY_CENTER;

        private int offsetX = 0;

        private int offsetY = 0;

        private int offsetW = 0;

        private int offsetH = 0;

        private boolean sameLength = false;

        private long animationDuration = DEFAULT_ANIMATION_DURATION;

        ToastDesc(String className) {
            this.className = className;
        }

        ToastDesc(String className, int tintColor) {
            this.className = className;
            this.tintColor = tintColor;
        }

        ToastDesc(String className, int tintColor, int iconId) {
            this.className = className;
            this.tintColor = tintColor;
            this.iconId = iconId;
        }

        public ToastDesc duration(int duration) {
            this.duration = duration;
            return this;
        }

        public ToastDesc text(int textRes) {
            this.textRes = textRes;
            return this;
        }

        public ToastDesc text(CharSequence text) {
            this.text = text;
            return this;
        }

        public ToastDesc level(int level) {
            this.level = level;
            return this;
        }

        public ToastDesc tintColor(int tintColor) {
            this.tintColor = tintColor;
            return this;
        }

        public ToastDesc iconId(int iconId) {
            this.iconId = iconId;
            return this;
        }

        public ToastDesc showIcon(boolean showIcon) {
            this.showIcon = showIcon;
            return this;
        }

        public ToastDesc sameLength(boolean sameLength) {
            this.sameLength = sameLength;
            return this;
        }

        public ToastDesc animate(boolean animate) {
            this.animate = animate;
            return this;
        }

        public ToastDesc textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public ToastDesc textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public ToastDesc layoutGravity(int layoutGravity) {
            this.layoutGravity = layoutGravity;
            return this;
        }

        public ToastDesc relativeGravity(int relativeGravity) {
            this.relativeGravity = relativeGravity;
            return this;
        }


        public ToastDesc offsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public ToastDesc offsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public ToastDesc style(int style) {
            this.style = style;
            return this;
        }

        public ToastDesc animationDuration(int animationDuration) {
            this.animationDuration = animationDuration;
            return this;
        }

        public ToastDesc animationGravity(int animationGravity) {
            this.animationGravity = animationGravity;
            return this;
        }

        public ToastDesc target(View view) {
            this.target = new WeakReference<>(view);
            return this;
        }

        public void show() {
            BToast.addToast(this);
        }

        public View getTarget() {
            if (target == null) {
                return null;
            }
            return target.get();
        }
    }

    static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            remove(activity);
        }
    }
}
