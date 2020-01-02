package com.ecareyun.im.framwork.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ecareyun.cloudpos.framwork.RepeatedClickHandler;


public abstract class BaseActivity<T extends BasePresenter> extends FragmentActivity implements MvpView {

    protected T mPresenter;
    protected boolean isHandleKey = false;
    private RepeatedClickHandler repeatedClickHandler;
    protected boolean isResumed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 处理重复点击
        repeatedClickHandler = new RepeatedClickHandler();
        doBeforeInitView();
        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
        initView();
//        excuteStatesBar();
        doAfterInitView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResumed = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 解决4.4设置状态栏颜色之后，布局内容嵌入状态栏位置问题
     */
    private void excuteStatesBar() {
        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows,
            // 而是设置 ContentView 的第一个子 View ，预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(true);
        }
    }

    /**
     * 处理重复点击
     *
     * @param view
     */
    protected void handleRepeatClick(View view) {
        repeatedClickHandler.handleRepeatedClick(view);
    }

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 在initView()调用之前调用，
     * 可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
     */
    protected abstract void doBeforeInitView();

    /**
     * 在此方法中进行setContentView()，ButterKnife.bind(this)，可以对view进行初始化设置
     */
    protected abstract void initView();

    /**
     * 在initView()之后调用
     */
    protected abstract void doAfterInitView();

    /**
     * 在initData()后调用，设置监听
     */
    protected abstract void initListener();

    /**
     * 用于创建Presenter和判断是否使用MVP模式(由子类实现)
     *
     * @return
     */
    protected abstract T createPresenter();

    @Override
    public void showToast(String msg) {
    }

    @Override
    public void showToast(@StringRes int msgId) {
    }



    @Override
    public void showLoadingDialog(@StringRes int msgId) {
        showLoadingDialog(getString(msgId));
    }



    @Override
    public void setIsReceiveScanResult(boolean isReceiveScanResult) {
        isHandleKey = isReceiveScanResult;
    }
}
