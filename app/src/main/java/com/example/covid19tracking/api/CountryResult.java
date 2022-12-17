package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

public class CountryResult {
    @SerializedName("country")
    String country;

    @SerializedName("cases")
    int cases;

    @SerializedName("deaths")
    int deaths;

    @SerializedName("recovered")
    int recovered;

    @SerializedName("active")
    int active;

    @SerializedName("countryInfo")
    private CountryDetail countryDetail;

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

    public int getActive() {
        return active;
    }

    public CountryDetail getGlobalDetail() {
        return countryDetail;
    }

    public void setGlobalDetail(CountryDetail countryDetail) {
        this.countryDetail = countryDetail;
    }

    public CountryResult(String country, int cases, int deaths, int recovered, int active, CountryDetail countryDetail) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.countryDetail = countryDetail;
    }
}
