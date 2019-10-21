package com.soul.lib.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;

import java.util.List;

/**
 * description:
 * author: FF
 * time: 2018/12/11 19:38
 */
public class UIUtils {

    private static Application sContext;
    private static Handler sHandler;
    private static int sMainThreadId;


    public static void init(Application context, Handler handler, int mainThreadId) {
        sContext = context;
        sHandler = handler;
        sMainThreadId = mainThreadId;
    }

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * 得到resouce对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的一个字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到string.xml中的一个字符串数组
     */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到color.xml中的颜色值
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * 得到应用程序的包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 得到主线程id
     */
    public static int getMainThreadId() {
        return sMainThreadId;
    }

    /**
     * 得到一个主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return sHandler;
    }

    /**
     * 安全的执行一个task
     */
    public static void postTaskSafely(Runnable task) {
        if (android.os.Process.myTid() == getMainThreadId()) {
            // 如果当前线程是主线程
            task.run();
        } else {
            // 如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }

    /**
     * 安全的延迟执行一个task
     */
    public static void postDelayedTaskSafely(Runnable task, long delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**
     * 包名判断是否为主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        return context.getPackageName().equals(getProcessName(context));
    }

    /**
     * 获取进程名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                return proInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取versionCode
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = getContext().getPackageManager().
                    getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取versionName
     */
    public static String getVersionName() {


        String versionName = "";
        try {
            versionName = getContext().getPackageManager().
                    getPackageInfo(getContext().getPackageName(), 0).versionName;
            if (versionName == null) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getDeviceSN() {

        String serialNumber = android.os.Build.SERIAL;
        if (serialNumber == null) {
            return "";
        }
        return serialNumber;
    }
}
