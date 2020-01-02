package com.ecareyun.im.model.bean.message;

public class DynamicExt extends MessageExt {

    public String id;
    public String url;
    public int width;
    public int height;
    /**
     * 0-动态
     * 1-价值交换
     * 3-个人主页
     * 4-广告推广
     */
    private int type;

    public DynamicExt() {
    }

    public DynamicExt(String id, String url, int type, int width, int height) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
