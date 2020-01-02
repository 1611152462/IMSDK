package com.ecareyun.im.framwork.mvp;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;

/**
 * 项目名称：CloudPos
 * 类描述：
 * 创建人：xiezheng
 * 创建时间：2018/12/18 下午3:27
 * 修改人：xiezheng
 * 修改时间：2018/12/18 下午3:27
 * 修改备注：
 */
public class BaseDialogFragment extends DialogFragment {
    /**
     * 判断弹窗是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        manager.executePendingTransactions();
        if (!isAdded() && !isShowing()) {
            try {
                super.show(manager, tag);
            } catch (IllegalStateException e) {
            }
        }
    }

    /**
     * 关闭DialogFragment
     *
     * @param isResume 在Fragment中使用可直接传入isResumed()
     *                 在FragmentActivity中使用可自定义全局变量 boolean isResumed 在onResume()和onPause()中分别传人判断为true和false
     */
    public void dismiss(boolean isResume) {
        if (isResume) {
            dismiss();
        } else {
            dismissAllowingStateLoss();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if (isShowing()) {
            super.dismissAllowingStateLoss();
        }
    }
}
