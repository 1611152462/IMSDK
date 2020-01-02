package com.ecareyun.im.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MessageEntity {
    @Id(autoincrement = true)
    private Long id;//这个msgID是表字段自增长ID
    @Unique
    private long msgId;//这个ID是先在本地按时间戳生成的ID，消息发送成功后改成IM系统返回的ID
    private int messageType;//消息类型1文本2普通图片3引导图片4音频5位置6视频
    private int chatType;//聊天方式0单聊1群聊
    private String sendId;//发起方用户ID
    private String sendName;//发起方姓名
    private String sendPic;//发起方头像
    private int relationType;//双方关系  (1好友，2关注，3粉丝，9陌生人)
    private String receiveId;//接收方ID
    private String receiveName;///接收方名字
    private String receivePic;///接收方头像
    private int boubleType;///气泡类型0发送方1接收方
    private long sendDate;//发送时间
    private String expandMsg;//扩展字段
    private String content;//消息内容
    private int sendStatus;//发送状态（0发送失败，1发送成功）
    private byte isRead;//是否已读状态（0未读，1已读）
    @Index
    private String uniqueId;//唯一ID
    public String getUniqueId() {
        return this.uniqueId;
    }
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public byte getIsRead() {
        return this.isRead;
    }
    public void setIsRead(byte isRead) {
        this.isRead = isRead;
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
    public String getExpandMsg() {
        return this.expandMsg;
    }
    public void setExpandMsg(String expandMsg) {
        this.expandMsg = expandMsg;
    }
    public long getSendDate() {
        return this.sendDate;
    }
    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }
    public int getBoubleType() {
        return this.boubleType;
    }
    public void setBoubleType(int boubleType) {
        this.boubleType = boubleType;
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
    public String getReceiveId() {
        return this.receiveId;
    }
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
    public int getRelationType() {
        return this.relationType;
    }
    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }
    public String getSendPic() {
        return this.sendPic;
    }
    public void setSendPic(String sendPic) {
        this.sendPic = sendPic;
    }
    public String getSendName() {
        return this.sendName;
    }
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }
    public String getSendId() {
        return this.sendId;
    }
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
    public int getChatType() {
        return this.chatType;
    }
    public void setChatType(int chatType) {
        this.chatType = chatType;
    }
    public int getMessageType() {
        return this.messageType;
    }
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    public long getMsgId() {
        return this.msgId;
    }
    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2135422578)
    public MessageEntity(Long id, long msgId, int messageType, int chatType,
            String sendId, String sendName, String sendPic, int relationType,
            String receiveId, String receiveName, String receivePic,
            int boubleType, long sendDate, String expandMsg, String content,
            int sendStatus, byte isRead, String uniqueId) {
        this.id = id;
        this.msgId = msgId;
        this.messageType = messageType;
        this.chatType = chatType;
        this.sendId = sendId;
        this.sendName = sendName;
        this.sendPic = sendPic;
        this.relationType = relationType;
        this.receiveId = receiveId;
        this.receiveName = receiveName;
        this.receivePic = receivePic;
        this.boubleType = boubleType;
        this.sendDate = sendDate;
        this.expandMsg = expandMsg;
        this.content = content;
        this.sendStatus = sendStatus;
        this.isRead = isRead;
        this.uniqueId = uniqueId;
    }
    @Generated(hash = 1797882234)
    public MessageEntity() {
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", msgId=" + msgId +
                ", messageType=" + messageType +
                ", chatType=" + chatType +
                ", sendId='" + sendId + '\'' +
                ", sendName='" + sendName + '\'' +
                ", sendPic='" + sendPic + '\'' +
                ", relationType=" + relationType +
                ", receiveId='" + receiveId + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", receivePic='" + receivePic + '\'' +
                ", boubleType=" + boubleType +
                ", sendDate=" + sendDate +
                ", expandMsg='" + expandMsg + '\'' +
                ", content='" + content + '\'' +
                ", sendStatus=" + sendStatus +
                ", isRead=" + isRead +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
