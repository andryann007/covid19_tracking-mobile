package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContinentResponse {
    private final List<ContinentResult> continent = new ArrayList<>();

    public List<ContinentResult> getResults() {
        return continent;
    }
}