package com.mpush.api.push;

/**
 * 消息内容类型
 */
public enum BodyType {
    TXT("文本", 1),//文本消息
    IMG("用户图片", 2),//网络图片
    FORWARD_IMG("引导图片", 3),//内部功能引导图
    AUDIO("音频", 4),
    IOC("位置",5),
    VIDEO("视频",6)
    ;

    BodyType(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    private final String desc;
    private final int value;

    public String getDesc() {
        return desc;
    }

    public int getValue() {
        return value;
    }


}
