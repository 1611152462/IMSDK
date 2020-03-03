package com.ecareyun.im.imsdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.services.core.LatLonPoint;
import com.ecareyun.im.App;
import com.ecareyun.im.Global;
import com.ecareyun.im.api.OSSUploadFileConfig;
import com.ecareyun.im.framwork.utils.AppUtils;
import com.ecareyun.im.framwork.utils.SharedUtils;
import com.ecareyun.im.model.bean.UpLoadImgInfo;
import com.ecareyun.im.model.bean.message.BaseMessage;
import com.ecareyun.im.model.bean.message.MessageExt;
import com.ecareyun.im.model.db.entity.MessageEntity;
import com.ecareyun.im.model.db.manager.DaoUtils;
import com.ecareyun.im.push.MPush;
import com.ecareyun.im.push.MPushEngine;
import com.ecareyun.im.push.MPushMessageFactory;
import com.example.mylibrary.utils.DateUtils;
import com.example.mylibrary.utils.Logger;
import com.luck.picture.lib.entity.LocalMedia;
import com.mpush.api.ack.AckCallback;
import com.mpush.api.ack.AckModel;
import com.mpush.api.protocol.Packet;
import com.mpush.api.push.MsgType;
import com.mpush.api.push.PushContext;
import com.mpush.api.push.PushMsg;
import com.mpush.client.AllotClient;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class YbImClient {
    private static final String TAG = "lllll";
    private Context mContext;
//    private int selfId = 10002;
//    private String selfName = "夏老二";
//    private int otherId;
//    private String otherName;
    private String uniqueId; //唯一识别ID
    private int relationType;
    private static String url;
    private OnSendMsgListener onSendMsgListener;
    private static  YbImClient ybImClient;
    private String fileSize;//声音文件大小
    private BaseMessage tempVoiceMsg;//声音的
    private BaseMessage tempImgMsg;//图片的
    private String msgImgPath = null;
    private BaseMessage tempLocationMsg;//地址

    private OSSUploadFileConfig ossUploadFileConfig;

    public static YbImClient getInstance(){
        if(ybImClient == null){
            ybImClient = new YbImClient();
        }
        return ybImClient;
    }

    public void init(Context context, String appKey){
        this.mContext = context;
        ossUploadFileConfig = OSSUploadFileConfig.getInstance((Activity) context);
        SharedUtils.putString(context, Global.APP_IMEI, AppUtils.getid(context));
        SharedUtils.putToken(context,"zs_aca291b62baedb2ce59ca87b7bbe9aaf");
        MPushEngine.getInstance(context).initPush();
    }

    //发送消息
    public  void pushTextMsg(int selfId,String selfName, String content,int otherId,String otherName,OnSendMsgListener listener){
        this.onSendMsgListener = listener;
        uniqueId = selfId + "and" + otherId;
        addTextMessage(content,selfId,selfName,otherId,otherName);
    }
    //发送语音
    public void pushVoiceMsg(String voicePath,int size,int selfId,String selfName,int otherId,String otherName,OnSendMsgListener listener){
        this.onSendMsgListener = listener;
        uniqueId = selfId + "and" + otherId;
        upLoadVoice(voicePath,selfId,selfName,otherId,otherName,size);
    }
    //发送图片
    public void pushImgMsg(LocalMedia localMedia,int selfId,String selfName,int otherId,String otherName,OnSendMsgListener listener){
        this.onSendMsgListener = listener;
        uniqueId = selfId + "and" + otherId;
        upLoadImage(localMedia,selfId,selfName,otherId,otherName);
    }

    //发送名片
    public void pushCardMsg(String did, String url, String name,int selfId,String selfName,int otherId,String otherName,OnSendMsgListener listener){
        this.onSendMsgListener = listener;
        uniqueId = selfId + "and" + otherId;
        addCardMessage(did,url,name,selfId,selfName,otherId,otherName);
    }
    //发送地址
    public void pushLocationMsg(String filePath, LatLonPoint point, String address,int selfId,String selfName,int otherId,String otherName,OnSendMsgListener listener){
        this.onSendMsgListener = listener;
        uniqueId = selfId + "and" + otherId;
        addLocationMessage(filePath,point,address,selfId,selfName,otherId,otherName);
    }

    //发送状态接口
    public interface OnSendMsgListener{
        abstract void sendMsgSuc(BaseMessage message);
        abstract void sendMsgError(String errMsg);
    }

    /**
     * 发送普通文本消息
     * @param content 发送内容
     * @param otherId 对方id
     * @param otherName 对方姓名
     */
    public void addTextMessage(String content,int selfId,String selfName,int otherId,String otherName) {
        long timeStame = DateUtils.getTimeStame();
        BaseMessage textMessage = MPushMessageFactory.Factory.buildTextMessage(BaseMessage.C_TYPE_ONLY,
                selfId, selfName, 1, timeStame, otherId, otherName, content, 0);
        //adapter.addData(textMessage);
        Message msg = new Message();
        msg.what = 1;
        //handler.sendMessage(msg);
        textMessage.setUniqueId(uniqueId);
        DaoUtils.getInstance(App.getMContext()).getMessageManager().insertMessageEntity(BaseMessage.convertMessageEntity(textMessage));
        sendMessage(textMessage,selfId);
        //etContent.setText(null);
    }

    /**
     *发送语音
     * @param
     */
    public void upLoadVoice(String voicePath,int selfId,String selfName,int otherId,String otherName, int size) {
        long timeStame = DateUtils.getTimeStame();
        List<UpLoadImgInfo> voice = new ArrayList<>();

        File file = new File(voicePath);
        DecimalFormat df = new DecimalFormat("#.00");
        fileSize = df.format(file.length() / 1048576);
        tempVoiceMsg = MPushMessageFactory.Factory.buildVoiceMessage(BaseMessage.C_TYPE_ONLY,
                selfId, selfName, relationType, timeStame, otherId, otherName, voicePath, fileSize, String.valueOf(size), 0);
        Logger.e("ChatActivity", "Thread====>" + Thread.currentThread().getName());

        //adapter.addData(tempVoiceMsg);
        Message msg = new Message();
        msg.what = 1;
        //handler.sendMessage(msg);
        voice.add(new UpLoadImgInfo(file, "chatvoice" + DateUtils.getTimeStame() + selfId + ".amr"));

        ossUploadFileConfig.init(voice);
        ossUploadFileConfig.setOnNetListener(new OSSUploadFileConfig.OnNetListener() {
            @Override
            public void onError() {
                if (tempVoiceMsg != null) {
                    MessageExt voiceExt = tempVoiceMsg.getExt();
                    tempVoiceMsg.setUniqueId(uniqueId);
                    DaoUtils.getInstance(mContext).getMessageManager().insertMessageEntity(BaseMessage.convertMessageEntity(tempVoiceMsg));
                }
                DaoUtils.getInstance(mContext).getMessageManager().updateMessageEntity(tempVoiceMsg.getId(), tempVoiceMsg.getId(), BaseMessage.S_TYPE_FAIL);
                tempVoiceMsg.setsType(BaseMessage.S_TYPE_FAIL);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                    }
//                });
            }
        });
        ossUploadFileConfig.setOnUploadFile(new OSSUploadFileConfig.OnUploadFile() {
            @Override
            public void onUploadFileSuccess(List<String> voiceUrl) {
                if (voiceUrl.size() > 0) {
                    String msgVoiceUrl = voiceUrl.get(0);
                    addVoiceMessage(msgVoiceUrl,selfId);
                }
            }

            @Override
            public void onUploadFileFailed(String errCode) {
                Toast.makeText(mContext,"语音上传失败, 稍后再试。",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void addVoiceMessage(String voicePath,int selfId) {
        if (tempVoiceMsg != null) {
            MessageExt voiceExt = tempVoiceMsg.getExt();
            voiceExt.url = voicePath;
            tempVoiceMsg.setUniqueId(uniqueId);
            DaoUtils.getInstance(mContext).getMessageManager().insertMessageEntity(BaseMessage.convertMessageEntity(tempVoiceMsg));
            sendMessage(tempVoiceMsg,selfId);
        }
    }

    /**
     * 发送图片
     * @param localMedia
     */
    private void upLoadImage(LocalMedia localMedia,int selfId,String selfName,int otherId,String otherName) {
        if (localMedia == null) {
            Toast.makeText(mContext,"图片上传异常",Toast.LENGTH_SHORT).show();
            return;
        }

        long timeStame = DateUtils.getTimeStame();
        List<UpLoadImgInfo> avatar = new ArrayList<>();
        File file = new File(localMedia.getCompressPath());
        if (localMedia.getWidth() == 0 || localMedia.getHeight() == 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; //为true，不返回bitmap，只返回这个bitmap的尺寸
            BitmapFactory.decodeFile(localMedia.getCompressPath(), options);
            localMedia.setWidth(options.outWidth);
            localMedia.setHeight(options.outHeight);
        }
        MessageExt messageExt = new MessageExt();
        messageExt.url = localMedia.getCompressPath();
        messageExt.width = localMedia.getWidth();
        messageExt.height = localMedia.getHeight();
        tempImgMsg = MPushMessageFactory.Factory.buildImageMessage(BaseMessage.C_TYPE_ONLY,
                selfId, selfName, relationType, timeStame, otherId, otherName
                , messageExt, 0);

        //adapter.addData(tempImgMsg);
        Message msg = new Message();
        msg.what = 1;
        //handler.sendMessage(msg);
        avatar.add(new UpLoadImgInfo(file, "chatimage_" + localMedia.getWidth() + "_" + localMedia.getHeight() + "_" + UUID.randomUUID().toString() + ".png"));
        ossUploadFileConfig.init(avatar);
        ossUploadFileConfig.setOnUploadFile(new OSSUploadFileConfig.OnUploadFile() {
            @Override
            public void onUploadFileSuccess(List<String> imageUrls) {
                if (imageUrls.size() > 0) {
                    msgImgPath = imageUrls.get(0);
                    addImageMessage(msgImgPath,selfId);
                }
            }

            @Override
            public void onUploadFileFailed(String errCode) {
                Toast.makeText(mContext,"图片上传失败, 稍后再试。",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addImageMessage(String httpUrl,int selfId) {
        MessageExt imageExt = tempImgMsg.getExt();
        imageExt.url = httpUrl;
        tempImgMsg.setUniqueId(uniqueId);
        DaoUtils.getInstance(mContext).getMessageManager().insertMessageEntity(BaseMessage.convertMessageEntity(tempImgMsg));
        sendMessage(tempImgMsg,selfId);

    }

    /**
     *
     * @param did 名片id
     * @param url 名片头像地址
     * @param name 名片名字
     */
    private void addCardMessage(String did, String url, String name,int selfId,String selfName,int otherId,String otherName) {
        long timeStame = DateUtils.getTimeStame();
        MessageExt messageExt = new MessageExt();
        messageExt.id = did;
        messageExt.url = url;
        messageExt.name = name;

        BaseMessage cardMsg = MPushMessageFactory.Factory.buildCardMessage(BaseMessage.C_TYPE_ONLY,
                selfId, selfName, relationType, timeStame, otherId, otherName
                , messageExt, 0);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                adapter.addData(dynamicMsg);
//                Message msg = new Message();
//                msg.what = 1;
//                handler.sendMessage(msg);
//            }
//        });

        cardMsg.setUniqueId(uniqueId);
        DaoUtils.getInstance(mContext).getMessageManager().insertMessageEntity(BaseMessage.convertMessageEntity(cardMsg));
        sendMessage(cardMsg,selfId);
    }

    /**
     * 发送位置
     * @param filePath 地图图片地址
     * @param point 经纬度
     * @param address 地址
     */
    private void addLocationMessage(String filePath, LatLonPoint point, String address,int selfId,String selfName,int otherId,String otherName) {
        long timeStame = DateUtils.getTimeStame();
        Logger.e("ChatActivity", "===>" + Thread.currentThread().getName());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //为true，不返回bitmap，只返回这个bitmap的尺寸
        BitmapFactory.decodeFile(filePath, options);
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        //String imageType = options.outMimeType;
        MessageExt messageExt = new MessageExt();
        messageExt.url = filePath;
        messageExt.lon = point.getLongitude();
        messageExt.lat = point.getLatitude();
        messageExt.address = address;
        messageExt.width = imgWidth;
        messageExt.height = imgHeight;
        tempLocationMsg = MPushMessageFactory.Factory.buildLocationMessage(BaseMessage.C_TYPE_ONLY,
                selfId, selfName, relationType, timeStame, otherId, otherName
                , messageExt, 0);
        //adapter.addData(tempLocationMsg);
        Message msg = new Message();
        msg.what = 1;
        //handler.sendMessage(msg);
        List<UpLoadImgInfo> locationBitmap = new ArrayList<>();
        File file = new File(filePath);
        locationBitmap.add(new UpLoadImgInfo(file, "chatimage_" + imgWidth + "_" + imgHeight + "_" + UUID.randomUUID().toString() + ".png"));
        ossUploadFileConfig.init(locationBitmap);

        ossUploadFileConfig.setOnUploadFile(new OSSUploadFileConfig.OnUploadFile() {
            @Override
            public void onUploadFileSuccess(List<String> imageUrls) {
                if (imageUrls.size() > 0) {
                    MessageExt ext = tempLocationMsg.getExt();
                    ext.url = imageUrls.get(0);
                    tempLocationMsg.setUniqueId(uniqueId);
                    DaoUtils.getInstance(mContext).getMessageManager().insertMessageEntity(BaseMessage.convertMessageEntity(tempLocationMsg));
                    sendMessage(tempLocationMsg,selfId);
                }

            }

            @Override
            public void onUploadFileFailed(String errCode) {
                Toast.makeText(mContext,"图片上传失败, 稍后再试。",Toast.LENGTH_SHORT).show();
            }
        });

    }
    //发送到服务端
    private void sendMessage(final BaseMessage message,int selfId) {
        if (!MPush.I.hasRunning()) {
            Logger.e("MPushEngine", "MPush.I.hasRunning() : false");
            boolean b = MPush.I.hasStarted();
            Logger.e(TAG, "MPush-hasStart-----" + b);
            if (b) {
                if(TextUtils.isEmpty(AllotClient.token)){
                    AllotClient.token = SharedUtils.getToken(App.getMContext());
                }
                MPush.I.pausePush();
                MPush.I.resumePush();
            } else {
                MPushEngine.getInstance(App.getMContext()).initPush();
            }
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    sendMsgAgain(message,selfId);
                }
            }.sendEmptyMessageDelayed(0,2000);
            return;
        }
        String strMsg = JSON.toJSONString(message);
        if (message.getbType() == 4) {
            MessageExt ext = message.getExt();
            url = ext.url;
        }
        Logger.e("MPushEngine", "sendJson=> : " + strMsg);
        PushMsg msg = PushMsg.build(MsgType.MESSAGE, strMsg);
        msg.setMsgId(String.valueOf(new Random().nextInt(1000)));// 这个地方是sessionId;
        final PushContext pContext = PushContext.build(msg)
                .setAckModel(AckModel.AUTO_ACK) //需返回确认
                .setUserId(String.valueOf(selfId))
                .setBroadcast(false)
                .setTimeout(15000)
                .setCallback(new AckCallback() {
                    @Override
                    public void onSuccess(Packet response) {
                        Logger.e("MPushEngine", "11111==" + Thread.currentThread().getName());
                        String result = new String(response.body);
                        Logger.e("MPushEngine", ">>> 发送成功，返回结果:" + response.toString() + "消息ID:" + result.trim());

                        DaoUtils.getInstance(App.getMContext()).getMessageManager().updateMessageEntity(message.getId(), Long.valueOf(result.trim()), BaseMessage.S_TYPE_SUCCESS);
                        message.setsType(BaseMessage.S_TYPE_SUCCESS);
                        message.setId(Long.valueOf(result.trim()));
                        onSendMsgListener.sendMsgSuc(message);
                    }

                    @Override
                    public void onTimeout(Packet request) {
                        Logger.e("MPushEngine", ">>> 发送超时 -----");
                        //Logger.e("MPushEngine", ">>> 发送超时，请求的数据为:" + request == null ? "request is null " : request.toString());
                        MessageEntity entity = DaoUtils.getInstance(App.getMContext()).getMessageManager().queryMessage(message.getId());
                        int sType = entity.getSendStatus();
                        if (sType != BaseMessage.S_TYPE_SUCCESS) {
                            DaoUtils.getInstance(App.getMContext()).getMessageManager().updateMessageEntity(message.getId(), message.getId(), BaseMessage.S_TYPE_FAIL);
                            message.setsType(BaseMessage.S_TYPE_FAIL);
                        }
                        onSendMsgListener.sendMsgError("发送超时");
                    }
                });
        new AsyncTask<Object, Integer, Void>() {
            @Override
            protected Void doInBackground(Object... objects) {
                MPush.I.sendPush(pContext);
                Log.e("ccccccc", "MPush.I.sendPush");
                return null;
            }
        }.execute();

    }
    //服务器端端断了之后的操作
    private void sendMsgAgain(final BaseMessage message,int selfId){
        if(!MPush.I.hasRunning()){
            DaoUtils.getInstance(App.getMContext()).getMessageManager().updateMessageEntity(message.getId(), message.getId(), BaseMessage.S_TYPE_FAIL);
            message.setsType(BaseMessage.S_TYPE_FAIL);
            onSendMsgListener.sendMsgError("发送超时");
            return;
        }
        String strMsg = JSON.toJSONString(message);
        if (message.getbType() == 4) {
            MessageExt ext = message.getExt();
            url = ext.url;
        }
        Logger.e("MPushEngine", "sendJson=> : " + strMsg);
        PushMsg msg = PushMsg.build(MsgType.MESSAGE, strMsg);
        msg.setMsgId(String.valueOf(new Random().nextInt(1000)));// 这个地方是sessionId;
        final PushContext pContext = PushContext.build(msg)
                .setAckModel(AckModel.AUTO_ACK) //需返回确认
                .setUserId(String.valueOf(selfId))
                .setBroadcast(false)
                .setTimeout(15000)
                .setCallback(new AckCallback() {
                    @Override
                    public void onSuccess(Packet response) {
                        Logger.e("MPushEngine", "11111==" + Thread.currentThread().getName());
                        String result = new String(response.body);
                        Logger.e("MPushEngine", ">>> 发送成功，返回结果:" + response.toString() + "消息ID:" + result.trim());

                        DaoUtils.getInstance(App.getMContext()).getMessageManager().updateMessageEntity(message.getId(), Long.valueOf(result.trim()), BaseMessage.S_TYPE_SUCCESS);
                        message.setsType(BaseMessage.S_TYPE_SUCCESS);
                        message.setId(Long.valueOf(result.trim()));
                        onSendMsgListener.sendMsgSuc(message);
                    }

                    @Override
                    public void onTimeout(Packet request) {
                        Logger.e("MPushEngine", ">>> 发送超时 -----");
                        //Logger.e("MPushEngine", ">>> 发送超时，请求的数据为:" + request == null ? "request is null " : request.toString());
                        MessageEntity entity = DaoUtils.getInstance(App.getMContext()).getMessageManager().queryMessage(message.getId());
                        int sType = entity.getSendStatus();
                        if (sType != BaseMessage.S_TYPE_SUCCESS) {
                            DaoUtils.getInstance(App.getMContext()).getMessageManager().updateMessageEntity(message.getId(), message.getId(), BaseMessage.S_TYPE_FAIL);
                            message.setsType(BaseMessage.S_TYPE_FAIL);
                        }
                        onSendMsgListener.sendMsgError("发送超时");
                    }
                });
        new AsyncTask<Object, Integer, Void>() {
            @Override
            protected Void doInBackground(Object... objects) {
                MPush.I.sendPush(pContext);
                Logger.e("MPushEngine", "MPush.I.sendPush");
                return null;
            }
        }.execute();
    }
}
