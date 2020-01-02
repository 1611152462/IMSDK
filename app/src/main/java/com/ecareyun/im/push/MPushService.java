/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     ohun@live.cn (夜色)
 */


package com.ecareyun.im.push;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecareyun.im.App;
import com.ecareyun.im.Global;
import com.ecareyun.im.framwork.utils.ProcessUtils;
import com.ecareyun.im.imsdk.R;
import com.ecareyun.im.model.bean.message.BaseMessage;
import com.ecareyun.im.model.bean.message.GroupBaseMessage;
import com.ecareyun.im.model.bean.message.MessageExt;
import com.ecareyun.im.model.db.entity.SessionEntity;
import com.ecareyun.im.model.db.manager.DaoUtils;
import com.example.mylibrary.utils.eventbus.Event;
import com.example.mylibrary.utils.eventbus.EventBusUtil;
import com.google.gson.Gson;
import com.mpush.api.Client;
import com.mpush.api.ClientListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yxx on 2016/2/13.
 *
 * @author ohun@live.cn
 */
public final class MPushService extends Service implements ClientListener {
    public static final String ACTION_MESSAGE_RECEIVED = "com.mpush.MESSAGE_RECEIVED";
    public static final String ACTION_GROUPMESSAGE_RECEIVED = "com.mpush.GROUPMESSAGE_RECEIVED";
    public static final String ACTION_NOTIFICATION_OPENED = "com.mpush.NOTIFICATION_OPENED";
    public static final String ACTION_KICK_USER = "com.mpush.KICK_USER";
    public static final String ACTION_CONNECTIVITY_CHANGE = "com.mpush.CONNECTIVITY_CHANGE";
    public static final String ACTION_HANDSHAKE_OK = "com.mpush.HANDSHAKE_OK";
    public static final String ACTION_BIND_USER = "com.mpush.BIND_USER";
    public static final String ACTION_UNBIND_USER = "com.mpush.UNBIND_USER";
    public static final String EXTRA_PUSH_MESSAGE = "push_message";
    public static final String EXTRA_PUSH_MESSAGE_ID = "push_message_id";
    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_DEVICE_ID = "device_id";
    public static final String EXTRA_BIND_RET = "bind_ret";
    public static final String EXTRA_CONNECT_STATE = "connect_state";
    public static final String EXTRA_HEARTBEAT = "heartbeat";
    private int SERVICE_START_DELAYED = 5;

    private static String TAG = MPushEngine.class.getSimpleName();

    private int chatIngOtherUserId = 0;
    private final int PID = android.os.Process.myPid();
    private ServiceConnection mConnection;
    private NotificationManager notificationManager;
    private String notificationId = "channelId";
    private String notificationName = "channelName";
    private Handler handler = new Handler(Looper.myLooper());
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private SessionEntity entity;
    private DaoUtils daoUtils;
    private IntentFilter mFilter;
    private MPushReceiver mPushReceiver;
    //每条消息之间的时间间隔
    private long endTime;
    //消息的id
    private long msgId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        daoUtils = DaoUtils.getInstance(App.getMContext());
        cancelAutoStartService(this);
        EventBusUtil.register(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_MIN);
            notificationManager.createNotificationChannel(channel);
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        startForeground(1, getNotification());
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                stopForeground(true);
            }
        }.sendEmptyMessageDelayed(0, 5000);
        mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mPushReceiver = new MPushReceiver();
        registerReceiver(mPushReceiver, mFilter);
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("YouTime")
                // .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)//统一消除声音和震动
                .setContentText("应用信息设置");
        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }

    /**
     * 关系变化时收到的消息
     *
     * @param client
     * @param content
     * @param messageId
     */
    @Override
    public void onReceiveRelation(Client client, String content, int messageId) {
        Log.e(TAG, "onReceiveRelation => " + content + "//" + messageId);
        if (!TextUtils.isEmpty(content)) {
            JSONObject jsonObject = JSONObject.parseObject(content);
            if (jsonObject.containsKey("ids") && jsonObject.containsKey("type")) {
                int receiveId = Integer.valueOf(jsonObject.getString("ids"));
                int relation = jsonObject.getInteger("type");
                daoUtils.getSessionManager().updateRelationByReceiveId(receiveId, relation);
                EventBusUtil.sendEvent(new Event(Global.TO_REFRESH_SESSION_LIST, ""));
            }
        }
    }

    /**
     * @param client
     * @param content
     * @param messageId
     */
    @Override
    public void onReceiveChat(Client client, String content, int messageId) {
        Log.e(TAG, "onReceiveChat => " + content + "//" + messageId);
        if (!TextUtils.isEmpty(content)) {
            Message msg = msgHandler.obtainMessage();
            msg.obj = content;
            msg.what = messageId;
            msgHandler.sendMessage(msg);
        }
    }


    GroupBaseMessage bms;
    @Override
    public void onReceiveGroupChat(Client client, String content, int messageId) {
        Log.e(TAG, "onReceiveGroupChat => " + content + "//" + messageId);
        if (!TextUtils.isEmpty(content)) {
            Message msg = msgGroupHandler.obtainMessage();
            msg.obj = content;
            msg.what = messageId;
            GroupBaseMessage bms = new Gson().fromJson(content, GroupBaseMessage.class);
            switch (bms.getFlag()){
                case 0:
                    msgGroupHandler.sendMessage(msg);
                    break;
                case 1:
                    switch (bms.getSeq()){
                        case 0:
                            msgGroupHandler.sendMessage(msg);
                            break;
                            default:
                                msgUpDbGroupHandler.sendMessage(msg);
                    }
                    break;
            }

        }
    }


    //普通单聊消息处理
    private Handler msgHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = (String) msg.obj;
            int messageId = msg.what;

