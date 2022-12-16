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

    //Constructor
    public ContinentResult(String continent, int cases, int deaths, int recovered) {
        this.continent = continent;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
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
}
