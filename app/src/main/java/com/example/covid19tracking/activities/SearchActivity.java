package com.example.covid19tracking.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

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

    private String query;
    private String searchTypeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApiClient.getClient().create(ApiService.class);

        query = getIntent().getStringExtra("searchQuery").toLowerCase();
        String searchType = getIntent().getStringExtra("searchType").toLowerCase();

        searchTypeTitle = getIntent().getStringExtra("searchQuery");

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
                        setTitleContinent(binding.searchToolbar, searchTypeTitle);
                        binding.textSearchResult.setText("Here is The Result");
                        continentResult = response.body();

                        continentSearchAdapter = new ContinentSearchAdapter(continentResult, SearchActivity.this);
                        binding.rvContinentSearch.setAdapter(continentSearchAdapter);
                    }
                } else {
                    binding.loadingSearch.setVisibility(View.GONE);
                    binding.textNoResults.setVisibility(View.VISIBLE);
                    setEmptySearchResult(binding.textNoResults, searchTypeTitle);
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ContinentResult> call, @NonNull Throwable t) {
                binding.loadingSearch.setVisibility(View.GONE);
                binding.textNoResults.setVisibility(View.VISIBLE);
                setErrorWhileSearch(binding.textNoResults, searchTypeTitle);
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
                        setTitleCountry(binding.searchToolbar, searchTypeTitle);
                        binding.textSearchResult.setText("Here is The Result");
                        countryResult = response.body();

                        countrySearchAdapter = new CountrySearchAdapter(countryResult, SearchActivity.this);
                        binding.rvCountrySearch.setAdapter(countrySearchAdapter);
                    }
                } else {
                    binding.loadingSearch.setVisibility(View.GONE);
                    binding.textNoResults.setVisibility(View.VISIBLE);
                    setEmptySearchResult(binding.textNoResults, searchTypeTitle);
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<CountryResult> call, @NonNull Throwable t) {
                binding.loadingSearch.setVisibility(View.GONE);
                binding.textNoResults.setVisibility(View.VISIBLE);
                setErrorWhileSearch(binding.textNoResults, searchTypeTitle);
            }
        });
    }

    private void setTitleContinent(Toolbar toolbar, String textValue){
        toolbar.setTitle(HtmlCompat.fromHtml("You Search : <b>" + textValue + "</b> Continent", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setTitleCountry(Toolbar toolbar, String textValue){
        toolbar.setTitle(HtmlCompat.fromHtml("You Search : <b>" + textValue + "</b> Country", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setEmptySearchResult(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("Sorry Search Result for <b>" + textValue + "</b> is Empty", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setErrorWhileSearch(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("Error Encounter While Searching For <b>" + textValue + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}