package com.example.covid19tracking.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GlobalResponse {
    private final List<GlobalResult> country = new ArrayList<>();

    public List<GlobalResult> getResults() {
        return country;
    }
}
