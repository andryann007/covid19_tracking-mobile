package com.example.covid19tracking.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("covid-19/continents")
    Call<ContinentResult> getCovid19Global();
}