package com.soul.libs.frame;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.soul.libs.test.ButtonTextFragment;
import com.soul.libs.R;

import java.util.Objects;

import androidx.core.content.ContextCompat;

/**
 * Description: 图片显示测试
 * Author: 祝明
 * CreateDate: 2022/9/14 18:24
 * UpdateUser:
 * UpdateDate: 2022/9/14 18:24
 * UpdateRemark:
 */
public class GlideTestFragment extends ButtonTextFragment {
    @Override
    public String getClassName() {
        return "图片显示测试";
    }

    @Override
    protected void initEvent() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.width = 500;
        layoutParams.height = 1000;
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccent));
        addView(imageView);
    }
}
