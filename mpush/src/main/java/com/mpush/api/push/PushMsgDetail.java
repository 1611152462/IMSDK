package com.mpush.api.push;

/**
 * @ClassName PushMsgDetail
 * @Description 消息实体
 * @Author PC
 * @Date 2019/5/28 11:44
 * @Version 1.0
 **/
public final class PushMsgDetail {
    private long id;//消息ID
    private int bType;//消息类型
    private int cType;//聊天方式
    private String fId;//发起方用户ID
    private String fNm;//发起方姓名
    private int fRely;//双方关系  (1好友，2关注，3粉丝，9陌生人)
    private String tId;//接收方ID
    private String tNm;///接收方名字
    private long sTime;//发送时间
    private PushMsgDetailExt ext;//扩展字段

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

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
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

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
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

    public PushMsgDetailExt getExt() {
        return ext;
    }

    public void setExt(PushMsgDetailExt ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "PushMsgDetail{" +
                "id=" + id +
                ", bType=" + bType +
                ", cType=" + cType +
                ", fId='" + fId + '\'' +
                ", fNm='" + fNm + '\'' +
                ", fRely='" + fRely + '\'' +
                ", tId='" + tId + '\'' +
                ", tNm='" + tNm + '\'' +
                ", sTime=" + sTime +
                ", ext=" + ext +
                '}';
    }
}
