package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

public class CountryDetail {
    @SerializedName("_id")
    int id;

    @SerializedName("iso2")
    String iso2;

    @SerializedName("iso3")
    String iso3;

    @SerializedName("lat")
    double latitude;

    @SerializedName("long")
    double longitude;

    @SerializedName("flag")
    String flagsURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFlagsURL() {
        return flagsURL;
    }

    public void setFlagsURL(String flagsURL) {
        this.flagsURL = flagsURL;
    }
}
