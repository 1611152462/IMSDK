package com.ecareyun.im.model.bean.message;

import java.io.Serializable;
import java.util.List;

public class MessageExt implements Serializable {
    public String url;
    public double lon;
    public double lat;
    public String address;
    public int width;
    public int height;
    public String size;
    public String len;
    public String id;
    public int type;
    private String txt;
    public String name;
    public int gType;
    public List<Member> members;
    public int fUid;
    public String code;
    public String gName;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public class Member{
        public String name;
        public long id;
        public String originId;
        public String originName;

        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", originId='" + originId + '\'' +
                    ", originName='" + originName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MessageExt{" +
                "url='" + url + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", address='" + address + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", size='" + size + '\'' +
                ", len='" + len + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", txt='" + txt + '\'' +
                ", name='" + name + '\'' +
                ", gType=" + gType +
                ", members=" + members +
                ", fUid=" + fUid +
                ", code='" + code + '\'' +
                ", gName='" + gName + '\'' +
                '}';
    }
}
