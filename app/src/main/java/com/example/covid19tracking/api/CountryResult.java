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

    @SerializedName("updated")
    long updated;

    @SerializedName("todayCases")
    int todayCases;

    @SerializedName("todayDeaths")
    int todayDeaths;

    @SerializedName("todayRecovered")
    int todayRecovered;

    @SerializedName("population")
    long population;

    @SerializedName("continent")
    String continent;

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public int getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(int todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }


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
