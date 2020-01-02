package com.ecareyun.im.push;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.app.Notification.VISIBILITY_SECRET;


/**
 * @author xuyj
 */
public class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    private NotificationChannel mNotificationChannel;
    public static NotificationHelper I = null;
    public static final String CHANNEL_ID = "my_channel_01";
    private static final String CHANNEL_NAME = "新消息通知";
    private static final String CHANNEL_DESCRIPTION = "This is my channel";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    private static Bitmap bitmap;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            bitmap = bundle.getParcelable("bitmap");
        }
    };

    public static NotificationHelper getInstance(Context c) {
        if (I == null) {
            synchronized (NotificationHelper.class) {
                if (I == null) {
                    I = new NotificationHelper(c);
                }
            }
        }
        return I;
    }

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.setDescription(CHANNEL_DESCRIPTION);
            //mNotificationChannel.setSound(Uri.parse("android.resource://" + MyApp.getInstance().getContext().getPackageName() + "/" + R.raw.new_message), null);
            mNotificationChannel.canBypassDnd();//是否绕过请勿打扰模式
            mNotificationChannel.setBypassDnd(true);//设置可绕过  请勿打扰模式
            mNotificationChannel.canShowBadge();//桌面launcher的消息角标
            mNotificationChannel.setShowBadge(true);
            mNotificationChannel.enableLights(true);//闪光灯
            mNotificationChannel.setLightColor(Color.GREEN);//闪关灯的灯光颜色
            mNotificationChannel.enableVibration(true);//是否允许震动
            mNotificationChannel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
            mNotificationChannel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            getNotificationManager().createNotificationChannel(mNotificationChannel);
        }
    }

//    public Notification getNotificationCompat(String uid, String title, String content) {
//        Notification notification = null;
//        //Logger.e("avatar--->>>", Api.BASE_URL_OSS + HashKeyUtils.hashKeyForDisk("useravatar_" + uid) + ".png");
//        //GetImageInputStream(Api.BASE_URL_OSS + HashKeyUtils.hashKeyForDisk("useravatar_" + uid) + ".png");
//        Bundle bundle = new Bundle();
//        bundle.putString("uId", String.valueOf(uid));
//        bundle.putString("nickname", title);
//        //Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra(Constant.BUNDLE_KEY, bundle);
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
//            builder.setPriority(NotificationCompat.PRIORITY_MAX);
//            builder.setContentTitle(title);
//            builder.setContentText(content);
//            //builder.setSound(Uri.parse("android.resource://" + MyApp.getInstance().getContext().getPackageName() + "/" + R.raw.new_message));
//            builder.setDefaults(Notification.DEFAULT_ALL);
//            //builder.setSmallIcon(R.mipmap.logo);
//            if (bitmap != null) {
//                builder.setLargeIcon(bitmap);
//            }
//            //点击自动删除通知
//            builder.setAutoCancel(true);
//            notification = builder.build();
//        } else {
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setContentTitle(title);
//            builder.setContentText(content);
//            //builder.setSmallIcon(R.mipmap.logo);
//            //builder.setSound(Uri.parse("android.resource://" + MyApp.getInstance().getContext().getPackageName() + "/" + R.raw.new_message));
//            builder.setDefaults(Notification.DEFAULT_ALL);
//            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
//            if (bitmap != null) {
//                builder.setLargeIcon(bitmap);
//            }
//            builder.setAutoCancel(true);
//            notification = builder.build();
//        }
//        notification.contentIntent = pIntent;
//        return notification;
//    }

    public void notify(int id, Notification notification) {
        if (getNotificationManager() != null) {
            getNotificationManager().notify(id, notification);
        }
    }

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
//    public static void GetImageInputStream(String imageurl) {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    URL url;
//                    HttpURLConnection connection = null;
//                    Bitmap bitmap = null;
//                    url = new URL(imageurl);
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setConnectTimeout(6000); //超时设置
//                    connection.setDoInput(true);
//                    connection.setUseCaches(false); //设置不使用缓存
//                    InputStream inputStream = connection.getInputStream();
//                    bitmap = BitmapFactory.decodeStream(inputStream);
//                    inputStream.close();
//                    Message message = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("bitmap", bitmap);
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

    public void openChannelSetting(String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
            startActivity(intent);
    }

    public void openNotificationSetting() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
            startActivity(intent);
    }

    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        return mNotificationManager;
    }

    public void deleteNotify(int id) {
        if (getNotificationManager() != null) {
            getNotificationManager().cancel(id);
        }
    }

    /**
     * 通知设置
     */
    private static final int NOTIFYID = 0;
    private static String beforeChannelId;
    NotificationChannel channel = null;
    Notification.Builder builder;
