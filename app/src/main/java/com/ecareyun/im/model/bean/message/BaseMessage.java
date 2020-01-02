package com.ecareyun.im.model.bean.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecareyun.im.model.db.entity.MessageEntity;
import com.example.mylibrary.utils.SharedUtils;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseMessage implements Serializable {

    /**
     * 文本
     */
    public static final int MSG_TYPE_TEXT = 1;
    /**
     * 图片
     */
    public static final int MSG_TYPE_IMAGE = 2;
    /**
     * 动态/引导图片
     */
    public static final int MSG_TYPE_DYNAMIC = 3;
    /**
     * 音频
     */
    public static final int MSG_TYPE_VOICE = 4;
    /**
     * 位置
     */
    public static final int MSG_TYPE_LOCATION = 5;

    /**
     * 视频
     */
    public static final int MSG_TYPE_VIDEO = 6;
    /**
     * 群操作提示
     */
    public static final int MSG_OPTIONTS = 8;
    /**
     * 名片
     */
    public static final int MSG_CARD = 9;
    /**
     * 邀请码
     */
    public static final int MSG_YQM = 10;

    public static final int RELY_FRIEND = 1;    //好友
    public static final int RELY_GROUP = 0;    //群聊
    public static final int RELY_ATTENTION = 2; //关注
    public static final int RELY_FANS = 3;      //粉丝
    public static final int RELY_STRANGER = 9;  //陌生人


    public static final int C_TYPE_ONLY = 0;   // 单聊
    public static final int C_TYPE_GROUP = 1;// 群聊

    public static final int S_TYPE_FAIL = 0;   // 未发送/发送失败
    public static final int S_TYPE_SUCCESS = 1;// 发送成功
    public static final int S_TYPE_SENDING = 2;// 发送中

    private long id;//消息ID
    private int bType;//消息类型
    private int cType;//聊天方式 (聊天方式 0单聊 / 1群聊)
    private int fId;//发起方用户ID
    private String fNm;//发起方姓名
    private String sendPic;//发起方头像
    private int fRely;//双方关系  (1好友，2关注，3粉丝，9陌生人)
    private int tId;//接收方ID
    private String tNm;///接收方名字
    private String receivePic;//接收方头像
    private int boubleType;//气泡类型0发送方1接收方
    private long sTime;//发送时间
    private MessageExt ext;//扩展字段
    private int sType;//发送状态（0发送失败，1发送成功)
    private byte isRead;//是否已读状态（0未读，1已读）
    private String uniqueId;//唯一ID

    public String getSendPic() {
        return sendPic;
    }

    public void setSendPic(String sendPic) {
        this.sendPic = sendPic;
    }

    public String getReceivePic() {
        return receivePic;
    }

    public void setReceivePic(String receivePic) {
        this.receivePic = receivePic;
    }

    public int getBoubleType() {
        return boubleType;
    }

    public void setBoubleType(int boubleType) {
        this.boubleType = boubleType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getbType() {
        return bType;
    }

    public void setbType(int bType) {
        this.bType = bType;
    }

    public int getcType() {
        return cType;
    }

    public void setcType(int cType) {
        this.cType = cType;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getfNm() {
        return fNm;
    }

    public void setfNm(String fNm) {
        this.fNm = fNm;
    }

    public int getfRely() {
        return fRely;
    }

    public void setfRely(int fRely) {
        this.fRely = fRely;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String gettNm() {
        return tNm;
    }

    public void settNm(String tNm) {
        this.tNm = tNm;
    }

    public long getsTime() {
        return sTime;
    }

    public void setsTime(long sTime) {
        this.sTime = sTime;
    }

    public MessageExt getExt() {
        return ext;
    }

    public void setExt(MessageExt ext) {
        this.ext = ext;
    }

    public int getsType() {
        return sType;
    }

    public void setsType(int sType) {
        this.sType = sType;
    }

    public byte getIsRead() {
        return isRead;
    }

    public void setIsRead(byte isRead) {
        this.isRead = isRead;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    protected JSONObject getJSONObject() {
        //JSONObject jsonObject = JSONObject.parseObject(this.ext);
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseMessage that = (BaseMessage) o;
        return id == that.id &&
                bType == that.bType &&
                cType == that.cType &&
                fId == that.fId &&
                fRely == that.fRely &&
                tId == that.tId &&
                sTime == that.sTime &&
                sType == that.sType &&
                isRead == that.isRead &&
                boubleType == that.boubleType &&
                fNm.equals(that.fNm) &&
                tNm.equals(that.tNm) &&
                sendPic.equals(that.sendPic) &&
                receivePic.equals(that.receivePic) &&
                uniqueId.equals(that.uniqueId) &&
                ext.equals(that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bType, cType, fId, fNm,sendPic, fRely, tId, tNm,receivePic,boubleType, sTime, ext, sType, isRead, uniqueId);
    }

    public static BaseMessage buildNotesMessage() {
        BaseMessage bm = new BaseMessage();
        bm.setbType(0);
        return bm;
    }


    public static BaseMessage convert(MessageEntity entity) {
        BaseMessage message = new BaseMessage();
        setValueMessage(message, entity);
        MessageExt ext = new Gson().fromJson(entity.getExpandMsg(), MessageExt.class);
        message.setExt(ext);
        return message;
    }

    private static void setValueMessage(BaseMessage message, MessageEntity entity) {
        if (message != null && entity != null) {
            message.setId(entity.getMsgId());
            message.setbType(entity.getMessageType());
            message.setcType(entity.getChatType());
            message.setfId(Integer.valueOf(entity.getSendId()));
            message.setfNm(entity.getSendName());
            message.setSendPic(entity.getSendPic());
            message.setfRely(entity.getRelationType());
            message.settId(Integer.valueOf(entity.getReceiveId()));
            message.settNm(entity.getReceiveName());
            message.setReceivePic(entity.getReceivePic());
            message.setBoubleType(entity.getBoubleType());
            message.setsTime(entity.getSendDate());
            message.setsType(entity.getSendStatus());
            message.setIsRead(entity.getIsRead());
            message.setUniqueId(entity.getUniqueId());
        }
    }

    public static List<BaseMessage> convertList(List<MessageEntity> list) {
        List<BaseMessage> msgList = new ArrayList<>();
        if (list != null) {
            for (MessageEntity entity : list) {
                msgList.add(convert(entity));

            }
        }
        return msgList;
    }

    public static MessageEntity convertMessageEntity(BaseMessage tm) {
        MessageEntity entity = new MessageEntity();
        entity.setId(null);
        entity.setMsgId(tm.getId());
        entity.setMessageType(tm.getbType());
        entity.setChatType(tm.getcType());
        entity.setSendId(String.valueOf(tm.getfId()));
        entity.setSendName(tm.getfNm());
        entity.setSendPic(tm.getSendPic());
        entity.setRelationType(tm.getfRely());
        entity.setReceiveId(String.valueOf(tm.gettId()));
        entity.setReceiveName(tm.gettNm());
        entity.setReceivePic(tm.getReceivePic());
        entity.setBoubleType(tm.getBoubleType());
        entity.setSendDate(tm.getsTime());
        entity.setSendStatus(tm.getsType());
        entity.setIsRead(tm.getIsRead());
        entity.setUniqueId(tm.getUniqueId());
        entity.setContent(tm.getExt().getTxt());
        entity.setExpandMsg(JSON.toJSONString(tm.getExt()));
//        Log.e("aaaaa",tm.getExt().txt);
        return entity;
    }

    public String getLabelText() {
        String lable = null;
        if (this.getbType() == BaseMessage.MSG_TYPE_TEXT) {
            lable = this.getExt().getTxt();
        } else {
            if (this.getbType() == BaseMessage.MSG_TYPE_IMAGE) {
                lable = "[图片]";
            } else if (this.getbType() == BaseMessage.MSG_TYPE_DYNAMIC) {
                lable = "[动态]";
            } else if (this.getbType() == BaseMessage.MSG_TYPE_VOICE) {
                lable = "[语音]";
            } else if (this.getbType() == BaseMessage.MSG_TYPE_LOCATION) {
                lable = "[位置]";
            }else if (this.getbType() == BaseMessage.MSG_OPTIONTS) {
//                switch (this.getExt().gType){
//                    case 0:
//                        String resadd = "";
//                        StringBuffer sbadd = new StringBuffer();
//                        if(StringUtil.isNullOrEmpty(this.getfNm())) {
//                            sbadd.append("你邀请");
//                        }else{
//                            String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getfId() + "");
//                            String showName = StringUtil.isNullOrEmpty(noteName)?this.getfNm():noteName;
//                            sbadd.append("\""+showName+"\"邀请");
//                        }
//                        if(null != this.getExt().members && this.getExt().members.size()>0) {
//                            for (int i = 0; i < this.getExt().members.size();i++){
//                                String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getExt().members.get(i).id + "");
//                                String showName = StringUtil.isNullOrEmpty(noteName)?this.getExt().members.get(i).name:noteName;
//                                if(this.getfId() != this.getExt().members.get(i).id)
//                                    sbadd.append(this.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                            }
//                            resadd = sbadd.toString();
//                        }
//                        if(!StringUtil.isNullOrEmpty(resadd)) {
//                            String showRes = resadd.substring(0, resadd.length() - 1);
//                            lable = showRes + "加入群聊";
//                        }
//                        break;
//                    case 1:
//                        if(null != this) {
//                            String resyq = "";
//                            StringBuffer sbyq = new StringBuffer();
//                            String noteName1 = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getfId() + "");
//                            String showName1 = StringUtil.isNullOrEmpty(noteName1)?this.getfNm():noteName1;
//                            sbyq.append("\"" + showName1 + "\"邀请");
//                            if (null != this.getExt()) {
//                                if (null != this.getExt().members && this.getExt().members.size() > 0) {
////                                    lable = "“" + this.getfNm() + "“想邀请" + this.getExt().members.size() + "位朋友加入群聊";
//                                    for (int i = 0;i < this.getExt().members.size();i++){
//                                        String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getExt().members.get(i).id + "");
//                                        String showName = StringUtil.isNullOrEmpty(noteName)?this.getExt().members.get(i).name:noteName;
//                                        sbyq.append("\""+showName+"\"、");
//                                    }
//                                    resyq = sbyq.toString();
//                                }
//                                if(!StringUtil.isNullOrEmpty(resyq)) {
//                                    String showRes = resyq.substring(0, resyq.length() - 1);
//                                    lable = showRes + "加入群聊，请确认";
//                                }
//                            }
//                        }
//                        break;
//                    case 2:
//                        String resdel = "";
//                        StringBuffer sbdel = new StringBuffer();
//                        if(StringUtil.isNullOrEmpty(this.getfNm())) {
//                            sbdel.append("你将");
//                        }else{
//                            String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getfId() + "");
//                            String showName = StringUtil.isNullOrEmpty(noteName)?this.getfNm():noteName;
//                            sbdel.append("\""+showName+"\"将");
//                        }
//
//                        if(null != this.getExt().members && this.getExt().members.size()>0) {
//                            for (int i = 0; i < this.getExt().members.size();i++){
//                                String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getExt().members.get(i).id + "");
//                                String showName = StringUtil.isNullOrEmpty(noteName)?this.getExt().members.get(i).name:noteName;
//                                sbdel.append(this.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                            }
//                            resdel = sbdel.toString();
//                        }
//                        if(!StringUtil.isNullOrEmpty(resdel)) {
//                            String showResdel = resdel.substring(0, resdel.length() - 1);
//                            lable = showResdel + "移出群聊";
//                        }
//                        break;
//                    case 3:
//                        if(this.getfId()== Api.UID){
//                            if(null != this)
//                                lable = "你修改群名为\""+this.getExt().gName+"\"";
//                        }else{
//                            if(null != this) {//msg.gettNm()
//                                String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getfId() + "");
//                                String showName = StringUtil.isNullOrEmpty(noteName) ? this.getfNm() : noteName;
//                                lable = "\"" + showName + "\"将群名称修改为\"" + this.getExt().gName + "\"";
//                            }
//                        }
//                        break;
//                    case 4:
//                        String resChange = "";
//                        StringBuffer sbChange = new StringBuffer();
//                        if(null != this.getExt().members && this.getExt().members.size()>0) {
//                            for (int i = 0; i < this.getExt().members.size();i++){
//                                sbChange.append(this.getExt().members.get(i).name);
//                            }
//                            resChange = sbChange.toString();
//                            if(StringUtil.isNullOrEmpty(this.getfNm())){
//                                lable = "你已将群主转让给了\""+resChange+"\"";
//                            }else {
//                                if (this.getExt().members.get(0).id == Api.UID) {
//                                    lable = "你已成为新群主";
//                                } else {
//                                    lable = "\"" + resChange + "\"已成为新群主";
//                                }
//                            }
//                        }
//
//                        break;
//                    case 5:
//                        if(StringUtil.isNullOrEmpty(this.getfNm())) {
//                            lable = "你已开启入群验证";
//                        }else{
//                            lable = "群主已开启入群验证";
//                        }
//                        break;
//                    case 6:
//                        if(StringUtil.isNullOrEmpty(this.getfNm())) {
//                            lable = "你已关闭入群验证";
//                        }else{
//                            lable = "群主已关闭入群验证";
//                        }
//                        break;
//                    case 7:
//                        String resagree = "";
//                        StringBuffer sbagree = new StringBuffer();
//                        if(StringUtil.isNullOrEmpty(this.getfNm())) {
//                            sbagree.append("你已同意");
//                        }else{
//
//                        }
//                        if(null != this.getExt().members && this.getExt().members.size()>0) {
//                            for (int i = 0; i < this.getExt().members.size();i++){
//                                if(!StringUtil.isNullOrEmpty(this.getExt().members.get(i).originName)){
//                                    String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getExt().members.get(i).originId + "");
//                                    String showName = StringUtil.isNullOrEmpty(noteName)?this.getExt().members.get(i).originName:noteName;
//                                    sbagree.append(Integer.parseInt(this.getExt().members.get(i).originId) == Api.UID?"你邀请的":showName+"邀请");
//                                }
//                                String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), this.getExt().members.get(i).id + "");
//                                String showName = StringUtil.isNullOrEmpty(noteName)?this.getExt().members.get(i).name:noteName;
//
//                                sbagree.append(this.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                            }
//                            resagree = sbagree.toString();
//                        }
//                        if(!StringUtil.isNullOrEmpty(resagree)) {
//                            String showResagree = resagree.substring(0, sbagree.length() - 1);
//                            if (StringUtil.isNullOrEmpty(this.getfNm())) {
//                                lable = showResagree + "加入群聊";
//                            } else {
//                                lable = showResagree + "已加入群聊";
//                            }
//                        }else{
//                            lable = resagree + "已加入群聊";
//                        }
//
//                        break;
//                }
            }else if (this.getbType() == BaseMessage.MSG_YQM) {
                lable = "[邀请码]";
            }else if (this.getbType() == BaseMessage.MSG_CARD) {
                lable = "[名片]";
            }else {
                lable = "[未知]";
            }
        }
        return lable;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "id=" + id +
                ", bType=" + bType +
                ", cType=" + cType +
                ", fId=" + fId +
                ", fNm='" + fNm + '\'' +
                ", sendPic='" + sendPic + '\'' +
                ", fRely=" + fRely +
                ", tId=" + tId +
                ", tNm='" + tNm + '\'' +
                ", receivePic='" + receivePic + '\'' +
                ", boubleType=" + boubleType +
                ", sTime=" + sTime +
                ", ext=" + ext +
                ", sType=" + sType +
                ", isRead=" + isRead +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
