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

    public CompoundButton getCompoundButton(int viewId){
        return getViewById(viewId);
    }

    public RecyclerView getRecyclerView(int viewId){
        return getViewById(viewId);
    }

    public int getViewType() {
        return viewType;
    }

}
