package com.example.covid19tracking.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19tracking.adapter.ContinentDataAdapter;
import com.example.covid19tracking.adapter.CountryDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.ContinentResult;
import com.example.covid19tracking.api.CountryResult;
import com.example.covid19tracking.databinding.ActivitySortBinding;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SortActivity extends AppCompatActivity {
    private ApiService apiService;

    private ContinentDataAdapter continentDataAdapter;
    private ArrayList<ContinentResult> continentDataResults;
    private CountryDataAdapter countryDataAdapter;
    private ArrayList<CountryResult> globalDataResults;

    private final String yesterday = "false";
    private final String twoDaysAgo = "false";
    private final String allowNull = "false";
    String sortBy = null;

    private ActivitySortBinding binding;

    private String sortType, sortQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySortBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApiClient.getClient().create(ApiService.class);

        sortType = getIntent().getStringExtra("sortType");
        sortQuery = getIntent().getStringExtra("sortBy");

        if(sortType.equals("continents")){
            switch (sortQuery){
                case "cases":
                    sortContinentByCase();
                    break;
                case "actives":
                    sortContinentByActive();
                    break;
                case "recovered":
                    sortContinentByRecovered();
                    break;
                case "deaths":
                    sortContinentByDeath();
                    break;
            }
        } else if (sortType.equals("countries")){
            switch (sortQuery){
                case "cases":
                    sortCountriesByCase();
                    break;
                case "actives":
                    sortCountriesByActive();
                    break;
                case "recovered":
                    sortCountriesByRecovered();
                    break;
                case "deaths":
                    sortCountriesByDeath();
                    break;
            }
        }

        binding.sortToolbar.setOnClickListener(v-> onBackPressed());
    }

    private void sortContinentByCase(){
        sortBy = "cases";

        Call<ArrayList<ContinentResult>> call = apiService.sortContinent(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvContinentSort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Continent Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textContinentData.setVisibility(View.VISIBLE);
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, SortActivity.this);
                        binding.rvContinentSort.setAdapter(continentDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted the Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortContinentByActive(){
        sortBy = "actives";

        Call<ArrayList<ContinentResult>> call = apiService.sortContinent(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvContinentSort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Continent Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textContinentData.setVisibility(View.VISIBLE);
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, SortActivity.this);
                        binding.rvContinentSort.setAdapter(continentDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted the Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortContinentByRecovered(){
        sortBy = "recovered";

        Call<ArrayList<ContinentResult>> call = apiService.sortContinent(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvContinentSort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Continent Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textContinentData.setVisibility(View.VISIBLE);
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, SortActivity.this);
                        binding.rvContinentSort.setAdapter(continentDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted the Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortContinentByDeath(){
        sortBy = "deaths";

        Call<ArrayList<ContinentResult>> call = apiService.sortContinent(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvContinentSort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Continent Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textContinentData.setVisibility(View.VISIBLE);
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, SortActivity.this);
                        binding.rvContinentSort.setAdapter(continentDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted the Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortCountriesByCase(){
        sortBy = "cases";

        Call<ArrayList<CountryResult>> call = apiService.sortCountries(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvCountrySort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Countries Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textCountryData.setVisibility(View.VISIBLE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, SortActivity.this);
                        binding.rvCountrySort.setAdapter(countryDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortCountriesByActive(){
        sortBy = "actives";

        Call<ArrayList<CountryResult>> call = apiService.sortCountries(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvCountrySort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Countries Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textCountryData.setVisibility(View.VISIBLE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, SortActivity.this);
                        binding.rvCountrySort.setAdapter(countryDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortCountriesByRecovered(){
        sortBy = "recovered";

        Call<ArrayList<CountryResult>> call = apiService.sortCountries(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvCountrySort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Countries Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textCountryData.setVisibility(View.VISIBLE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, SortActivity.this);
                        binding.rvCountrySort.setAdapter(countryDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortCountriesByDeath(){
        sortBy = "deaths";

        Call<ArrayList<CountryResult>> call = apiService.sortCountries(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){
            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                binding.loadingSort.setVisibility(View.VISIBLE);
                binding.rvCountrySort.setVisibility(View.VISIBLE);

                if(response.isSuccessful()){
                    binding.sortToolbar.setTitle("Countries Data Sort by " + sortBy);
                    binding.textSort.setVisibility(View.VISIBLE);
                    binding.textCountryData.setVisibility(View.VISIBLE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, SortActivity.this);
                        binding.rvCountrySort.setAdapter(countryDataAdapter);
                    }
                    Toast.makeText(SortActivity.this, "Successfully Sorted Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(SortActivity.this, "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}