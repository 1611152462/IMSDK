package com.mpush.api.push;

/**
 * @ClassName MessageExtContent
 * @Description 文本内容
 * @Author PC
 * @Date 2019/7/23 18:39
 * @Version 1.0
 **/
public class MessageExtContent implements PushMsgDetailExt {
    private String txt;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "MessageExtContent{" +
                "txt='" + txt + '\'' +
                '}';
    }
}
