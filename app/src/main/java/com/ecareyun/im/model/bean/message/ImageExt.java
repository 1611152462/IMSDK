package com.ecareyun.im.model.bean.message;

public class ImageExt extends MessageExt {
    public String url;
    public int width;
    public int height;

    public ImageExt() {}

    public ImageExt(String txt, int width, int height) {
        this.url = txt;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
