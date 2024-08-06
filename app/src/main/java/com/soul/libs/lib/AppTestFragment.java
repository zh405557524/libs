package com.soul.libs.lib;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.soul.libs.test.ButtonTextFragment;
import com.soul.lib.utils.AppUtils;
import com.soul.lib.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @描述：App相关工具类
 * @作者：祝明
 * @项目名:libs
 * @创建时间：2019/10/21 20:13
 */
public class AppTestFragment extends ButtonTextFragment implements View.OnClickListener {

    @Override
    public String getClassName() {

        return "App相关工具类";
    }

    @Override
    protected void initEvent() {
        addTextName("判断App是否安装", this);
        addTextName("安装App", this);
        addTextName("静默安装App", this);
        addTextName("卸载App", this);
        addTextName("静默卸载App", this);
        addTextName("判断App是否有root权限", this);
        addTextName("打开App", this);
        addTextName("关闭App", this);
        addTextName("获取App包名", this);
        addTextName("获取App具体设置", this);


        TextView context;
        context = null;
        TextView context1 = context;
        context = new TextView(getContext());
        View view = (View) null;
        Log.i("Tag", "context1:" + context1 + "   view:" + view);
        try {
            context.append("fsdafdsa");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        switch (tag) {
            case "判断App是否安装":
                break;
            case "打开App":
                Date date = new Date();
                LogUtils.i("时间time为：" + date.toLocaleString());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String format = sdf.format(date);
                LogUtils.i("时间time为：" + format);
                if ("11:22".equals(format)) {
                    AppUtils.startAppByName(mRootView.getContext(), "钉钉");
                }
                break;
            case "关闭app":
                try {
                    ActivityManager m = (ActivityManager) getContext().getSystemService(ACTIVITY_SERVICE);
                    m.killBackgroundProcesses("com.alibaba.android.rimet");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "获取App包名":
                String packageName = AppUtils.getAppPackageName(getContext());
                LogUtils.i("Tag", "packageName:" + packageName);
                break;
            case "获取App具体设置":
                AppUtils.getAppDetailsSettings(getContext());
                break;
            case "安装App":
                AppUtils.installApp(getContext(), "com.soul.libs");//todo 路径
                break;
        }
    }
}