//    public void notifyMsg(String uid, String title, String content,boolean isClose,int isGroup){
//
//                mNotificationManager = getNotificationManager();
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    if (SharedUtils.getInt(MyApp.getInstance().getContext(), Api.UID + "STATE", 0) != 0) {
//                        SharedUtils.putInt(NotificationHelper.this, Api.UID + "STATE", 0);
//                        mNotificationManager.deleteNotificationChannel(SharedUtils.getString(MyApp.getInstance().getContext(), Api.UID + "BEFOREID", ""));
//                    }
//                    channel = new NotificationChannel(CHANNEL_ID + SharedUtils.getInt(NotificationHelper.this, Api.UID + "NUM", 0), CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//
//                    if (!SharedUtils.getBoolean(NotificationHelper.this, Api.UID + "MSG", true)) {
//                        if (SharedUtils.getBoolean(NotificationHelper.this, Api.UID + "VIBRATE", true)) {
//                            channel.setSound(null, null);
//                            channel.setImportance(NotificationManager.IMPORTANCE_LOW);
//                        }
//                    } else {
//                        if (SharedUtils.getBoolean(NotificationHelper.this, Api.UID + "VIBRATE", true)) {
//
//                        } else {
//                            // 设置通知出现时不震动
//                            channel.enableVibration(false);
//                            channel.setVibrationPattern(new long[]{0});
//                        }
//                    }
//                    mNotificationManager.createNotificationChannel(channel);
//                        builder = new Notification.Builder(NotificationHelper.this, CHANNEL_ID + SharedUtils.getInt(NotificationHelper.this, Api.UID + "NUM", 0));
//                        builder.setSmallIcon(R.mipmap.logo);
//                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
//
//                    SharedUtils.putString(MyApp.getInstance().getContext(), Api.UID + "BEFOREID", CHANNEL_ID + SharedUtils.getInt(NotificationHelper.this, Api.UID + "NUM", 0));
//                } else {
//                        builder = new Notification.Builder(NotificationHelper.this);
//                        builder.setSmallIcon(R.mipmap.logo);
//                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
//
//                }
//
//                Logger.e("avatar--->>>", Api.BASE_URL_OSS + HashKeyUtils.hashKeyForDisk("useravatar_" + uid) + ".png");
//                GetImageInputStream(Api.BASE_URL_OSS + HashKeyUtils.hashKeyForDisk("useravatar_" + uid) + ".png");
//                Intent intent=null;
//                switch (isGroup){
//                    case 1:
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("uId", Integer.parseInt(uid));
//                        bundle.putString("nickname", title);
//                        intent = new Intent(NotificationHelper.this, ChatActivity.class);
//                        intent.putExtra(Constant.BUNDLE_KEY, bundle);
//                        break;
//                    case 2:
//                        SharedUtils.putString(MyApp.getInstance().getContext(),uid+"",title);
//                        EventBusUtil.sendStickyEvent(new Event(Global.LOGIN_USER_ID));
//                        Bundle bundle1 = new Bundle();
//                        bundle1.putInt("uId", Integer.parseInt(uid));
//                        bundle1.putString("nickname", title);
//                        bundle1.putInt("RELATIONTYPE", BaseMessage.RELY_GROUP);
//                        bundle1.putInt("UNREADNUM",0);
//                        intent = new Intent(NotificationHelper.this, QLChatActivity.class);
//                        intent.putExtra(Constant.BUNDLE_KEY, bundle1);
//                        break;
//                }
//
//                PendingIntent pIntent = PendingIntent.getActivity(NotificationHelper.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//                builder.setAutoCancel(true);
//                builder.setContentTitle(title);
//                builder.setContentText(content);
//                builder.setContentIntent(pIntent);
//                builder.setTicker("");//首次收到的时候，在状态栏中，图标的右侧显示的文字
////        builder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + MyApp.getInstance().getContext().getPackageName() + "/" + R.raw.new_message));
//                if (!SharedUtils.getBoolean(NotificationHelper.this, Api.UID + "MSG", true)) {
//                    if (SharedUtils.getBoolean(NotificationHelper.this, Api.UID + "VIBRATE", true)) {
//                        builder.setDefaults(Notification.DEFAULT_VIBRATE);
//                    }
//                } else {
//                    if (SharedUtils.getBoolean(NotificationHelper.this, Api.UID + "VIBRATE", true)) {
//                        builder.setDefaults(Notification.DEFAULT_ALL);
//                    } else {
//                        builder.setDefaults(Notification.DEFAULT_SOUND);
//                    }
//                }
//                // builder.setDefaults(Notification.DEFAULT_VIBRATE);//打开呼吸灯，声音，震动，触发系统默认行为
//                mNotificationManager.notify(NOTIFYID, builder.build());
//                //比较手机sdk版本与Android 5.0 Lollipop的sdk
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && !isClose) {
//                    builder.setVisibility(Notification.VISIBILITY_PUBLIC)
//                            //.setColor(context.getResources().getColor(R.color.small_icon_bg_color))//设置smallIcon的背景色
//                            .setFullScreenIntent(pIntent, true);//将Notification变为悬挂式Notification，使用这种模式的时候，activity 必须处于全屏状态，否则无效
//                    mNotificationManager.notify("PUSH", NOTIFYID, builder.build());
//                    new Handler() {
//                        @Override
//                        public void handleMessage(Message msg) {
//                            super.handleMessage(msg);
//                            mNotificationManager.cancel("PUSH", NOTIFYID);
//                        }
//                    }.sendEmptyMessageDelayed(0, 3000);
//                }
//
//
//    }


}