package com.chillar.manoramaapp.Activities;

public class Places {

    String placeName, ipAddress;

    public Places(String placeName, String ipAddress) {
        this.placeName = placeName;
        this.ipAddress = ipAddress;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return placeName;
    }
}
