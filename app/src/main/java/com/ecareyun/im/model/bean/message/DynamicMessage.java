package com.ecareyun.im.model.bean.message;

public class DynamicMessage extends BaseMessage {

    private int dId;
    private String url;
    private int type;

    @Override
    public int getbType() {
        return MSG_TYPE_DYNAMIC;
    }

    public String getDId(){
        if (getJSONObject() != null) {
            return getJSONObject().getString("id");
        }
        return null;
    }

    public String getUrl(){
        if (getJSONObject() != null) {
            return getJSONObject().getString("url");
        }
        return null;
    }

    public int getType(){
        if (getJSONObject() != null) {
            return getJSONObject().getIntValue("type");
        }
        return -1;
    }
}