//            JSONObject jsonObject = JSONObject.parseObject(content);
//            BaseMessage bm = JSON.parseObject(content, BaseMessage.class);
//            bm.setUniqueId(bm.gettId() + "and" + bm.getfId());
//            bm.setBoubleType(1);
//            bm.setReceivePic(ImageUrlUtils.encryptAvatarUrl(bm.getfId()));
//            bm.setSendPic(ImageUrlUtils.encryptAvatarUrl(bm.gettId()));
//            if (bm.getfRely() == BaseMessage.RELY_FANS) {
//                bm.setfRely(BaseMessage.RELY_ATTENTION);
//            } else if (bm.getfRely() == BaseMessage.RELY_ATTENTION) {
//                bm.setfRely(BaseMessage.RELY_FANS);
//            }
//
//            if (bm.getbType() == BaseMessage.MSG_TYPE_TEXT) {
//                bm.setExt(new Gson().fromJson(jsonObject.getJSONObject("ext").toJSONString(), MessageExt.class));
//            } else if (bm.getbType() == BaseMessage.MSG_TYPE_IMAGE) {
//                MessageExt imageExt = new Gson().fromJson(jsonObject.getJSONObject("ext").toJSONString(), MessageExt.class);
//                int[] arr = ImageUrlUtils.fromUrlGetWH(imageExt.url);
//                imageExt.width = arr[0];
//                imageExt.height = arr[1];
//                bm.setExt(imageExt);
//            } else if (bm.getbType() == BaseMessage.MSG_TYPE_DYNAMIC) {
//                MessageExt dynamicExt = new Gson().fromJson(jsonObject.getJSONObject("ext").toJSONString(), MessageExt.class);
//                int[] arr = ImageUrlUtils.fromUrlGetWH(dynamicExt.url);
//                dynamicExt.width = arr[0];
//                dynamicExt.height = arr[1];
//                bm.setExt(dynamicExt);
//            } else if (bm.getbType() == BaseMessage.MSG_TYPE_VOICE) {
//                bm.setExt(new Gson().fromJson(jsonObject.getJSONObject("ext").toJSONString(), MessageExt.class));
//            } else if (bm.getbType() == BaseMessage.MSG_TYPE_LOCATION) {
//                MessageExt locationExt = new Gson().fromJson(jsonObject.getJSONObject("ext").toJSONString(), MessageExt.class);
//                int[] arr = ImageUrlUtils.fromUrlGetWH(locationExt.url);
//                locationExt.width = arr[0];
//                locationExt.height = arr[1];
//                bm.setExt(locationExt);
//            }
//            List<BaseMessage> baseMessageList = new ArrayList<>();
//            daoUtils.getMessageManager().insertMessageEntity(BaseMessage.convertMessageEntity(bm));
//
//            // todo 如果当前聊天界面正在与某人聊天，更新session表最后一条信息，添加数据到message表
//            // todo 如果收到的消息不是与正在聊天的人发来的
//            if (chatIngOtherUserId == 0 || chatIngOtherUserId != bm.getfId()) {
//                // 更新session表，记录未读消息数。
//                // 在message表中添加收到的消息
//                // 在登陆的情况下，发状态栏消息
//                entity = null;
//                entity = DaoUtils.getInstance(App.getMContext()).getSessionManager().querySessionById(bm.getfId());
//                if (entity == null) {
//                    entity = new SessionEntity(null, bm.getfRely(), bm.getcType(), bm.getId(), bm.getfId(), bm.getfNm(), ImageUrlUtils.encryptAvatarUrl(bm.getfId()), bm.getbType(),
//                            bm.getsTime(), bm.getLabelText(), 1, 1, bm.getbType(), (byte) 0, bm.getfId() + "and" + bm.getUniqueId());
//                } else {
//                    entity.setMessageId(bm.getId());
//                    entity.setMessageType(bm.getbType());
//                    entity.setContent(bm.getLabelText());
//                    entity.setSendData(bm.getsTime());
////                    UserNotesEntity userNotesEntity = daoUtils.getUserNotesManager().queryUserNotes(bm.getfId());
//                    String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), bm.getfId() + "");
//                    if (StringUtil.isNullOrEmpty(noteName)) {
//                        entity.setReceiveName(bm.getfNm());
//                    } else {
//                        entity.setReceiveName(noteName);
//                    }
//                    entity.setRelationType(bm.getfRely());
//                    entity.setReceivePic(ImageUrlUtils.encryptAvatarUrl(bm.getfId()));
//                    entity.setUnReadNum(entity.getUnReadNum() + 1); // 未读消息数+1
//                }
//                Log.e(TAG, entity.toString());
//
//                daoUtils.getSessionManager().insertSessionEntity(entity);
//                EventBusUtil.sendEvent(new Event(Global.TO_REFRESH_SESSION_LIST + bm.getfRely(), ""));
//                EventBusUtil.sendEvent(new Event(Global.TO_REFRESH_MESSAGE_NUM, ""));
////                MediaPlayerUtils.I.newMessageTip(bm.getfId());
//                // todo 发notifycation
//                String string = jsonObject.getJSONObject("ext").getString("txt");
////                if (!appOnForeground()) {
//
//                try {
//                    ChatSettingEntity chatSettingEntity = daoUtils.getChatSettingManager().queryChatSettingEntity(bm.getfId());
//                    if (chatSettingEntity != null) {
//                        Logger.e(TAG, chatSettingEntity.toString());
//                        byte b = chatSettingEntity.getNoDisturb();
//                        Logger.e(TAG, b + "------->>>>");
//                        if (b != 1) {
//                            sendBroadcast(new Intent(MPushService.this, MyReceiver.class)
//                                    .setAction(ACTION_MESSAGE_RECEIVED)
//                                    .addCategory(BuildConfig.APPLICATION_ID)
//                                    .putExtra(EXTRA_PUSH_MESSAGE, content)
//                                    .putExtra(EXTRA_PUSH_MESSAGE_ID, messageId)
//                            );
//                        }
//                    }else{
//                        sendBroadcast(new Intent(MPushService.this, MyReceiver.class)
//                                .setAction(ACTION_MESSAGE_RECEIVED)
//                                .addCategory(BuildConfig.APPLICATION_ID)
//                                .putExtra(EXTRA_PUSH_MESSAGE, content)
//                                .putExtra(EXTRA_PUSH_MESSAGE_ID, messageId)
//                        );
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
////                }
//            } else {
//                //如果接收的消息正好是正在聊天中的用户发来的，还要做什么操作呢？
//                EventBusUtil.sendStickyEvent(new Event<BaseMessage>(Global.MPUSH_MESSAGE, bm));
//            }
        }
    };
    private Handler msgGroupHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = (String) msg.obj;
            int messageId = msg.what;

