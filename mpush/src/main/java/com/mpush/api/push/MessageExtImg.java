package com.mpush.api.push;

/**
 * @ClassName MessageExtImg
 * @Description TODO
 * @Author PC
 * @Date 2019/7/23 18:40
 * @Version 1.0
 **/
public class MessageExtImg implements PushMsgDetailExt {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
