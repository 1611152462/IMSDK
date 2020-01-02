package com.ecareyun.im.framwork.mvp;

import android.support.annotation.StringRes;
/**
 * 项目名称：CloudPos
 * 类描述：
 * 创建人：xiezheng
 * 创建时间：2018/11/16 上午11:47
 * 修改人：xiezheng
 * 修改时间：2018/11/16 上午11:47
 * 修改备注：
 */
public interface MvpView {

    /**
     * 吐司通知
     *
     * @param msg 通知内容
     */
    void showToast(String msg);

    /**
     * 吐司通知
     *
     * @param msgId 通知内容的资源id
     */
    void showToast(@StringRes int msgId);

    /**
     * 显示加载中对话框
     */
    void showLoadingDialog(String msg);

    /**
     * 显示加载中对话框
     */
    void showLoadingDialog(@StringRes int msgId);

    /**
     * 取消加载中对话框
     */
    void dismissLoading();

    /**
     * 取消提醒对话框
     */
    void dismissAlertDialog();

    /**
     * 设置是否接收扫码结果
     *
     * @param isReceiveScanResult
     */
    void setIsReceiveScanResult(boolean isReceiveScanResult);
}
