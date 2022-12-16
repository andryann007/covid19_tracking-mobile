package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

public class GlobalResult {
    @SerializedName("country")
    String country;

    @SerializedName("cases")
    int cases;

    @SerializedName("deaths")
    int deaths;

    @SerializedName("recovered")
    int recovered;

    public String getCountry() {
        return country;
    }

    public int getGlobalCases() {
        return cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRecovered() {
        return recovered;
    }
}