//            AsyncTask task = new AsyncTask<Object, Integer, Integer>() {
//                @Override
//                protected Integer doInBackground(Object... objects) {
//
//                    bms = new Gson().fromJson(content, GroupBaseMessage.class);
//                    List<GroupBaseMessage.Body> list = new ArrayList<>();
//                    for (GroupBaseMessage.Body bm : bms.getBody()) {
//                        bm.setUniqueId(Api.UID + "andgroup" + bms.getgId());
//                        list.add(bm);
//                    }
//                    bms.setBody(list);
//                    bms.setBoubleType(1);
//                    daoUtils.getMessageManager().insertListMessage(GroupBaseMessage.convertListMessageEntity(bms));
//                    // todo 如果当前聊天界面正在与某人聊天，更新session表最后一条信息，添加数据到message表
//                    // todo 如果收到的消息不是与正在聊天的人发来的
//                    if (chatIngOtherUserId == 0 || chatIngOtherUserId != Integer.parseInt(bms.getgId())) {
//                        GroupBaseMessage.Body bm = null;
//                        switch (bms.getFlag()){
//                            case 1:
//                                bm = bms.getBody().get(0);
//                                break;
//                                default:
//                                    bm = bms.getBody().get(bms.getBodyLen() - 1);
//
//                        }
//
//                        // 更新session表，记录未读消息数。
//                        // 在message表中添加收到的消息
//                        // 在登陆的情况下，发状态栏消息
//                        entity = null;
//                        entity = DaoUtils.getInstance(MyApp.getInstance()).getSessionManager().querySessionById(Integer.parseInt(bms.getgId()));
//                        if (entity == null) {
//                            entity = new SessionEntity(null, 0, bm.getbType(), bm.getMsgId(), Integer.parseInt(bms.getgId()), bms.getgName(), ImageUrlUtils.encryptAvatarUrl(Long.parseLong(bms.getgId())), bm.getbType(),
//                                    bm.getsTime(), bms.getLabelText(), 1, 1, bm.getbType(), (byte) 0, bms.getgId() + "and" + bm.getUniqueId());
//                        } else {
//                            entity.setMessageId(bm.getMsgId());
//                            entity.setMessageType(bm.getbType());
//                            entity.setContent(bms.getLabelText());
//                            entity.setSendData(bm.getsTime());
//                            entity.setReceiveName(bms.getgName());
//                            entity.setRelationType(0);
//                            entity.setReceivePic(ImageUrlUtils.encryptAvatarUrl(Long.parseLong(bm.getfId())));
//                            entity.setUnReadNum(entity.getUnReadNum() + bms.getBodyLen()); // 未读消息数+1
//                        }
//                        Logger.e(TAG, entity.toString());
//
//                        daoUtils.getSessionManager().insertSessionEntity(entity);
//                        EventBusUtil.sendEvent(new Event(Global.TO_REFRESH_SESSION_LIST + 0, ""));
//                        EventBusUtil.sendEvent(new Event(Global.TO_REFRESH_MESSAGE_NUM, ""));
//
//                        try {
//                            ChatSettingEntity chatSettingEntity = daoUtils.getChatSettingManager().queryChatSettingEntity(Integer.parseInt(bms.getgId()));
//                            if (chatSettingEntity != null) {
//                                Logger.e(TAG, chatSettingEntity.toString());
//                                byte b = chatSettingEntity.getNoDisturb();
//                                Logger.e(TAG, b + "------->>>>");
//                                if (b != 1) {
//                                    if(bms.geteTime() - endTime>1000) {
//                                        endTime = bms.geteTime();
//                                        sendBroadcast(new Intent(MPushService.this, MyReceiver.class)
//                                                .setAction(ACTION_GROUPMESSAGE_RECEIVED)
//                                                .addCategory(BuildConfig.APPLICATION_ID)
//                                                .putExtra(EXTRA_PUSH_MESSAGE, content)
//                                                .putExtra(EXTRA_PUSH_MESSAGE_ID, messageId)
//                                        );
//                                    }
//                                }
//                            } else {
//                                if(bms.geteTime() - endTime>1000) {
//                                    endTime = bms.geteTime();
//                                    sendBroadcast(new Intent(MPushService.this, MyReceiver.class)
//                                            .setAction(ACTION_GROUPMESSAGE_RECEIVED)
//                                            .addCategory(BuildConfig.APPLICATION_ID)
//                                            .putExtra(EXTRA_PUSH_MESSAGE, content)
//                                            .putExtra(EXTRA_PUSH_MESSAGE_ID, messageId)
//                                    );
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return 0;
//                    } else {
//                        if(bms.getId() != msgId) {
//                            msgId = bms.getId();
//                            //倒序
//                            List<GroupBaseMessage.Body> listbody = bms.getBody();
//                            Collections.reverse(listbody);
//                            for (GroupBaseMessage.Body bd :listbody) {
//                                switch (bd.getExt().gType){
//                                    case 0:
//                                        if(null != bd.getExt().members && bd.getExt().members.size()>0) {
//                                            for (int i = 0;i < bd.getExt().members.size();i++) {
//                                                if (bd.getExt().members.get(i).id == Api.UID) {
//                                                    SharedUtils.putInt(MyApp.getInstance().getContext(), bms.getgId() + "ISDEL", 0);//0没有被踢1被踢了
//                                                }
//                                            }
//                                        }
//                                        break;
//                                    case 2:
//                                        if(null != bd.getExt().members && bd.getExt().members.size()>0) {
//                                            for (int i = 0;i < bd.getExt().members.size();i++) {
//                                                if (bd.getExt().members.get(i).id == Api.UID) {
//                                                    SharedUtils.putInt(MyApp.getInstance().getContext(), bms.getgId() + "ISDEL", 1);//0没有被踢1被踢了
//                                                }
//                                            }
//                                        }
//                                        break;
//                                }
//                                EventBusUtil.sendStickyEvent(new Event<BaseMessage>(Global.MPUSH_MESSAGE, getBaseMessage(bms, bd)));
//                            }
//                        }
//                        return 1;
//                        //如果接收的消息正好是正在聊天中的用户发来的，还要做什么操作呢？
//
//                    }
//                }
//
//                @Override
//                protected void onPostExecute(Integer flag) {
//                    switch (flag) {
//                        case 0:
//
//                            break;
//                        case 1:
//
//                            break;
//                    }
//                }
//            };
//            task.execute();//串行的一个一个执行
//            task.executeOnExecutor(customPool);//并行的
        }
    };

    //群组离线消息后台插入数据库不更新界面
    private Handler msgUpDbGroupHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = (String) msg.obj;

