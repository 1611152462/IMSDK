package com.ecareyun.im.model.bean.message;

public class ImageMessage extends BaseMessage {

    private String url;

    @Override
    public int getbType() {
        return MSG_TYPE_IMAGE;
    }

    public String getUrl(){
        if (getJSONObject() != null) {
            return getJSONObject().getString("url");
        }
        return null;
    }

}
