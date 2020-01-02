package com.example.mylibrary.utils.recycadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Old.Boy 2019/2/14.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    public Activity context;
    public int layoutRes;
    private BaseRecyclerHolder baseRecyclerHolder;
    private int position;
    public List<T> items;

    public BaseRecyclerAdapter(Activity context, int layoutRes) {
        this(context, layoutRes, null);
    }

    public BaseRecyclerAdapter(Activity context, int layoutRes, List<T> items) {
        this.context = context;
        if (layoutRes != 0) this.layoutRes = layoutRes;
        this.items = items == null ? new ArrayList<T>() : items;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new BaseRecyclerHolder(LayoutInflater.from(context).inflate(layoutRes, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder baseRecyclerHolder, int position) {
        this.baseRecyclerHolder = baseRecyclerHolder;
        this.position = position;
        convert(baseRecyclerHolder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void setData(List<T> items) {
        this.items = items == null ? new ArrayList<T>() : items;
        notifyDataSetChanged();
    }

    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        items.add(position, data);
        notifyItemRangeInserted(position, 1);
        compatibilityDataSizeChanged(1);
    }

    public void addData(@NonNull T data) {
        items.add(data);
        notifyItemRangeInserted(items.size() - 1, 1);
    }

    public void addData(@NonNull List<T> datas) {
        items.addAll(items.size() , datas);
        notifyItemRangeInserted(items.size(), datas.size());
        notifyDataSetChanged();
    }

    public void remove(int position) {
        int internalPosition = position;
        items.remove(position);
        notifyItemRemoved(internalPosition);
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = items == null ? 0 : items.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    protected void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    //TODO RecyclerAdapter需要重写的方法
    protected abstract void convert(BaseRecyclerHolder baseRecyclerHolder, int position);
}
