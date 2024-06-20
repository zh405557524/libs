package com.soul.lib.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class CommonAdapter<T> extends RecyclerView.Adapter {

    protected final List<T> data = new LinkedList<>();

    private Context context;

    private RecyclerView parent;

    public CommonAdapter() {

    }

    public CommonAdapter(List<T> list) {
        this.data.addAll(list);
    }

    @Deprecated
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        this.context = viewGroup.getContext();
        this.parent = (RecyclerView) viewGroup;

        //自定义布局
        View view = null;
        Object o = getItemView(viewType);
        if (o instanceof View){
            view = (View) o;
        } else  if (o instanceof Integer){
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate((Integer) o, viewGroup, false);
        }

        //创建ViewHolder
        final CommonViewHolder holder = new CommonViewHolder(view, viewType);

        //
        onItemViewCreated((CommonViewHolder) holder);

        return holder;
    }

    @Deprecated
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int reallyPosition = getItemList().size() == 0?0:position%getItemList().size();
        if (!getItemList().isEmpty()){
            onItemViewConvert((CommonViewHolder) holder, getItemList().get(reallyPosition), reallyPosition);
        }
    }

    public Context getContext() {
        return context;
    }

    public RecyclerView getParent() {
        return parent;
    }

    @Deprecated
    @Override
    public int getItemCount() {
        return getItemList().isEmpty() ?0:isInfinity()?Integer.MAX_VALUE:getItemList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    //注意以下
    //数据 List 千万不要直接对外引用，防止出现数据篡改但并未及时更新的BUG

    public List<T> getItemList(){
        return new ArrayList<>(data);
    }

    public void setItemList(List<T> list){
        this.data.clear();
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    public void setDataList(List<T> list){
        this.data.clear();
        this.data.addAll(list);
    }

    public void addItemList(List<T> list){
        if (this.data.isEmpty()){
            this.data.addAll(list);
            notifyDataSetChanged();
        } else {
            int start = this.data.size();
            int length = list.size();
            this.data.addAll(list);
            notifyItemRangeInserted(start,length);
        }
    }

    public void addDataList(List<T> list){
        this.data.addAll(list);
    }

    public void addItem(T t){
        if (this.data.isEmpty()){
            data.add(t);
            notifyDataSetChanged();
        } else {
            int index = this.data.size();
            this.data.add(t);
            notifyItemInserted(index);
        }
    }

    public void addData(T t){
        data.add(t);
    }

    public void addItem(int position,T t){
        if (this.data.isEmpty()){
            data.add(t);
            notifyDataSetChanged();
        } else {
            this.data.add(position,t);
            notifyItemInserted(position);
            notifyItemRangeChanged(position,data.size());
        }
    }

    public void addData(int position,T t){
        this.data.add(position,t);
    }

    public boolean updateItem(int position,T t){
        if (position < 0 || position >= data.size()){
            return false;
        }
        data.remove(position);
        data.add(position,t);
        notifyItemChanged(position);
        return true;
    }

    public boolean updateData(int position,T t){
        if (position < 0 || position >= data.size()){
            return false;
        }
        data.remove(position);
        data.add(position,t);
        return true;
    }

    public boolean removeItem(int position){
        if (position < 0 || position >= data.size()){
            return false;
        }
        notifyItemRemoved(position);
        data.remove(position);
        notifyItemRangeChanged(position,data.size());
        return true;
    }

    public boolean removeData(int position){
        if (position < 0 || position >= data.size()){
            return false;
        }
        data.remove(position);
        return true;
    }

    public boolean moveItem(int start,int end){
        if (start < 0 || start >= data.size() || end < 0 || end >= data.size()){
            return false;
        }
        T item = data.get(start);
        data.remove(item);
        data.add(end,item);
        notifyItemMoved(start,end);
        notifyItemRangeChanged(end,data.size());
        return true;
    }

    public boolean moveData(int start,int end){
        if (start < 0 || start >= data.size() || end < 0 || end >= data.size()){
            return false;
        }
        T item = data.get(start);
        data.remove(item);
        data.add(end,item);
        return true;
    }

    protected boolean isInfinity(){
        return false;
    }

    protected void onItemViewCreated(CommonViewHolder holder){

    }

    //可以通过重载这个方法根据viewType返回不同的布局类型
    protected abstract Object getItemView(int viewType);

    protected abstract void onItemViewConvert(CommonViewHolder holder, T t, int position);

}
