package com.shi.btoast;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * created by bravin on 2018/6/19.
 */
public abstract class SimpleRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> data;

    public SimpleRecyclerViewAdapter(){
        data = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindVH(holder, position, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(data.get(position));
    }

    public abstract int getItemType(T item);

    public abstract void onBindVH(RecyclerView.ViewHolder holder, int position, T item);

    // 如果希望局部刷新时 图片不闪烁，重写此方法
    // 使用notifyItemChanged(int position, @Nullable Object payload)局部刷新  payload不为null
    public void onRefreshItem(RecyclerView.ViewHolder viewHolder, T item){
        // do nothing
    }

    public void setData(List<T> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();

    }

    public void addData(List<T> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public T getItem(int position){
        return data.get(position);
    }

        @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onRefreshItem(holder,data.get(position));
        }
    }
}
