package com.example.mylibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 作者：Old.Boy 2019/1/15.
 */
public class IntentUtils {

    public static void startActivity(Activity activity, Class<?> clazz) {
        startActivity(activity, clazz, null, Constant.REQUESTCODE,false);
    }

    public static void startActivity(Activity activity, Class<?> clazz,Boolean isExit) {
        startActivity(activity, clazz, null, Constant.REQUESTCODE,isExit);
    }

    public static void startActivity(Activity activity, Class<?> clazz, Bundle bundle) {
        startActivity(activity, clazz, bundle, Constant.REQUESTCODE,false);
    }

    public static void startActivity(Activity activity, Class<?> clazz, Bundle bundle,Boolean isExit) {
        startActivity(activity, clazz, bundle, Constant.REQUESTCODE,isExit);
    }

    public static void startActivity(Activity activity, Class<?> clazz, int requestCode) {
        startActivity(activity, clazz, null, requestCode,false);
    }

    public static void startActivity(Activity activity, Class<?> clazz, Bundle bundle, int requestCode,boolean isExit) {
        Intent intent = new Intent(activity, clazz);

        if (bundle != null) {
            intent.putExtra(Constant.BUNDLE_KEY,bundle);
        }

        if (requestCode == Constant.REQUESTCODE) {
            activity.startActivity(intent);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }

        if(isExit){
            activity.finish();
        }
    }
}
