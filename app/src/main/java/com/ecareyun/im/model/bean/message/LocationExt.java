package com.ecareyun.im.model.bean.message;

public class LocationExt extends MessageExt {

    public String url;
    public double lon;
    public double lat;
    public String address;
    public int width;
    public int height;

    public LocationExt(){}

    public LocationExt(String url, double lon, double lat, String address, int width, int height) {
        this.url = url;
        this.lon = lon;
        this.lat = lat;
        this.address = address;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
