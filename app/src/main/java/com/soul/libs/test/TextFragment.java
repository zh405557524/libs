package com.soul.libs.test;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试界面分类
 */
public class TextFragment extends ButtonTextFragment {


    private List<String> classNames = new ArrayList<>();

    private boolean isRefresh = false;

    @Override
    public String getClassName() {
        return "";
    }

    @Override
    protected void initEvent() {
        //添加音频焦点的测试界面
        final Bundle arguments = getArguments();
        if (arguments != null) {
            classNames = arguments.getStringArrayList("classNames");
        }

        if (!isRefresh && classNames != null) {
            mRootView.post(refreshViewTask);
            isRefresh = true;
        }
    }


    public void addNewText(final String name) {
        addTextName(name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.startFragment(name, getActivity());
            }
        });
    }


    final Runnable refreshViewTask = new Runnable() {
        @Override
        public void run() {
            for (String className : classNames) {
                addNewText(className);
            }
        }
    };
}
