package com.soul.lib;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;

import com.soul.lib.module.log.LogManger;
import com.soul.lib.utils.LogUtil;
import com.soul.lib.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * description:
 * author: FF
 * time: 2018/12/11 19:38
 */
public class Global {
    public static final String INNER_DATA_DIR = File.separator + "data" + File.separator;

    private static Application sContext;
    private static Handler sHandler;

    private static Handler sBgHandler;

    private static int sMainThreadId;
    private static Handler sHeavilyHandler;

    /**
     * 初始化
     *
     * @param isDebug 是否是调试模式, true:打印日志,保持日志, false:不打印日志,不保存日志
     */
    public static void init(Application context, boolean isDebug) {
        Handler handler = new Handler();
        HandlerThread backgroundTask = new HandlerThread("background_task");
        backgroundTask.start();
        init(context, handler, new Handler(backgroundTask.getLooper()), 0);

        LogUtil.sIsDebug = isDebug;
        LogManger.getInstance().setConfig(isDebug);
        LogManger.getInstance().init();
        CrashHandler.getInstance().init(context);
        Utils.init(context);
    }

    public static void init(Application context, Handler handler, Handler bgHandler, int mainThreadId) {
        sContext = context;
        sHandler = handler;
        sMainThreadId = mainThreadId;
        sBgHandler = bgHandler;
        HandlerThread heavilyThread = new HandlerThread("task_heavily");
        heavilyThread.start();
        sHeavilyHandler = new Handler(heavilyThread.getLooper());
        Utils.init(context);
    }

    public static void setApplication(Application application) {
        sContext = application;
    }


    /**
     * 得到上下文
     */
    public static Application getContext() {
        if (sContext == null) {
            throw new RuntimeException("The Global must be initialized first!");
        }
        return sContext;
    }

    /**
     * 得到resouce对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    public static float getDimension(int resId) {
        return getResources().getDimension(resId);
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


    public static String getAppName() {
        try {
            PackageManager packageManager = getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return getContext().getResources().getString(labelRes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取非系统应用的应用名集合
     *
     * @return
     */
    private static List<PackageInfo> getAppInfoList() {
        PackageManager packageManager = sContext.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        ArrayList<PackageInfo> appNameList = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {//非系统应用
                appNameList.add(packageInfo);
            }
        }

        return appNameList;
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
        if (sHandler == null) {
            throw new RuntimeException("The Global must init first");
        }
        return sHandler;
    }

    public static Handler getBackgroundHandler() {
        if (sBgHandler == null) {
            throw new RuntimeException("The Global must init first");
        }
        return sBgHandler;
    }

    public static Handler getHeavilyHandler() {
        if (sHeavilyHandler == null) {
            throw new RuntimeException("The Global must init first");
        }
        return sHeavilyHandler;
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


    @SuppressLint("SdCardPath")
    public static String getExternalDataDir() {

        String EXTERNAL_DATA_DIR = File.separator + getContext().getPackageName() + File.separator;
        //        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        //
        //        String model = Build.MODEL;
        //
        //        do {
        //            if ("MIX 3".equals(model)) {//小米 特殊处理
        //                break;
        //            }
        //            if (externalStorageDirectory == null) {
        //                break;
        //            }
        //            if (TextUtils.isEmpty(externalStorageDirectory.getAbsolutePath())) {
        //                break;
        //            }
        //            return externalStorageDirectory.getAbsolutePath() + EXTERNAL_DATA_DIR;
        //        } while (false);
        return "/sdcard/" + EXTERNAL_DATA_DIR;
    }


    public static String getInnerDataDir() {

        return getContext().getApplicationInfo().dataDir + INNER_DATA_DIR;
    }

    /**
     * 获取sd缓存目录
     *
     * @return
     */
    public static String getExternalCacheDir() {
        Context context = getContext();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir != null) {
                return externalCacheDir.getPath() + File.separator;
            }
        }
        return context.getCacheDir().getPath() + File.separator;
    }

    /**
     * 创建随机唯一数
     */
    public static String createUid() {
        return UUID.randomUUID().toString();
    }

    public static void exitAPP() {
        System.exit(0);
    }
}
