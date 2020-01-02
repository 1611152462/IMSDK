package com.ecareyun.im.push;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.ecareyun.im.App;
import com.ecareyun.im.AppConst;
import com.ecareyun.im.Global;
import com.ecareyun.im.framwork.utils.SharedUtils;
import com.mpush.BuildConfig;
import com.mpush.api.Client;
import com.mpush.api.ClientListener;
import com.mpush.client.ClientConfig;
import com.mpush.util.DefaultLogger;

public class MPushEngine {

    private static MPushEngine INSTANCE;
    private Context context;

    private MPushEngine(Context context) {
        this.context = context;
    }

    public static MPushEngine getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MPushEngine.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MPushEngine(context);
                }
            }
        }
        return INSTANCE;
    }

    public void initPush() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpezcQ5a02OngBSo7pX0jNBsmg/++j2C/C7pOrRS7w4saPySfM8+DDTIzYSafSNUkrvYltJ649bDuNNGZM0Dj+9M7UsWaVC/83FnI8BGEFyaHluG1YhQb6CYnPobFaNr2dkncBRJq246JR6W5CaGAiP8izrglHuGVPj6PvGXJu3QIDAQAB";
        String UID = "10002";
        String IMEIID = SharedUtils.getString(App.getMContext(), Global.APP_IMEI);
        String token = SharedUtils.getToken(context);
        ClientConfig cc = ClientConfig.build()
                .setPublicKey(publicKey)
                .setAllotServer(AppConst.ALLCOK_SERVER)
                .setDeviceId(IMEIID)
                .setOsName("android" + android.os.Build.BRAND)
                .setOsVersion(Build.VERSION.RELEASE)
                .setClientVersion(BuildConfig.VERSION_NAME)
                .setLogger(new DefaultLogger())
                .setLogEnabled(BuildConfig.DEBUG)
                .setEnableHttpProxy(true)
                .setUserId(UID)
                .setToken(token)
                .setTags("youtime");

        Log.e("TAG--alloc--setToken", token);

        MPush.I.checkInit(context).setClientConfig(cc);
        MPush.I.checkInit(context).startPush();
    }

    private String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            String time = Long.toString((System.currentTimeMillis() / (1000 * 60 * 60)));
            deviceId = time + time;
        }
        return deviceId;
    }
}
