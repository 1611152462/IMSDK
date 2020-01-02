package com.example.mylibrary.utils.search;


import com.example.mylibrary.utils.cn.CN;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by you on 2017/9/11.
 */

public class Contact {


    /**
     * code : 200
     * msg :
     * data : [{"id":12333,"nickname":"小可爱","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"小可爱","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"小可爱","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"大可爱","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"二可爱","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"张三","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"李四","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"王五","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"马五五","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"},{"id":12333,"nickname":"啊五五","avatar":"https://public-tbank.oss-cn-beijing.aliyuncs.com/1223.jpg","marke":"测试备注"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements CN {
        /**
         * id : 10005
         * nickname : j9可爱
         * avatar :
         * sex : 1
         * age : 0
         * constellation : 13
         * location : 这是在数据库获取的用户信息的测试地址
         * intro : 111111
         */

        private int uid;
        private String nickname;
        private String avatar;
        private int sex;
        private int age;
        private int constellation;
        private String location;
        private String intro;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getConstellation() {
            return constellation;
        }

        public void setConstellation(int constellation) {
            this.constellation = constellation;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        @Override
        public String chinese() {
            return nickname;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "uid=" + uid +
                    ", nickname='" + nickname + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", sex=" + sex +
                    ", age=" + age +
                    ", constellation=" + constellation +
                    ", location='" + location + '\'' +
                    ", intro='" + intro + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Contact{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
