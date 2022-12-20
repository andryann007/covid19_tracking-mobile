package com.example.covid19tracking.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19tracking.adapter.ContinentSearchAdapter;
import com.example.covid19tracking.adapter.CountrySearchAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.ContinentResult;
import com.example.covid19tracking.api.CountryResult;
import com.example.covid19tracking.databinding.ActivitySearchBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private ContinentResult continentResult;
    private CountryResult countryResult;
    private ContinentSearchAdapter continentSearchAdapter;
    private CountrySearchAdapter countrySearchAdapter;
    private ApiService apiService;
    private ActivitySearchBinding binding;

    private String query, searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApiClient.getClient().create(ApiService.class);

        query = getIntent().getStringExtra("searchQuery");
        searchType = getIntent().getStringExtra("searchType");

        switch (searchType){
            case "continents":
                searchContinent();
                break;

            case "countries":
                searchCountry();
        }

        binding.searchToolbar.setOnClickListener(v-> onBackPressed());
    }

    private void searchContinent() {
        String twoDaysAgo = "false";
        String yesterday = "false";
        String allowNull = "false";
        String strict = "false";

        Call<ContinentResult> call = apiService.searchContinents(query, yesterday, twoDaysAgo, strict, allowNull);
        call.enqueue(new Callback <ContinentResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call <ContinentResult> call, @NonNull Response <ContinentResult> response) {
                binding.loadingSearch.setVisibility(View.VISIBLE);
                binding.rvContinentSearch.setVisibility(View.VISIBLE);

                if(response.body() != null){
                    if(response.isSuccessful()){
                        binding.loadingSearch.setVisibility(View.GONE);
                        binding.searchToolbar.setTitle("You Search " + query + " Continent");
                        binding.textSearchResult.setText("Here is The Result");
                        continentResult = response.body();

                        continentSearchAdapter = new ContinentSearchAdapter(continentResult, SearchActivity.this);
                        binding.rvContinentSearch.setAdapter(continentSearchAdapter);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ContinentResult> call, @NonNull Throwable t) {
                binding.loadingSearch.setVisibility(View.GONE);
                binding.textNoResults.setVisibility(View.VISIBLE);
            }
        });
    }

    private void searchCountry() {
        String twoDaysAgo = "false";
        String yesterday = "false";
        String allowNull = "false";
        String strict = "false";

        Call<CountryResult> call = apiService.searchCountries(query, yesterday, twoDaysAgo, strict, allowNull);
        call.enqueue(new Callback<CountryResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<CountryResult> call, @NonNull Response<CountryResult> response) {
                binding.loadingSearch.setVisibility(View.VISIBLE);
                binding.rvCountrySearch.setVisibility(View.VISIBLE);

                if(response.body() != null){
                    if(response.isSuccessful()){
                        binding.loadingSearch.setVisibility(View.GONE);
                        binding.searchToolbar.setTitle("You Search " + query + " Continent");
                        binding.textSearchResult.setText("Here is The Result");
                        countryResult = response.body();

                        countrySearchAdapter = new CountrySearchAdapter(countryResult, SearchActivity.this);
                        binding.rvCountrySearch.setAdapter(countrySearchAdapter);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<CountryResult> call, @NonNull Throwable t) {
                binding.loadingSearch.setVisibility(View.GONE);
                binding.textNoResults.setVisibility(View.VISIBLE);
            }
        });
    }
}