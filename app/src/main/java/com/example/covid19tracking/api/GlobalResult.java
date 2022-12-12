package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

public class GlobalResult {
    @SerializedName("country")
    String country;

    public String getCountry() {
        return country;
    }

    @SerializedName("cases")
    int totalCase;

    public int getTotalCase() {
        return totalCase;
    }

    @SerializedName("deaths")
    int deaths;

    public int getDeaths() {
        return deaths;
    }

    @SerializedName("recovered")
    int recovered;

    public int getRecovered() {
        return recovered;
    }
}
