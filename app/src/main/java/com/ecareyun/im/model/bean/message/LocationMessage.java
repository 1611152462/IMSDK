package com.ecareyun.im.model.bean.message;

public class LocationMessage extends BaseMessage {

    private String url;
    private double lon;
    private double lat;
    private String address;

    @Override
    public int getbType() {
        return MSG_TYPE_LOCATION;
    }

    public String getUrl(){
        if (getJSONObject() != null) {
            return getJSONObject().getString("url");
        }
        return null;
    }

    public double getLon(){
        if (getJSONObject() != null) {
            return getJSONObject().getDouble("lon");
        }
        return -1;
    }

    public double getLat(){
        if (getJSONObject() != null) {
            return getJSONObject().getDouble("lat");
        }
        return -1;
    }

    public String getAddress(){
        if (getJSONObject() != null) {
            return getJSONObject().getString("address");
        }
        return null;
    }
}
