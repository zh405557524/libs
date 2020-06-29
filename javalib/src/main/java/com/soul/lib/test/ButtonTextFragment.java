package com.soul.lib.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soul.lib.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class ButtonTextFragment extends Fragment implements IButtonText, IText {

    protected View mRootView;
    protected LinearLayout mLlRootView;

    public String classChineName = getClassName();

    public abstract String getClassName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_text_button, container, false);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initEvent();
    }


    protected void initView() {
        mLlRootView = mRootView.findViewById(R.id.ll_rootView);
    }

    protected void initData() {

    }

    protected abstract void initEvent();


    @Override
    public Button addTextName(String clickName, View.OnClickListener onClickListener) {
        final Button button = new Button(getActivity());
        button.setText(clickName);
        button.setTextSize(16);
        button.setOnClickListener(onClickListener);
        button.setTag(clickName);
        mLlRootView.addView(button);
        return button;
    }

    @Override
    public TextView addText(LinearLayout.LayoutParams param) {
        final TextView textView = new TextView(getActivity());
        textView.setTextSize(16);
        mLlRootView.addView(textView, param);
        return textView;
    }

}
