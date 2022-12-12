package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContinentResponse {
    @SerializedName("results")
    private final List<ContinentResult> results = new ArrayList<>();

    public List<ContinentResult> getResults() {
        return results;
    }
}