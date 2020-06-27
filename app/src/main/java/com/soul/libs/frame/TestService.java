package com.soul.libs.frame;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Tag", "服务被创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Tag", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Tag", "服务被绑定");
        return new Binder();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("Tag", "服务解绑");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        Log.i("Tag", "服务被销毁");
        super.onDestroy();
    }
}