//            AsyncTask task = new AsyncTask<Object, Integer, Integer>() {
//                @Override
//                protected Integer doInBackground(Object... objects) {
//
//                    bms = new Gson().fromJson(content, GroupBaseMessage.class);
//                    List<GroupBaseMessage.Body> list = new ArrayList<>();
//                    for (GroupBaseMessage.Body bm : bms.getBody()) {
//                        bm.setUniqueId(Api.UID + "andgroup" + bms.getgId());
//                        list.add(bm);
//                    }
//                    bms.setBody(list);
//                    bms.setBoubleType(1);
//                    daoUtils.getMessageManager().insertListMessage(GroupBaseMessage.convertListMessageEntity(bms));
//
//                    // todo 如果当前聊天界面正在与某人聊天，更新session表最后一条信息，添加数据到message表
//                    // todo 如果收到的消息不是与正在聊天的人发来的
//                    if (chatIngOtherUserId == 0 || chatIngOtherUserId != Integer.parseInt(bms.getgId())) {
////
//                        return 0;
//                    } else {
//                        return 1;
//                    }
//                }
//
//                @Override
//                protected void onPostExecute(Integer flag) {
//                    switch (flag) {
//                        case 0:
//
//                            break;
//                        case 1:
//
//                            break;
//                    }
//                }
//            };
//            task.execute();//串行的一个一个执行
        }
    };

    /**
     * 群聊时候获取message
     * @return
     */
    public BaseMessage getBaseMessage(GroupBaseMessage groupBaseMessage,GroupBaseMessage.Body entity){
        BaseMessage message = new BaseMessage();
        setValueMessage(message, groupBaseMessage,entity);
        message.setExt(entity.getExt());
        return message;
    }

    private static void setValueMessage(BaseMessage message, GroupBaseMessage groupBaseMessage, GroupBaseMessage.Body entity) {
//        if (message != null && entity != null) {
//            message.setId(entity.getMsgId());
//            message.setbType(entity.getbType());
//            message.setcType(1);
//            message.setfId(Integer.parseInt(entity.getfId()));
//            message.setfNm(entity.getfNm());
//            message.setSendPic(ImageUrlUtils.encryptAvatarUrl(Api.UID));
//            message.setfRely(BaseMessage.RELY_GROUP);
//            message.settId(Integer.valueOf(groupBaseMessage.getgId()));
//            message.settNm(StringUtil.isNullOrEmpty(entity.getExt().gName)?groupBaseMessage.getgName():entity.getExt().gName);
//            message.setReceivePic(ImageUrlUtils.encryptAvatarUrl(Long.parseLong(entity.getfId())));
//            message.setBoubleType(1);
//            message.setsTime(entity.getsTime());
//            message.setsType(1);
//            message.setIsRead((byte)1);
//            message.setUniqueId(entity.getUniqueId());
//        }
    }

    private boolean appOnForeground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(cn.getPackageName()) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    //是否在前台
    private boolean isAppForeground(String packageName) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(packageName)) {
            return true;
        }
        return false;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Event event) {
        if (event.getCode() == Global.CHAT_ING) {
            this.chatIngOtherUserId = (Integer) event.getData();
        } else if (event.getCode() == Global.CHAT_EXIT) {
            this.chatIngOtherUserId = 0;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand => flag : " + flags + "// startId : " + startId + "===" + MPush.I.hasStarted());
        if (!MPush.I.hasStarted()) {
            MPush.I.checkInit(this).create(this);
        }
        if (MPush.I.hasStarted()) {
            if (MPushReceiver.hasNetwork(this)) {
                MPush.I.client.start();
            }
            MPushFakeService.startForeground(this);
            flags = START_STICKY;
            SERVICE_START_DELAYED = 5;
            return super.onStartCommand(intent, flags, startId);
        } else {
            int ret = super.onStartCommand(intent, flags, startId);
            stopSelf();
            SERVICE_START_DELAYED += SERVICE_START_DELAYED;
            return ret;
        }
    }

    /**
     * service停掉后自动启动应用
     *
     * @param context
     * @param delayed 延后启动的时间，单位为秒
     */
    private static void startServiceAfterClosed(Context context, int delayed) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayed * 1000, getOperation(context));
    }

    public static void cancelAutoStartService(Context context) {
        AlarmManager alarm = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(getOperation(context));
    }

    private static PendingIntent getOperation(Context context) {
        Intent intent = new Intent(context, MPushService.class);
        PendingIntent operation = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return operation;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mPushReceiver);
        EventBusUtil.unregister(this);
        MPushReceiver.cancelAlarm(this);
        MPush.I.destroy();
