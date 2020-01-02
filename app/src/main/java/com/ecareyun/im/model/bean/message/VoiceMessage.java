package com.ecareyun.im.model.bean.message;

public class VoiceMessage extends BaseMessage {
    private String url;
    private int size;
    private int len;

    @Override
    public int getbType() {
        return MSG_TYPE_VOICE;
    }

    public String getUrl(){
        if (getJSONObject() != null) {
            return getJSONObject().getString("url");

        }
        return null;
    }

    public int getSize(){
        if (getJSONObject() != null) {
            return getJSONObject().getIntValue("size");

        }
        return -1;
    }

    public int getLen(){
        if (getJSONObject() != null) {
            return getJSONObject().getIntValue("len");
        }
        return -1;
    }
}
