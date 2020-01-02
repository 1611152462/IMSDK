package com.ecareyun.im.framwork.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.ecareyun.im.Global;
import com.ecareyun.im.push.MPush;
import com.ecareyun.im.push.MPushEngine;
import com.example.mylibrary.utils.SharedUtils;
import com.mpush.client.AllotClient;

import org.jsoup.helper.StringUtil;

import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

public class AppUtils {
    /**
     * 获取app包名
     *
     * @return 返回包名
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 切换tab执行方法
     */
    public static void changeTab(boolean isActive, Context context) {
        String token = SharedUtils.getToken(context);
        if (token.contains("zs")) {
            if (!isActive) {
                boolean b = MPush.I.hasStarted();
                if (b) {
                    if (AppUtils.isEmptyString(AllotClient.token)) {
                        AllotClient.token = SharedUtils.getToken(context);
                    }
                    MPush.I.pausePush();
                    MPush.I.startPush();
                } else {
                    MPushEngine.getInstance(context).initPush();
                }
            }
        }
    }

    /**
     * @param context
     * @param packageName
     * @return
     * @Title isPackageExist
     * @Description .判断package是否存在
     * @date 2013年12月31日 上午9:49:59
     */
    public static boolean isPackageExist(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent intent = new Intent().setPackage(packageName);
        @SuppressLint("WrongConstant") List<ResolveInfo> infos = manager.queryIntentActivities(intent,
                PackageManager.GET_INTENT_FILTERS);
        if (infos == null || infos.size() < 1) {
            return false;
        } else {
            return true;
        }
    }

    public static void toRealName(Activity activity, String appPackageName, String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        //前提：知道要跳转应用的包名、类名
        ComponentName componentName = new ComponentName(appPackageName, activityName);
        intent.setComponent(componentName);
        activity.startActivityFromChild(activity, intent, Global.YQB_REQUEST_CODE);
    }


    public static void openApp(Activity activity, String packageName) {
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityFromChild(activity, intent, Global.REQUEST_CODE);
        }
    }

    /**
     * 检测 响应某个Intent的Activity 是否存在
     *
     * @param context
     * @param intent
     * @return
     */
    @SuppressLint("WrongConstant")
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    @SuppressLint("MissingPermission")
    public synchronized static String getid(Context context) {
        String ID = "";
//        LogUtil.LogError("设备标识 ", ID + "-----");

        if (Build.VERSION.SDK_INT > 28) {
            //ANDROID_ID是Android系统第一次启动时产生的一个64bit（16BYTES）数，如果设备被wipe还原后，该ID将被重置（变化）
            ID = GetDeviceId.createRandomUUID(context);
//            ID =  Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//            283a4d383a692405

        } else {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (AppUtils.isEmptyString(TelephonyMgr.getDeviceId())) {
                ID = TelephonyMgr.getDeviceId();
            }
        }
        return ID;
    }

    /**
     * 判断字符串是否null或为空
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {

        if (null != str && !"".equals(str) && !"null".equals(str)) {

            return true;
        }


        return false;
    }

//    /**
//     * @return 当前版本信息
//     */
//    public static String getASVersionName() {
//        int localVersionCode = BuildConfig.VERSION_CODE;
//        String versionName = BuildConfig.VERSION_NAME;
//
//        return versionName;
//    }
}
