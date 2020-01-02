package com.example.mylibrary.utils.recycadapter;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：Old.Boy 2019/2/14.
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    private SparseArrayCompat<View> mView;

    public BaseRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        mView = new SparseArrayCompat<>();
    }

    public <V extends View> V getView(@IdRes int res){
        View v = mView.get(res);
        if(v==null){
            v=itemView.findViewById(res);
            mView.put(res,v);
        }
        return (V)v;
    }
}
