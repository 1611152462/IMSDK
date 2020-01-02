package com.mpush.api.push;

/**
 * @ClassName MessageExtVideo
 * @Description
 * @Author PC
 * @Date 2019/7/23 18:47
 * @Version 1.0
 **/
public class MessageExtVideo implements  PushMsgDetailExt {

    private String url;

    /**
     * 大小
     */
    private long size;

    /**
     * 时长
     */
    private long len;
}
