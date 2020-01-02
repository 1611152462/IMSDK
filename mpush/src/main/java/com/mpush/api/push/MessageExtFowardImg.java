package com.mpush.api.push;

/**
 * @ClassName MessageExtFowardImg
 * @Description 引导图片
 * @Author PC
 * @Date 2019/7/23 18:41
 * @Version 1.0
 **/
public class MessageExtFowardImg implements PushMsgDetailExt {
    /**
     * 引导图片地址
     */
    private String url;

    /**
     * 引导图片类型
     */
    private int type;

    /**
     * 引导图片类型对应ID
     */
    private int id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MessageExtFowardImg{" +
                "url='" + url + '\'' +
                ", type=" + type +
                ", id=" + id +
                '}';
    }
}
