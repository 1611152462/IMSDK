package com.ecareyun.im.model.bean.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecareyun.im.model.db.entity.MessageEntity;
import com.ecareyun.im.model.db.manager.DaoUtils;
import com.example.mylibrary.utils.SharedUtils;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupBaseMessage implements Serializable {

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
    public static final int MSG_OPTIONTS = 7;
    /**
     * 名片
     */
    public static final int MSG_OARD = 8;

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

    private List<Body> body;
    private int bodyLen;//消息类型
    private int boubleType;//气泡类型0发送方1接收方
    private String gId;//群id
    private long eTime;//发送时间
    private String gName;//群名字
    private long id;
    private int flag;//1指的是离线消息0是在线的
    private int seq;//离线消息中0指的是第一组
    private int sType;//发送状态（0发送失败，1发送成功)
    private byte isRead;//是否已读状态（0未读，1已读）

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }

    public int getBodyLen() {
        return bodyLen;
    }

    public void setBodyLen(int bodyLen) {
        this.bodyLen = bodyLen;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public long geteTime() {
        return eTime;
    }

    public void seteTime(long eTime) {
        this.eTime = eTime;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
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


    protected JSONObject getJSONObject() {
        //JSONObject jsonObject = JSONObject.parseObject(this.ext);
        return null;
    }

    public class Body implements Serializable{
        private int bType;//消息类型
        private MessageExt ext;//扩展字段
        private String fId;//发起方用户ID
        private String fNm;//发起方姓名
        private long msgId;//消息id
        private long sTime;//发送时间
        private String uniqueId;//唯一ID

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public int getbType() {
            return bType;
        }

        public void setbType(int bType) {
            this.bType = bType;
        }

        public MessageExt getExt() {
            return ext;
        }

        public void setExt(MessageExt ext) {
            this.ext = ext;
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

        public long getMsgId() {
            return msgId;
        }

        public void setMsgId(long msgId) {
            this.msgId = msgId;
        }

        public long getsTime() {
            return sTime;
        }

        public void setsTime(long sTime) {
            this.sTime = sTime;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Body that = (Body) obj;
            return bType == that.bType &&
                    msgId == that.msgId &&
                    sTime == that.sTime &&
                    ext.equals(that.ext) &&
                    fId.equals(that.fId) &&
                    uniqueId.equals(that.uniqueId) &&
                    fNm.equals(that.fNm);
        }

        @Override
        public String toString() {
            return "Body{" +
                    "bType=" + bType +
                    ", ext=" + ext +
                    ", fId='" + fId + '\'' +
                    ", fNm='" + fNm + '\'' +
                    ", msgId=" + msgId +
                    ", sTime=" + sTime +
                    ", uniqueId='" + uniqueId + '\'' +
                    '}';
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupBaseMessage that = (GroupBaseMessage) o;
        return id == that.id &&
                bodyLen == that.bodyLen &&
                eTime == that.eTime &&
                sType == that.sType &&
                flag == that.flag &&
                seq == that.seq &&
                isRead == that.isRead &&
                boubleType == that.boubleType &&
                gId.equals(that.gId) &&
                gName.equals(that.gName) &&
                body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, bodyLen, eTime, gId, gName,id, id,boubleType, sType, isRead);
    }

//    public static GroupBaseMessage buildNotesMessage() {
//        GroupBaseMessage bm = new GroupBaseMessage();
//        bm.setbType(0);
//        return bm;
//    }


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

    public static List<MessageEntity> convertListMessageEntity(GroupBaseMessage tm) {
        List<MessageEntity> list = new ArrayList<>();
        for(Body bodyitme:tm.body){
            MessageEntity entity = new MessageEntity();
            entity.setId(null);
            entity.setMsgId(bodyitme.getMsgId());
            entity.setMessageType(bodyitme.getbType());
            entity.setChatType(1);
            entity.setSendId(String.valueOf(bodyitme.getfId()));
            entity.setSendName(bodyitme.getfNm());
            entity.setSendPic("");
            entity.setRelationType(RELY_GROUP);
            entity.setReceiveId(tm.getgId());
            //entity.setReceiveName(StringUtil.isNullOrEmpty(bodyitme.getExt().gName)?tm.getgName():bodyitme.getExt().gName);
            entity.setReceivePic("");
            entity.setBoubleType(tm.getBoubleType());
            entity.setSendDate(bodyitme.getsTime());
            entity.setSendStatus(tm.getsType());
            entity.setIsRead(tm.getIsRead());
            entity.setUniqueId(bodyitme.getUniqueId());
            entity.setContent(bodyitme.getExt().getTxt());
            entity.setExpandMsg(JSON.toJSONString(bodyitme.getExt()));

            list.add(entity);
        }
        return list;
    }

    public String getLabelText() {
        String lable = null;

        switch (this.getFlag()){
            case 1:
                if (this.getBody().get(this.getBodyLen()-1).getbType() == GroupBaseMessage.MSG_TYPE_TEXT) {
                    switch (this.getFlag()){
                        case 1:
                            lable = this.getBody().get(0).getExt().getTxt();
                            break;
                        default:
                            lable = this.getBody().get(this.getBodyLen()-1).getExt().getTxt();
                    }
                } else if (this.getBody().get(0).getbType() == GroupBaseMessage.MSG_TYPE_IMAGE) {
                        lable = "[图片]";
                    } else if (this.getBody().get(0).getbType() == GroupBaseMessage.MSG_TYPE_DYNAMIC) {
                        lable = "[动态]";
                    } else if (this.getBody().get(0).getbType() == GroupBaseMessage.MSG_TYPE_VOICE) {
                        lable = "[语音]";
                    } else if (this.getBody().get(0).getbType() == GroupBaseMessage.MSG_TYPE_LOCATION) {
                        lable = "[位置]";
                    }  else if (this.getBody().get(0).getbType() == BaseMessage.MSG_OPTIONTS) {
//                        List<MessageEntity> list = DaoUtils.getInstance(MyApp.getInstance().getContext()).getMessageManager().queryMessageList(this.getBody().get(this.getBodyLen()-1).getUniqueId(), 10, 0);
//                        List<BaseMessage> msgList = BaseMessage.convertList(list);
//                        BaseMessage msg = msgList.get(msgList.size()-1);
//                        switch (msg.getExt().gType){
//                            case 0:
//                                String resadd = "";
//                                StringBuffer sbadd = new StringBuffer();
//                                if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                    sbadd.append("你邀请");
//                                }else{
//                                    String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                    String showName = StringUtil.isNullOrEmpty(noteName)?msg.getfNm():noteName;
//                                    sbadd.append("\""+showName+"\"邀请");
//                                }
//                                if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                    for (int i = 0; i < msg.getExt().members.size();i++){
//
//                                        String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                        String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//
//
//                                        if(msg.getfId() != msg.getExt().members.get(i).id)
//                                            sbadd.append(msg.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                                    }
//                                    resadd = sbadd.toString();
//                                }
//                                if(!StringUtil.isNullOrEmpty(resadd)) {
//                                    String showRes = resadd.substring(0, resadd.length() - 1);
//                                    lable = showRes + "加入了群聊";
//                                }
//                                break;
//                            case 1:
//                                if(null != msg) {
//                                    String resyq = "";
//                                    StringBuffer sbyq = new StringBuffer();
//                                    String noteName1 = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                    String showName1 = StringUtil.isNullOrEmpty(noteName1)?msg.getfNm():noteName1;
//                                    sbyq.append("\"" + showName1 + "\"邀请");
//                                    if (null != msg.getExt()) {
//                                        if (null != msg.getExt().members && msg.getExt().members.size() > 0) {
//                                            for (int i = 0;i < msg.getExt().members.size();i++){
//                                                String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                                String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//                                                sbyq.append("\""+showName+"\"、");
//                                            }
//                                            resyq = sbyq.toString();
//                                        }
//                                        if(!StringUtil.isNullOrEmpty(resyq)) {
//                                            String showRes = resyq.substring(0, resyq.length() - 1);
//                                            lable = showRes + "加入群聊，请确认";
//                                        }
//                                    }
//                                }
//                                break;
//                            case 2:
//                                String resdel = "";
//                                StringBuffer sbdel = new StringBuffer();
//                                if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                    sbdel.append("你将");
//                                }else{
//                                    String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                    String showName = StringUtil.isNullOrEmpty(noteName)?msg.getfNm():noteName;
//                                    sbdel.append("\""+showName+"\"将");
//                                }
//                                if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                    for (int i = 0; i < msg.getExt().members.size();i++){
//                                        String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                        String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//
//                                        sbdel.append(msg.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                                    }
//                                    resdel = sbdel.toString();
//                                }
//                                if(!StringUtil.isNullOrEmpty(resdel)) {
//                                    String showResdel = resdel.substring(0, resdel.length() - 1);
//                                    lable = showResdel + "移出群聊";
//                                }
//                                break;
//                            case 3:
//                                if(msg.getfId()== Api.UID){
//                                    if(null != msg) {
//                                        lable = "你修改群名为\"" + msg.gettNm() + "\"";
//                                    }
//                                }else{
//                                    if(null != msg) {
//                                        String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                        String showName = StringUtil.isNullOrEmpty(noteName)?msg.getfNm():noteName;
//                                        lable = "\"" + showName + "\"将群名称修改为\"" + msg.gettNm() + "\"";
//
//                                    }
//                                }
//                                break;
//                            case 4:
//                                String resChange = "";
//                                StringBuffer sbChange = new StringBuffer();
//                                if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                    for (int i = 0; i < msg.getExt().members.size();i++){
//                                        sbChange.append(msg.getExt().members.get(i).name);
//                                    }
//                                    resChange = sbChange.toString();
//                                    if(null != msg) {
//                                        if (StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                            lable = "你已将群主转让给了\"" + resChange + "\"";
//                                        } else {
//                                            if (msg.getExt().members.get(0).id == Api.UID) {
//                                                lable = "你已成为新群主";
//                                            } else {
//                                                lable = "\"" + resChange + "\"已成为新群主";
//                                            }
//                                        }
//                                    }
//                                }
//
//                                break;
//                            case 5:
//                                if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                    lable = "你已开启入群验证";
//                                }else{
//                                    lable = "群主已开启入群验证";
//                                }
//                                break;
//                            case 6:
//                                if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                    lable = "你已关闭入群验证";
//                                }else{
//                                    lable = "群主已关闭入群验证";
//                                }
//                                break;
//                            case 7:
//                                String resagree = "";
//                                StringBuffer sbagree = new StringBuffer();
//                                if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                    sbagree.append("你已同意");
//                                }
//                                if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                    for (int i = 0; i < msg.getExt().members.size();i++){
//                                        if(!StringUtil.isNullOrEmpty(msg.getExt().members.get(i).originName)){
//                                            String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).originId + "");
//                                            String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).originName:noteName;
//                                            sbagree.append(Integer.parseInt(msg.getExt().members.get(i).originId) == Api.UID?"你邀请的":showName+"邀请");
//                                        }
//                                        String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                        String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//                                        sbagree.append(msg.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                                    }
//                                    resagree = sbagree.toString();
//                                }
//                                if(!StringUtil.isNullOrEmpty(resagree)) {
//                                    String showResagree = resagree.substring(0, sbagree.length() - 1);
//                                    if (StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                        lable = showResagree + "加入群聊";
//                                    } else {
//                                        lable = showResagree + "已加入群聊";
//                                    }
//                                }else{
//                                    lable = resagree + "已加入群聊";
//                                }
//                                break;
//                        }
                    }else if (this.getBody().get(0).getbType() == BaseMessage.MSG_CARD) {
                        lable = "[名片]";
                    }else if (this.getBody().get(0).getbType() == BaseMessage.MSG_YQM) {
                        lable = "[邀请码]";
                    }else {
                        lable = "[未知]";
                    }
                break;
                default:
                    if (this.getBody().get(this.getBodyLen()-1).getbType() == GroupBaseMessage.MSG_TYPE_TEXT) {
                        switch (this.getFlag()){
                            case 1:
                                lable = this.getBody().get(0).getExt().getTxt();
                                break;
                            default:
                                lable = this.getBody().get(this.getBodyLen()-1).getExt().getTxt();
                        }
                    } else if (this.getBody().get(this.getBodyLen()-1).getbType() == GroupBaseMessage.MSG_TYPE_IMAGE) {
                            lable = "[图片]";
                        } else if (this.getBody().get(this.getBodyLen()-1).getbType() == GroupBaseMessage.MSG_TYPE_DYNAMIC) {
                            lable = "[动态]";
                        } else if (this.getBody().get(this.getBodyLen()-1).getbType() == GroupBaseMessage.MSG_TYPE_VOICE) {
                            lable = "[语音]";
                        } else if (this.getBody().get(this.getBodyLen()-1).getbType() == GroupBaseMessage.MSG_TYPE_LOCATION) {
                            lable = "[位置]";
                        }  else if (this.getBody().get(this.getBodyLen()-1).getbType() == BaseMessage.MSG_OPTIONTS) {
//                            List<MessageEntity> list = DaoUtils.getInstance(MyApp.getInstance().getContext()).getMessageManager().queryMessageList(this.getBody().get(this.getBodyLen()-1).getUniqueId(), 10, 0);
//                            List<BaseMessage> msgList = BaseMessage.convertList(list);
//                            BaseMessage msg = msgList.get(msgList.size()-1);
//                            switch (msg.getExt().gType){
//                                case 0:
//                                    String resadd = "";
//                                    StringBuffer sbadd = new StringBuffer();
//                                    if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                        sbadd.append("你邀请");
//                                    }else{
//                                        String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                        String showName = StringUtil.isNullOrEmpty(noteName)?msg.getfNm():noteName;
//                                        sbadd.append("\""+showName+"\"邀请");
//                                    }
//                                    if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                        for (int i = 0; i < msg.getExt().members.size();i++){
//
//                                            String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                            String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//
//
//                                            if(msg.getfId() != msg.getExt().members.get(i).id)
//                                                sbadd.append(msg.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                                        }
//                                        resadd = sbadd.toString();
//                                    }
//                                    if(!StringUtil.isNullOrEmpty(resadd)) {
//                                        String showRes = resadd.substring(0, resadd.length() - 1);
//                                        lable = showRes + "加入了群聊";
//                                    }
//                                    break;
//                                case 1:
//                                    if(null != msg) {
//                                        String resyq = "";
//                                        StringBuffer sbyq = new StringBuffer();
//                                        String noteName1 = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                        String showName1 = StringUtil.isNullOrEmpty(noteName1)?msg.getfNm():noteName1;
//                                        sbyq.append("\"" + showName1 + "\"邀请");
//                                        if (null != msg.getExt()) {
//                                            if (null != msg.getExt().members && msg.getExt().members.size() > 0) {
//                                                for (int i = 0;i < msg.getExt().members.size();i++){
//                                                    String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                                    String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//                                                    sbyq.append("\""+showName+"\"、");
//                                                }
//                                                resyq = sbyq.toString();
//                                            }
//                                            if(!StringUtil.isNullOrEmpty(resyq)) {
//                                                String showRes = resyq.substring(0, resyq.length() - 1);
//                                                lable = showRes + "加入群聊，请确认";
//                                            }
//                                        }
//                                    }
//                                    break;
//                                case 2:
//                                    String resdel = "";
//                                    StringBuffer sbdel = new StringBuffer();
//                                    if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                        sbdel.append("你将");
//                                    }else{
//                                        String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                        String showName = StringUtil.isNullOrEmpty(noteName)?msg.getfNm():noteName;
//                                        sbdel.append("\""+showName+"\"将");
//                                    }
//                                    if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                        for (int i = 0; i < msg.getExt().members.size();i++){
//                                            String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                            String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//
//                                            sbdel.append(msg.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                                        }
//                                        resdel = sbdel.toString();
//                                    }
//                                    if(!StringUtil.isNullOrEmpty(resdel)) {
//                                        String showResdel = resdel.substring(0, resdel.length() - 1);
//                                        lable = showResdel + "移出群聊";
//                                    }
//                                    break;
//                                case 3:
//                                    if(msg.getfId()== Api.UID){
//                                        if(null != msg) {
//                                            lable = "你修改群名为\"" + msg.gettNm() + "\"";
//                                        }
//                                    }else{
//                                        if(null != msg) {
//                                            String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getfId() + "");
//                                            String showName = StringUtil.isNullOrEmpty(noteName)?msg.getfNm():noteName;
//                                            lable = "\"" + showName + "\"将群名称修改为\"" + msg.gettNm() + "\"";
//
//                                        }
//                                    }
//                                    break;
//                                case 4:
//                                    String resChange = "";
//                                    StringBuffer sbChange = new StringBuffer();
//                                    if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                        for (int i = 0; i < msg.getExt().members.size();i++){
//                                            sbChange.append(msg.getExt().members.get(i).name);
//                                        }
//                                        resChange = sbChange.toString();
//                                        if(null != msg) {
//                                            if (StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                                lable = "你已将群主转让给了\"" + resChange + "\"";
//                                            } else {
//                                                if (msg.getExt().members.get(0).id == Api.UID) {
//                                                    lable = "你已成为新群主";
//                                                } else {
//                                                    lable = "\"" + resChange + "\"已成为新群主";
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                    break;
//                                case 5:
//                                    if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                        lable = "你已开启入群验证";
//                                    }else{
//                                        lable = "群主已开启入群验证";
//                                    }
//                                    break;
//                                case 6:
//                                    if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                        lable = "你已关闭入群验证";
//                                    }else{
//                                        lable = "群主已关闭入群验证";
//                                    }
//                                    break;
//                                case 7:
//                                    String resagree = "";
//                                    StringBuffer sbagree = new StringBuffer();
//                                    if(StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                        sbagree.append("你已同意");
//                                    }
//                                    if(null != msg.getExt().members && msg.getExt().members.size()>0) {
//                                        for (int i = 0; i < msg.getExt().members.size();i++){
//                                            if(!StringUtil.isNullOrEmpty(msg.getExt().members.get(i).originName)){
//                                                String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).originId + "");
//                                                String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).originName:noteName;
//                                                sbagree.append(Integer.parseInt(msg.getExt().members.get(i).originId) == Api.UID?"你邀请的":showName+"邀请");
//                                            }
//                                            String noteName = SharedUtils.getNoteString(MyApp.getInstance().getContext(), msg.getExt().members.get(i).id + "");
//                                            String showName = StringUtil.isNullOrEmpty(noteName)?msg.getExt().members.get(i).name:noteName;
//                                            sbagree.append(msg.getExt().members.get(i).id == Api.UID?"你、":"\""+showName+"\"、");
//                                        }
//                                        resagree = sbagree.toString();
//                                    }
//                                    if(!StringUtil.isNullOrEmpty(resagree)) {
//                                        String showResagree = resagree.substring(0, sbagree.length() - 1);
//                                        if (StringUtil.isNullOrEmpty(msg.getfNm())) {
//                                            lable = showResagree + "加入群聊";
//                                        } else {
//                                            lable = showResagree + "已加入群聊";
//                                        }
//                                    }else{
//                                        lable = resagree + "已加入群聊";
//                                    }
//                                    break;
//                            }
                        }else if (this.getBody().get(this.getBodyLen()-1).getbType() == BaseMessage.MSG_CARD) {
                            lable = "[名片]";
                        }else if (this.getBody().get(this.getBodyLen()-1).getbType() == BaseMessage.MSG_YQM) {
                            lable = "[邀请码]";
                        }else {
                            lable = "[未知]";
                        }
                    }
        return lable;
    }

    @Override
    public String toString() {
        return "GroupBaseMessage{" +
                "body=" + body +
                ", bodyLen=" + bodyLen +
                ", boubleType=" + boubleType +
                ", gId='" + gId + '\'' +
                ", eTime=" + eTime +
                ", gName='" + gName + '\'' +
                ", id=" + id +
                ", sType=" + sType +
                ", isRead=" + isRead +
                ", uniqueId='" + + '\'' +
                '}';
    }
}
