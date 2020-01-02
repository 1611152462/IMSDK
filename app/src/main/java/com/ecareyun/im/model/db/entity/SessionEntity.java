package com.ecareyun.im.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class SessionEntity {

    @Id(autoincrement = true)
    private Long sId;
    private int relationType;//归属关系1好友2关注3粉丝9陌生人0群聊
    private int chatType;//聊天类型0单聊1群聊
    private long messageId;
    @Unique
    private int receiveId;//聊天对象ID
    private String receiveName;//聊天对象名字
    private String receivePic;//聊天对象头像
    private int messageType;//消息类型1文本2普通图片3引导图片4音频5位置6视频
    private long sendData;//发送消息时间
    private String content;//内容
    private int sendStatus;//消息发送状态0失败1成功
    private int unReadNum;//未读数
    private int extType;//引导类型
    private byte showTop;//1:置顶；0:非置顶
    private String uniqueId;//唯一ID
    public String getUniqueId() {
        return this.uniqueId;
    }
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public byte getShowTop() {
        return this.showTop;
    }
    public void setShowTop(byte showTop) {
        this.showTop = showTop;
    }
    public int getExtType() {
        return this.extType;
    }
    public void setExtType(int extType) {
        this.extType = extType;
    }
    public int getUnReadNum() {
        return this.unReadNum;
    }
    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }
    public int getSendStatus() {
        return this.sendStatus;
    }
    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getSendData() {
        return this.sendData;
    }
    public void setSendData(long sendData) {
        this.sendData = sendData;
    }
    public int getMessageType() {
        return this.messageType;
    }
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    public String getReceivePic() {
        return this.receivePic;
    }
    public void setReceivePic(String receivePic) {
        this.receivePic = receivePic;
    }
    public String getReceiveName() {
        return this.receiveName;
    }
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
    public int getReceiveId() {
        return this.receiveId;
    }
    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }
    public long getMessageId() {
        return this.messageId;
    }
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
    public int getChatType() {
        return this.chatType;
    }
    public void setChatType(int chatType) {
        this.chatType = chatType;
    }
    public int getRelationType() {
        return this.relationType;
    }
    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }
    public Long getSId() {
        return this.sId;
    }
    public void setSId(Long sId) {
        this.sId = sId;
    }

    @Generated(hash = 2111651576)
    public SessionEntity(Long sId, int relationType, int chatType, long messageId,
            int receiveId, String receiveName, String receivePic, int messageType,
            long sendData, String content, int sendStatus, int unReadNum,
            int extType, byte showTop, String uniqueId) {
        this.sId = sId;
        this.relationType = relationType;
        this.chatType = chatType;
        this.messageId = messageId;
        this.receiveId = receiveId;
        this.receiveName = receiveName;
        this.receivePic = receivePic;
        this.messageType = messageType;
        this.sendData = sendData;
        this.content = content;
        this.sendStatus = sendStatus;
        this.unReadNum = unReadNum;
        this.extType = extType;
        this.showTop = showTop;
        this.uniqueId = uniqueId;
    }
    @Generated(hash = 1440899673)
    public SessionEntity() {
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "sId=" + sId +
                ", relationType=" + relationType +
                ", chatType=" + chatType +
                ", messageId=" + messageId +
                ", receiveId=" + receiveId +
                ", receiveName='" + receiveName + '\'' +
                ", receivePic='" + receivePic + '\'' +
                ", messageType=" + messageType +
                ", sendData=" + sendData +
                ", content='" + content + '\'' +
                ", sendStatus=" + sendStatus +
                ", unReadNum=" + unReadNum +
                ", extType=" + extType +
                ", showTop=" + showTop +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
