package com.ecareyun.im.framwork.mvp;


import com.lzy.okgo.OkGo;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @user lqm
 * @desc BasePresenter
 */

public abstract class BasePresenter<V extends MvpView> {

    protected Reference<V> mViewRef;


    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        OkGo.getInstance().cancelTag(this);//取消当前P层中进行的网络请求
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }


}
