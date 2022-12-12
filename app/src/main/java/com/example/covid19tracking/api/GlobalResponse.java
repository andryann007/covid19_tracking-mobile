package com.example.covid19tracking.api;

import java.util.ArrayList;
import java.util.List;

public class GlobalResponse {
    private final List<GlobalResult> results = new ArrayList<>();

    public List<GlobalResult> getResults() {
        return results;
    }
}
