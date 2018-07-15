package com.shi.btoast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    private static final ArrayList<ToastEntity> toasts = new ArrayList<>();
    private static final int SHOW_TOAST = 1;
    public static final int LEVEL_CAN_REMOVE = 1;
    public static final int LEVEL_HOLD = 2;
    private static boolean canNotify = true;
    private static final int DURATION_SHORT = 2000;
    private static final int DURATION_LONG = 3500;

    private BToast() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Application app) {
        BToast.app = app;
        subThreadHandler = new SubThreadHandler();
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleImpl());
        runAgentThread();// 启动代理线程
    }

    private static void runAgentThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    synchronized (toasts) {
                        if (toasts.size() > 0) {
                            ToastEntity toastEntity = toasts.remove(toasts.size() - 1);
                            Message message = Message.obtain();
                            message.what = SHOW_TOAST;
                            message.obj = toastEntity;
                            subThreadHandler.sendMessage(message);
                            canNotify = false;
                            try {
                                toasts.wait(toastEntity.duration ==
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
                ToastEntity toastEntity = (ToastEntity) msg.obj;
                View toastLayout = ((LayoutInflater)app.getSystemService("layout_inflater"))
                        .inflate(R.layout.toast_layout, (ViewGroup)null);
                if (toastEntity.text != null) {
                    Toast toast = Toast.makeText(app, toastEntity.text, toastEntity.duration);
                    toast.setView(toastLayout);
                    toast.show();
                } else {
                    Toast.makeText(app, toastEntity.textRes, toastEntity.duration).show();
                }
            }
        }
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
                toasts.removeIf(new Predicate<ToastEntity>() {
                    @Override
                    public boolean test(ToastEntity entity) {
                        return entity.className.equals(name) &&
                                (removeHoldToast || entity.level == LEVEL_CAN_REMOVE);
                    }
                });
            } else {
                Set<ToastEntity> removeSet = new HashSet<>();
                for (ToastEntity entity : toasts) {
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
                toasts.removeIf(new Predicate<ToastEntity>() {
                    @Override
                    public boolean test(ToastEntity entity) {
                        return entity.level == LEVEL_CAN_REMOVE;
                    }
                });
            } else {
                Set<ToastEntity> removeSet = new HashSet<>();
                for (ToastEntity entity : toasts) {
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

    public static void toast(Context context, CharSequence text) {
        toast(context, text, Toast.LENGTH_SHORT, LEVEL_CAN_REMOVE);
    }

    public static void toast(Context context, int textRes) {
        toast(context, textRes, Toast.LENGTH_SHORT, LEVEL_CAN_REMOVE);
    }

    public static void toast(Context context, CharSequence text, int duration) {
        toast(context, text, duration, LEVEL_CAN_REMOVE);
    }

    public static void toast(Context context, int textRes, int duration) {
        toast(context, textRes, duration, LEVEL_CAN_REMOVE);
    }

    public static void toast(Context context, CharSequence text, int duration, int level) {
        ToastEntity toastEntity =
                new ToastEntity(context.getClass().getSimpleName(), text, duration, level);
        addToast(toastEntity);
    }

    public static void toast(Context context, int textRes, int duration, int level) {
        ToastEntity toastEntity =
                new ToastEntity(context.getClass().getSimpleName(), textRes, duration, level);
        addToast(toastEntity);
    }

    private static void addToast(ToastEntity entity) {
        synchronized (toasts) {
            remove(entity.className, false);
            toasts.add(entity);
            if (canNotify)
                toasts.notifyAll();
        }

    }

    static final class ToastEntity {
        String className;
        CharSequence text;
        int textRes = -1;
        int duration = -1;
        int level = -1;

        ToastEntity(String className, int textRes, int duration, int level) {
            this.className = className;
            this.textRes = textRes;
            this.duration = duration;
            this.level = level;
        }

        ToastEntity(String className, CharSequence text, int duration, int level) {
            this.className = className;
            this.text = text;
            this.duration = duration;
            this.level = level;
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
