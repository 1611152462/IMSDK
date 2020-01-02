package com.example.mylibrary.utils.location;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.custombanner.R;
import com.example.mylibrary.utils.popupwindow.CommonPopupWindow;

/**
 * Dialog工具类
 *
 * @author AndSync
 * @date 2017/10/30
 * Copyright © 2014-2017 AndSync All rights reserved.
 */
public class DialogUtil {

    private static TextView cancel;
    private static TextView toSetting;

    public static void showPermissionManagerDialog(final Activity context, final String str) {
        final CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(context)
                .setView(R.layout.popup_permission)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView tipContent = view.findViewById(R.id.tipContent);
                        cancel = view.findViewById(R.id.cancel);
                        toSetting = view.findViewById(R.id.toSetting);

                        tipContent.setText(str);
                    }
                })
                .setBackGroundLevel(0.5f)
                .setOutsideTouchable(false)
                .create();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
            }
        },500);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                context.finish();
            }
        });
        toSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, 200);
            }
        });
    }

    public static void showLocServiceDialog(final Context context) {
        new AlertDialog.Builder(context).setTitle("手机未开启位置服务")
                .setMessage("请在 设置-系统安全-位置信息 (将位置服务打开))")
                .setNegativeButton("取消", null)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            intent.setAction(Settings.ACTION_SETTINGS);
                            try {
                                context.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .show();
    }
}
