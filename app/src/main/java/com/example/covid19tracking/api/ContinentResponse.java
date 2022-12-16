package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContinentResponse {
    private ContinentResult[] continent;

    public ContinentResult[] getContinent() {
        return continent;
    }

    public void setContinent(ContinentResult[] continent) {
        this.continent = continent;
    }
}