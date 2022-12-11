package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

public class ContinentResult {
    @SerializedName("cases")
    int Case;

    public int getCase() {
        return Case;
    }

    @SerializedName("todayCases")
    int todayCase;

    public int getTodayCase() {
        return todayCase;
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
