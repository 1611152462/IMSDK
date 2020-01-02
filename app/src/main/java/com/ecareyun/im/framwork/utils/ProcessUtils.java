package com.ecareyun.im.framwork.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ProcessUtils {

    /**
     * 判断进程是否存活
     *
     * @param context
     * @param proessName
     * @return
     */
    public static boolean isProcessRunning(Context context, String proessName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : lists) {
            String pn = info.processName;
            if (pn.equals(proessName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断服务是否运行
     *
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> lists = am.getRunningServices(30);

        for (ActivityManager.RunningServiceInfo info : lists) {//判断服务
            String s = info.service.getClassName();
            if (s.equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
