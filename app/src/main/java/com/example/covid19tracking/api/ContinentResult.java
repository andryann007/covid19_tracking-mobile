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

    @SerializedName("active")
    int active;

    @SerializedName("countries")
    String [] countries;

    @SerializedName("updated")
    int updated;

    @SerializedName("todayCases")
    int todayCases;

    @SerializedName("todayDeaths")
    int todayDeaths;

    @SerializedName("todayRecovered")
    int todayRecovered;

    @SerializedName("population")
    int population;

    public int getUpdated() {
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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    //Constructor
    public ContinentResult(String continent, int cases, int deaths, int recovered, int active, String [] countries) {
        this.continent = continent;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.countries = countries;
    }

    //Getter & Setter
    public String getContinent(){ return continent; }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public int getContinentCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }
}
