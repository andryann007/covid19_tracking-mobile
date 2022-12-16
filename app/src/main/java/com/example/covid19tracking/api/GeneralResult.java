package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

public class GeneralResult {
    @SerializedName("cases")
    int cases;

    public int getGeneralCases() {
        return cases;
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

    @SerializedName("active")
    int active;

    public int getActive(){ return active; }
}
