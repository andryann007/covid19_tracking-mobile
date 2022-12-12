package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GlobalResponse {
    @SerializedName("results")
    private final List<GlobalResult> results = new ArrayList<>();

    public List<GlobalResult> getResults() {
        return results;
    }
}
