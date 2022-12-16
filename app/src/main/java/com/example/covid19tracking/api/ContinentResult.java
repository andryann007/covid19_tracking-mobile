package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

public class ContinentResult {
    @SerializedName("continent")
    String continent;

    @SerializedName("cases")
    int cases;

    @SerializedName("deaths")
    int deaths;

    @SerializedName("recovered")
    int recovered;

    public String getContinent() {
        return continent;
    }

    public int getContinentCases() {
        return cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRecovered() {
        return recovered;
    }
}
