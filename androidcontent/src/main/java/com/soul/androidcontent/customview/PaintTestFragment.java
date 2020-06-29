package com.soul.androidcontent.customview;

import android.view.View;

import com.soul.androidcontent.customview.paint.GradientPaintUI;
import com.soul.lib.test.ButtonTextFragment;

/**
 * Description: paint使用示例
 * Author: zhuMing
 * CreateDate: 2020/6/29 17:19
 * ProjectName: libs
 * UpdateUser:
 * UpdateDate: 2020/6/29 17:19
 * UpdateRemark:
 */
public class PaintTestFragment extends ButtonTextFragment implements View.OnClickListener {

    @Override
    public String getClassName() {
        return "paint使用示例";
    }

    @Override
    protected void initEvent() {
        addTextName("paint常用API", this);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        CustomUI customUI = null;
        switch (tag) {
            case "paint常用API":
                customUI = new GradientPaintUI();
                break;
        }

        CustomView customView = new CustomView(getContext());
        customView.setUI(customUI);
        addView(customView);
    }

}
