package com.soul.lib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Description: 屏幕适配方案
 * Author: 祝明
 * CreateDate: 2024/5/28 13:37
 * UpdateUser:
 * UpdateDate: 2024/5/28 13:37
 * UpdateRemark:
 */
public class EDensityUtils {

    //    private static final float  WIDTH = 480;//参考设备的宽，单位是dp DPI:640
    //    private static final float  WIDTH = 640;//参考设备的宽，单位是dp DPI:480
    //    private static final float  WIDTH = 960;//参考设备的宽，单位是dp DPI:320
    private static final float WIDTH = 375;//参考设备的宽，单位是dp DPI:160时
    private static float appDensity;//表示屏幕密度
    private static float appScaleDensity; //字体缩放比例，默认appDensity


    private EDensityUtils() {
        throw new UnsupportedOperationException("you can't instantiate EDensityUtils...");
    }


    public static void setDensity(final Application application, Activity activity) {
        //获取当前app的屏幕显示信息
        try {
            DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
            if (appDensity == 0) {
                //初始化赋值操作
                appDensity = displayMetrics.density;
                appScaleDensity = displayMetrics.scaledDensity;

                //添加字体变化监听回调
                application.registerComponentCallbacks(new ComponentCallbacks() {
                    @Override
                    public void onConfigurationChanged(Configuration newConfig) {
                        //字体发生更改，重新对scaleDensity进行赋值
                        if (newConfig != null && newConfig.fontScale > 0) {
                            appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                        }
                    }

                    @Override
                    public void onLowMemory() {

                    }
                });
            }

            //计算目标值density, scaleDensity, densityDpi
            float targetDensity = displayMetrics.widthPixels / WIDTH; // 1920 / 1920 = 1.0
            float targetScaleDensity = targetDensity * (appScaleDensity / appDensity);
            int targetDensityDpi = (int) (targetDensity * 160);

            //替换Activity的density, scaleDensity, densityDpi
            DisplayMetrics dm = activity.getResources().getDisplayMetrics();
            dm.density = targetDensity;
            dm.scaledDensity = targetScaleDensity;
            dm.densityDpi = targetDensityDpi;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
