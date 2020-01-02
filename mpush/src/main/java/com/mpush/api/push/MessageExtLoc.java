package com.mpush.api.push;

/**
 * @ClassName MessageExtIoc
 * @Description TODO
 * @Author PC
 * @Date 2019/7/23 18:46
 * @Version 1.0
 **/
public class MessageExtLoc implements PushMsgDetailExt {
    /**
     * 地图的图片
     */
    private String url;

    /**
     * 地图的位置
     */
    private String pos;

    /**
     * 位置的详细地址
     */
    private String address;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPos() {
        return pos;
    }



    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MessageExtIoc{" +
                "url='" + url + '\'' +
                ", pos='" + pos + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
