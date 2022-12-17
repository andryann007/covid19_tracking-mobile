package com.example.covid19tracking.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/v3/covid-19/all")
    Call<GeneralResult> getGeneralData(@Query("yesterday") String yesterday, @Query("twoDaysAgo")
            String twoDaysAgo, @Query("allowNull") String allowNull);

    @GET("/v3/covid-19/continents")
    Call<ArrayList<ContinentResult>> getContinentsData(@Query("yesterday") String yesterday, @Query("twoDaysAgo")
            String twoDaysAgo, @Query("allowNull") String allowNull);

    @GET("/v3/covid-19/countries")
    Call<ArrayList<CountryResult>> getCountriesData(@Query("yesterday") String yesterday, @Query("twoDaysAgo")
            String twoDaysAgo, @Query("allowNull") String allowNull);

    @GET("/v3/covid-19/continents/{continent}")
    Call<ContinentResult> getContinentsDetail(@Path("continent") String continent, @Query("yesterday")
            String yesterday, @Query("twoDaysAgo") String twoDaysAgo, @Query("strict")
            String strict, @Query("allowNull") String allowNull);

    @GET("/v3/covid-19/countries/{country}")
    Call<CountryResult> getCountriesDetail(@Path("country") String country, @Query("yesterday")
            String yesterday, @Query("twoDaysAgo") String twoDaysAgo, @Query("strict")
            String strict, @Query("allowNull") String allowNull);
}