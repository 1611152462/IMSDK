package com.example.mylibrary.utils.stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * 作者：Old.Boy 2019/1/9.
 */
public class AppStackManager {

    public static Stack<Activity> activityStack = new Stack<>();
    /**
     * 将activity添加到栈顶
     *
     * @param activity
     */
    public static void pushActivityToStack(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 将activity添加到指定位置(默认栈顶)
     *
     * @param position
     * @param activity
     */
    public static void addActivityToStack(int position, Activity activity) {
        activityStack.add(position, activity);
    }

    /**
     * 获取栈顶activity
     *
     * @return
     */
    public static Activity getStackTopActivity() {
        return activityStack.peek();
    }

    /**
     * 获取指定activity在在此栈从1开始的位置
     *
     * @param activity
     * @return
     */
    public static int getDesignateLocation(Activity activity) {
        return activityStack.search(activity);
    }

    /**
     * 移除此栈顶activity
     */
    public static void finishCurrentActivity() {
        Activity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 移除指定activity
     *
     * @param activity
     */
    public static void finishAppointActivity(Activity activity) {
        if (activity != null) {
            if (activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有的activity
     */
    private static void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 应用退出
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public static void AppExit(Context context) {
        finishAllActivity();
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        manager.killBackgroundProcesses(context.getPackageName());
        System.exit(0);
    }

}