//        if (Api.isLogin && Api.UID > 0) {
//            startServiceAfterClosed(this, SERVICE_START_DELAYED);//5s后重启
//            Logger.e(TAG, "MPushService - 5s后重启 -");
//        } else {
//            Logger.e(TAG, "MPushService exec onDestroy");
//        }
    }

    @Override
    public void onReceivePush(Client client, byte[] content, int messageId) {
        try {
            String str = new String(content, "UTF-8");
            Log.e(TAG, "onReceivePush => content : " + str + "//" + messageId + " / " + Thread.currentThread().getName());
            if (!TextUtils.isEmpty(str)) {
                JSONObject jsonObject = JSONObject.parseObject(str);
                if (jsonObject.containsKey("content")) {
                    String result = jsonObject.getJSONObject("content").getString("content");
                    if (ProcessUtils.isProcessRunning(MPushService.this, "com.source.im")) {
//                        Intent intent = new Intent(MPushService.this, PushDialogActivity.class);
//                        intent.putExtra("content", result);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void logout() {
//        mvpModel.getInstance().postNetData(Api.SYSTEM_REGISTER, YKTokenBean.class, Api.getRegisterYKTokenToApp(Api.IMEIID, "", "", "1", "1", HashKeyUtils.hashKeyForDisk(Api.IMEIID)), new mvpCallBack<YKTokenBean>() {
//            @Override
//            public void onSuccess(YKTokenBean data) {
//                daoUtils.getUserNotesManager().deleteAllUserNotes();
//                daoUtils.resetNull();
//                String token = data.getData().getToken();
//                MPush.I.pausePush();
//                MPush.I.unbindAccount();
//                MPush.I.stopPush();
//
//                Api.isLogin = false;
//                SharedUtils.putToken(MPushService.this, token);
//            }
//
//            @Override
//            public void onFailure(int code) {
//                Logger.e(TAG, "onFailure=>" + code);
//            }
//
//            @Override
//            public void onError(String error) {
//                Logger.e(TAG, "onError=>" + error);
//            }
//        });
    }

    @Override
    public void onKickUser(String deviceId, String userId) {

//        Log.e(TAG, "onKickUser => " + deviceId + "//" + userId + " / " + Thread.currentThread().getName());
//
//        logout();
//
//        Intent intent = new Intent(MPushService.this, LogoutDialogActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//
//        sendBroadcast(new Intent(this, MyReceiver.class)
//                .setAction(ACTION_KICK_USER)
//                .addCategory(BuildConfig.APPLICATION_ID)
//                .putExtra(EXTRA_DEVICE_ID, deviceId)
//                .putExtra(EXTRA_USER_ID, userId)
//        );
    }

    @Override
    public void onBind(boolean success, String userId) {

//        Log.e(TAG, "onBind => " + success + "//" + userId + " / " + Thread.currentThread().getName());
//
//        sendBroadcast(new Intent(this, MyReceiver.class)
//                .setAction(ACTION_BIND_USER)
//                .addCategory(BuildConfig.APPLICATION_ID)
//                .putExtra(EXTRA_BIND_RET, success)
//                .putExtra(EXTRA_USER_ID, userId)
//        );

    }

    @Override
    public void onUnbind(boolean success, String userId) {
//        Log.e(TAG, "onUnbind => " + success + "//" + userId + " / " + Thread.currentThread().getName());
//        sendBroadcast(new Intent(this, MyReceiver.class)
//                .setAction(ACTION_UNBIND_USER)
//                .addCategory(BuildConfig.APPLICATION_ID)
//                .putExtra(EXTRA_BIND_RET, success)
//                .putExtra(EXTRA_USER_ID, userId)
//        );
    }

    @Override
    public void onConnected(Client client) {
//        Log.e(TAG, "onConnected => " + "//" + client + " / " + Thread.currentThread().getName());
//        sendBroadcast(new Intent(this, MyReceiver.class)
//                .setAction(ACTION_CONNECTIVITY_CHANGE)
//                .addCategory(BuildConfig.APPLICATION_ID)
//                .putExtra(EXTRA_CONNECT_STATE, true)
//        );
    }

    @Override
    public void onDisConnected(Client client) {
//        Log.e(TAG, "onDisConnected => " + "//" + client + " / " + Thread.currentThread().getName());
//        MPushReceiver.cancelAlarm(this);
//        sendBroadcast(new Intent(this, MyReceiver.class)
//                .setAction(ACTION_CONNECTIVITY_CHANGE)
//                .addCategory(BuildConfig.APPLICATION_ID)
//                .putExtra(EXTRA_CONNECT_STATE, false)
//        );
    }

    @Override
    public void onHandshakeOk(Client client, int heartbeat) {
//        Log.e(TAG, "onHandshakeOk => " + client + "//" + heartbeat + " / " + Thread.currentThread().getName());
//        MPushReceiver.startAlarm(this, heartbeat - 1000);
//        sendBroadcast(new Intent(this, MyReceiver.class)
//                .setAction(ACTION_HANDSHAKE_OK)
//                .addCategory(BuildConfig.APPLICATION_ID)
//                .putExtra(EXTRA_HEARTBEAT, heartbeat)
//        );
    }


}
