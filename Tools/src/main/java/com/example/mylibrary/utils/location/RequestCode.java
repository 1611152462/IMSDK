package com.example.mylibrary.utils.location;

/**
 * 请求码
 *
 * @author AndSync
 * @date 2017/10/30
 * Copyright © 2014-2017 AndSync All rights reserved.
 */
public interface RequestCode {
    /**
     * 电话
     */
    int PHONE = 0x00;
    /**
     * 位置
     */
    int LOCATION = 0x01;
    /**
     * 相机
     */
    int CAMERA = 0x02;
    /**
     * 语音
     */
    int AUDIO = 0x04;
    /**
     * 存储
     */
    int EXTERNAL = 0x08;
    /**
     * 存储
     */
    int WRITE = 0x03;
    /**
     * 手机唯一码
     */
    int IMEI = 0x12;
    /**
     * 多个
     */
    int MORE = 0x10;

    /**
     * 分享
     */
    int SHARE = 0x32;
}