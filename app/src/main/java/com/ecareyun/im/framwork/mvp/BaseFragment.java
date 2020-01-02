package com.ecareyun.im.framwork.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * user：lqm
 * desc：BaseFragment
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements MvpView {

    protected T mPresenter;
    public BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
        doBeforeInitView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(provideContentViewId(), container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doAfterInitView();
        initListener();

    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    @Override
    public void showToast(String msg) {
        if (mActivity != null) mActivity.showToast(msg);
    }

    @Override
    public void showToast(@StringRes int msgId) {
        if (mActivity != null) mActivity.showToast(msgId);
    }

    @Override
    public void showLoadingDialog(String msg) {
        if (mActivity != null) mActivity.showLoadingDialog(msg);
    }

    @Override
    public void showLoadingDialog(@StringRes int msgId) {
        showLoadingDialog(getString(msgId));
    }

    @Override
    public void dismissLoading() {
        if (mActivity != null) mActivity.dismissLoading();
    }

    @Override
    public void dismissAlertDialog() {
        if (mActivity != null) mActivity.dismissAlertDialog();
    }

    @Override
    public void setIsReceiveScanResult(boolean isReceiveScanResult) {
        mActivity.setIsReceiveScanResult(isReceiveScanResult);
    }

    /**
     * 在initView()调用之前调用，
     */
    protected abstract void doBeforeInitView();

    /**
     * 在此方法中可以对view进行初始化设置
     */
    protected abstract void initView(View rootView);

    /**
     * 在initView()之后调用
     */
    protected abstract void doAfterInitView();

    /**
     * 在initData()后调用，设置监听
     */
    protected abstract void initListener();


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    public T getPresenter() {
        return mPresenter;
    }
    /**
     * Fragment之间传递数据或者Fragment与Activity之间传递值都可以使用该方法
     * 对应的获取Bundule的方法是
     * Bundle bundle=getArguments();
     * @param bundle
     */
    public abstract void setBundle(Bundle bundle);
}
