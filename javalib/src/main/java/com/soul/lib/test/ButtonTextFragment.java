package com.soul.lib.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soul.lib.R;
import com.soul.lib.utils.ScreenUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class ButtonTextFragment extends Fragment implements IButtonText, IText, IView {

    private static final String TAG = ButtonTextFragment.class.getSimpleName();
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

    private static final String VIEW_TAG = "view_tag";

    @Override
    public void addView(View view) {
        View childAt = mLlRootView.getChildAt(mLlRootView.getChildCount() - 1);
        if (childAt != null && VIEW_TAG.equals(childAt.getTag())) {
            mLlRootView.removeView(childAt);
        }

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        }
//        view.setTag(VIEW_TAG);
        layoutParams.height = layoutParams.height <= 0 ? ScreenUtils.getScreenHeight() : layoutParams.height;
        layoutParams.width = layoutParams.width <= 0 ? ScreenUtils.getScreenWidth() : layoutParams.width;
        Log.i(TAG, "height:" + layoutParams.height + "    width:" + layoutParams.width);
        mLlRootView.addView(view, layoutParams);
    }
}
