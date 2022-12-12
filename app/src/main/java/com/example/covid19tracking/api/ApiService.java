package com.example.covid19tracking.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/v3/covid-19/continents")
    Call<ContinentResponse> getContinentsData(@Query("yesterday") String yesterday, @Query("twoDaysAgo")
                                           String twoDaysAgo, @Query("sort") String sortBy,
                                              @Query("allowNull") String allowNull);

    @GET("/v3/covid-19/countries")
    Call<GlobalResponse> getCountriesData(@Query("yesterday") String yesterday, @Query("twoDaysAgo")
            String twoDaysAgo, @Query("sort") String sortBy,
                                              @Query("allowNull") String allowNull);
}