package com.ecareyun.im.push;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecareyun.im.App;
import com.ecareyun.im.model.bean.message.BaseMessage;
import com.ecareyun.im.model.bean.message.GroupBaseMessage;
import com.ecareyun.im.model.bean.message.TextExt;
import com.example.mylibrary.utils.Logger;
import com.example.mylibrary.utils.SharedUtils;
import com.example.mylibrary.utils.eventbus.Event;
import com.example.mylibrary.utils.eventbus.EventBusUtil;
import com.google.gson.Gson;
import com.mpush.api.Constants;

import java.util.HashMap;
import java.util.List;


public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (MPushService.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //TODO 收到新的消息通知 不是当前聊天用户 推送通知
            try {
//                int messageId = intent.getIntExtra(MPushService.EXTRA_PUSH_MESSAGE_ID, 0);
//                String content = intent.getStringExtra(MPushService.EXTRA_PUSH_MESSAGE);
//                Logger.e("收到新的通知--->>>>", content);
//
//                if (messageId > 0) MPush.I.ack(messageId);
//                if (TextUtils.isEmpty(content)) return;
//
//                JSONObject jsonObject = JSONObject.parseObject(content);
//                BaseMessage bm = JSON.parseObject(content, BaseMessage.class);
//                bm.setUniqueId(bm.gettId() + "and" + bm.getfId());
//
//                String fNm = "";
////            UserNotesEntity userNotesEntity = DaoUtils.getInstance(MyApp.getInstance()).getUserNotesManager().queryUserNotes(bm.getfId());
//                String noteName = SharedUtils.getNoteString(context, bm.getfId() + "");
//                if (!StringUtil.isNullOrEmpty(noteName)) {
//                    fNm = noteName;
//                } else {
//                    fNm = jsonObject.get("fNm").toString();
//                }
//                String fId = jsonObject.get("fId").toString();
//                String notifyContent = "";
//                if (bm.getbType() == BaseMessage.MSG_TYPE_TEXT) {
//                    TextExt ext = JSON.parseObject(jsonObject.getJSONObject("ext").toJSONString(), TextExt.class);
//                    if (SharedUtils.getBoolean(App.getMContext(), Api.UID + "ISSHOWDETAIL", true)) {
//                        notifyContent = ext.getTxt();
//                    } else {
//                        notifyContent = "您有新消息";
//                    }
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_IMAGE) {
//                    notifyContent = "[图片]";
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_DYNAMIC) {
//                    notifyContent = "[动态]";
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_VOICE) {
//                    notifyContent = "[语音]";
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_LOCATION) {
//                    notifyContent = "[位置]";
//                }
////            Notification notification = NotificationHelper.getInstance(context).getNotificationCompat(fId, fNm, notifyContent);
//                NotificationHelper.getInstance(context).notifyMsg(fId, fNm, notifyContent, true,1);
////            NotificationHelper.getInstance(context).notify(Integer.parseInt(fId), notification);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MPushService.ACTION_GROUPMESSAGE_RECEIVED.equals(intent.getAction())) {
            //TODO  群通知
            try {
//                int messageId = intent.getIntExtra(MPushService.EXTRA_PUSH_MESSAGE_ID, 0);
//                String content = intent.getStringExtra(MPushService.EXTRA_PUSH_MESSAGE);
//                Logger.e("收到新的通知--->>>>", content);
//
//                if (messageId > 0) MPush.I.ack(messageId);
//                if (TextUtils.isEmpty(content)) return;
//
//                GroupBaseMessage bms = new Gson().fromJson(content, GroupBaseMessage.class);
//                GroupBaseMessage.Body bm = bms.getBody().get(bms.getBodyLen()-1);
//                bm.setUniqueId(Api.UID + "andgroup" + bms.getgId());
//
//                String fNm = bms.getgName();
//
//                String fId = bms.getgId();
//                String notifyContent = "";
//                if (bm.getbType() == BaseMessage.MSG_TYPE_TEXT) {
//                    if (SharedUtils.getBoolean(MyApp.getInstance().getContext(), Api.UID + "ISSHOWDETAIL", true)) {
//                        notifyContent = bm.getExt().getTxt();
//                    } else {
//                        notifyContent = "您有新消息";
//                    }
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_IMAGE) {
//                    notifyContent = "[图片]";
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_DYNAMIC) {
//                    notifyContent = "[动态]";
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_VOICE) {
//                    notifyContent = "[语音]";
//                } else if (bm.getbType() == BaseMessage.MSG_TYPE_LOCATION) {
//                    notifyContent = "[位置]";
//                }else if (bm.getbType() == BaseMessage.MSG_CARD) {
//                    notifyContent = "[名片]";
//                }
//
//                NotificationHelper.getInstance(context).notifyMsg(fId, fNm, notifyContent, true,2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (MPushService.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //TODO 点击消息通知
//            HashMap<String, String> map = new HashMap<>();
//            map.put("uId", uid);
//            map.put("nickname", title);
//            Event event = new Event(Global.LOGIN_USER_ID, map);
//            EventBusUtil.sendStickyEvent(event);
        } else if (MPushService.ACTION_KICK_USER.equals(intent.getAction())) {
            //TODO  用户被踢下线了
        } else if (MPushService.ACTION_BIND_USER.equals(intent.getAction())) {
            //TODO 绑定用户 intent.getStringExtra(MPushService.EXTRA_USER_ID) + (intent.getBooleanExtra(MPushService.EXTRA_BIND_RET, false) ? "成功" : "失败")
        } else if (MPushService.ACTION_UNBIND_USER.equals(intent.getAction())) {
            //TODO 解绑用户 intent.getBooleanExtra(MPushService.EXTRA_BIND_RET, false)? "成功" : "失败"
        } else if (MPushService.ACTION_CONNECTIVITY_CHANGE.equals(intent.getAction())) {

            Log.e("aaaaaa", intent.getBooleanExtra(MPushService.EXTRA_CONNECT_STATE, false)
                    ? "MPUSH连接建立成功"
                    : "MPUSH连接断开");

            //TODO 连接 intent.getBooleanExtra(MPushService.EXTRA_CONNECT_STATE, false)? "MPUSH连接建立成功": "MPUSH连接断开"
        } else if (MPushService.ACTION_HANDSHAKE_OK.equals(intent.getAction())) {

            //TODO 心跳包保活 intent.getIntExtra(MPushService.EXTRA_HEARTBEAT,0)
        }
    }
}
