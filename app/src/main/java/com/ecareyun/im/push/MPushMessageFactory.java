package com.ecareyun.im.push;

import com.ecareyun.im.model.bean.message.BaseMessage;
import com.ecareyun.im.model.bean.message.MessageExt;
import com.mpush.api.Message;
import com.mpush.api.push.BodyType;
import com.mpush.api.push.ChatType;
import com.mpush.api.push.MessageExtContent;
import com.mpush.api.push.PushMsgDetail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MPushMessageFactory {

    public final static MPushMessageFactory Factory = new MPushMessageFactory();

    /*private PushMsgDetail createMessage(int bType, int cType, String fromId, String fromName, String toId, String toName){
        PushMsgDetail pushMsgDetail=new PushMsgDetail();
        pushMsgDetail.setId(Calendar.getInstance().getTimeInMillis());
        pushMsgDetail.setbType(bType);//消息类型
        pushMsgDetail.setcType(cType);//聊天方式 0单聊 / 1群聊
        pushMsgDetail.setfId(fromId);
        pushMsgDetail.setfNm(fromName);
        pushMsgDetail.setfRely(1);
        pushMsgDetail.settId(toId);
        pushMsgDetail.settNm(toName);
        pushMsgDetail.setsTime(Calendar.getInstance().getTimeInMillis());
        return pushMsgDetail;
    }

    public PushMsgDetail buildTxtMessage(String fromId, String fromName, String toId, String toName, String content){
        PushMsgDetail pushMsgDetail= createMessage(BodyType.TXT.getValue(), ChatType.chat.ordinal(),
                fromId, fromName, toId, toName);
        MessageExtContent extContent=new MessageExtContent();
        extContent.setTxt(content);
        pushMsgDetail.setExt(extContent);
        return pushMsgDetail;
    }*/

    private BaseMessage buildMessage(int bType, int cType, int fId, String fName, int fRely, long date, int tId,
                                     String tName, MessageExt ext, int boubleType) {
        BaseMessage baseMessage = new BaseMessage();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        baseMessage.setId(Long.valueOf(format.format(new Date())));
        baseMessage.setbType(bType);//消息类型
        baseMessage.setcType(cType);//聊天方式 0单聊 / 1群聊
        baseMessage.setfId(fId);
        baseMessage.setfNm(fName);
//        baseMessage.setSendPic(ImageUrlUtils.encryptAvatarUrl(fId));
        baseMessage.setfRely(fRely);
        baseMessage.settId(tId);
        baseMessage.settNm(tName);
//        baseMessage.setReceivePic(ImageUrlUtils.encryptAvatarUrl(tId));
        baseMessage.setBoubleType(boubleType);
        baseMessage.setsTime(Calendar.getInstance().getTimeInMillis());
        baseMessage.setsType(BaseMessage.S_TYPE_SENDING);
        baseMessage.setExt(ext);
        return baseMessage;
    }

    public BaseMessage buildTextMessage(int cType, int fromId, String fromName, int fRely, long date, int toId,
                                        String toName, String content,int boubleType) {
        MessageExt messageExt = new MessageExt();
        messageExt.setTxt(content);
        BaseMessage textMessage = buildMessage(BaseMessage.MSG_TYPE_TEXT, cType, fromId, fromName,
                fRely, date, toId, toName, messageExt,boubleType);
        return textMessage;
    }

    public BaseMessage buildImageMessage(int cType, int fromId, String fromName, int fRely, long date, int toId,
                                         String toName, MessageExt ext,int boubleType) {
        BaseMessage imageMessage = buildMessage(BaseMessage.MSG_TYPE_IMAGE, cType, fromId, fromName,
                fRely, date, toId, toName, ext,boubleType);
        return imageMessage;
    }

    public BaseMessage buildDynamicMessage(int cType, int fromId, String fromName, int fRely, long date, int toId,
                                           String toName, MessageExt ext,int boubleType) {
        BaseMessage imageMessage = buildMessage(BaseMessage.MSG_TYPE_DYNAMIC, cType, fromId, fromName,
                fRely, date, toId, toName, ext,boubleType);
        return imageMessage;
    }
    //名片
    public BaseMessage buildCardMessage(int cType, int fromId, String fromName, int fRely, long date, int toId,
                                           String toName, MessageExt ext,int boubleType) {
        BaseMessage imageMessage = buildMessage(BaseMessage.MSG_CARD, cType, fromId, fromName,
                fRely, date, toId, toName, ext,boubleType);
        return imageMessage;
    }

    public BaseMessage buildVoiceMessage(int cType, int fromId, String fromName, int fRely, long date, int toId,
                                         String toName, String url, String size, String len,int boubleType) {
        MessageExt messageExt = new MessageExt();
        messageExt.url = url;
        messageExt.size = size;
        messageExt.len = len;
        BaseMessage imageMessage = buildMessage(BaseMessage.MSG_TYPE_VOICE, cType, fromId, fromName,
                fRely, date, toId, toName, messageExt,boubleType);
        return imageMessage;
    }

    public BaseMessage buildLocationMessage(int cType, int fromId, String fromName, int fRely, long date, int toId,
                                            String toName, MessageExt ext,int boubleType) {
        BaseMessage imageMessage = buildMessage(BaseMessage.MSG_TYPE_LOCATION, cType, fromId, fromName,
                fRely, date, toId, toName, ext,boubleType);
        return imageMessage;
    }

    //邀请码
    public BaseMessage buildYQMMessage(int cType, int fromId, String fromName, int fRely, long date, int toId,
                                        String toName, MessageExt ext,int boubleType) {
        BaseMessage imageMessage = buildMessage(BaseMessage.MSG_YQM, cType, fromId, fromName,
                fRely, date, toId, toName, ext,boubleType);
        return imageMessage;
    }

}
