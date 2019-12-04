package com.soul.libs;

import android.app.Application;

import com.soul.lib.base.AppInstanceBase;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019/10/23 10:31
 * UpdateUser:
 * UpdateDate: 2019/10/23 10:31
 * UpdateRemark:
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInstanceBase appInstanceBase = new AppInstanceBase();
        appInstanceBase.onCreate(this);
    }
}
