package com.ecareyun.im.model.bean.message;

public class VoiceExt extends MessageExt {
    private String url;
    private String size;
    private String len;

    public VoiceExt(){}

    public VoiceExt(String url, String size, String len) {
        this.url = url;
        this.size = size;
        this.len = len;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }
}
