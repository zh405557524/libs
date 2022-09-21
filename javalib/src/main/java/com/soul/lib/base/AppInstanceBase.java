package com.soul.lib.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.soul.lib.utils.AppUtils;
import com.soul.lib.utils.UIUtils;


public class AppInstanceBase {
    private Application mApp = null;
    private Context mContext = null;

    private HandlerThread mBackgroundThread = null;
    private Handler mBackgroudHandler = null;

    private HandlerThread mHeavyThread = null;
    private Handler mheavyHandler = null;

    private Handler mUiHandler = null;

    public void onCreate(Application app) {
        mApp = app;
        mContext = app.getApplicationContext();
        Global.setContext(mContext);
        initial();
        UIUtils.init(app, mUiHandler, android.os.Process.myTid());
    }

    public Context getContext() {
        return mContext;
    }

    public Application getApp() {
        return mApp;
    }


    private void initial() {
        //初始化工作线程
        mBackgroundThread = new HandlerThread("backgroundThread");
        mBackgroundThread.start();
        mBackgroudHandler = new Handler(mBackgroundThread.getLooper());

        mHeavyThread = new HandlerThread("heavyThread");
        mHeavyThread.start();
        mheavyHandler = new Handler(mHeavyThread.getLooper());

        mUiHandler = new Handler(mContext.getMainLooper());

        Global.setHandler(mUiHandler, mBackgroudHandler, mheavyHandler);
        //start 一个service,保活
    }

    public boolean isMainProcess() {
        return AppUtils.isMainProcess(mContext);
    }

    public String getProcessName() {
        return AppUtils.getProcessName(mContext, android.os.Process.myPid());
    }

}