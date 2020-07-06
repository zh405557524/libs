package com.soul.libs.lib;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.soul.lib.test.ButtonTextFragment;

/**
 * @描述：TODO
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
        addTextName("获取App包名", this);
        addTextName("获取App具体设置", this);


        TextView context ;
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
        }
    }
}
