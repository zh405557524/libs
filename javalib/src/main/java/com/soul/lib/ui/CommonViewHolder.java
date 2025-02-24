package com.soul.lib.ui;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private int viewType;
    private SparseArray<View> array = new SparseArray<View>();

    public CommonViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;
    }

    private <T extends View> T getViewById(int viewId) {
        View view = array.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            array.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId, String text) {
        TextView textView = getViewById(viewId);
        textView.setText(text);
    }

    public void setTextColor(int viewId, int color) {
        TextView textView = getViewById(viewId);
        textView.setTextColor(color);
    }

    public void setImageResource(int viewId, int resId) {
        ImageView imageView = getViewById(viewId);
        imageView.setImageResource(resId);
    }

    public <T extends View> T getView(int viewId) {
        return getViewById(viewId);
    }

    public ImageView getImageView(int viewId) {
        return getViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return getViewById(viewId);
    }

    public ViewGroup getViewGroup(int viewId) {
        return getViewById(viewId);
    }

    public CompoundButton getCompoundButton(int viewId) {
        return getViewById(viewId);
    }

    public RecyclerView getRecyclerView(int viewId) {
        return getViewById(viewId);
    }

    public int getViewType() {
        return viewType;
    }

    // 设置视图的点击事件
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getViewById(viewId);
        view.setOnClickListener(listener);
    }

    // 设置视图的长按事件
    public void setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getViewById(viewId);
        view.setOnLongClickListener(listener);
    }

    // 设置视图的可见性
    public void setVisibility(int viewId, int visibility) {
        View view = getViewById(viewId);
        view.setVisibility(visibility);
    }

    // 设置视图的背景颜色
    public void setBackgroundColor(int viewId, int color) {
        View view = getViewById(viewId);
        view.setBackgroundColor(color);
    }

}
