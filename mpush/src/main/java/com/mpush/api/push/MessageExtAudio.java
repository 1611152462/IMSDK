package com.mpush.api.push;

/**
 * @ClassName MessageExtAudio
 * @Description 音频文件
 * @Author PC
 * @Date 2019/7/23 18:44
 * @Version 1.0
 **/
public class MessageExtAudio {

    /**
     * 音频地址
     */
    private String ulr;

    /**
     * 音频大小 b
     */
    private int size;

    /**
     * 音频长度 秒
     */
    private int len;

    public String getUlr() {
        return ulr;
    }

    public void setUlr(String ulr) {
        this.ulr = ulr;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
