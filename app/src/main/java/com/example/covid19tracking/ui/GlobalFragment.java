package com.example.covid19tracking.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.adapter.CountryDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.CountryResult;
import com.example.covid19tracking.databinding.FragmentGlobalBinding;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GlobalFragment extends Fragment {

    private ApiService apiService;

    private FragmentGlobalBinding binding;

    private ProgressBar loadingGlobalData;
    private CountryDataAdapter countryDataAdapter;
    private ArrayList<CountryResult> globalDataResults;

    private final String yesterday = "false";
    private final String twoDaysAgo = "false";
    private final String allowNull = "false";
    String sortBy;

    public GlobalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = FragmentGlobalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
        globalDataResults = new ArrayList<>();

        getCountriesData(root);

        return root;
    }

    private void getCountriesData(View view){
        RecyclerView rvGlobalData = view.findViewById(R.id.rvCountryData);
        loadingGlobalData = view.findViewById(R.id.loadingCountryData);

        Call<ArrayList<CountryResult>> call = apiService.getCountriesData(yesterday, twoDaysAgo, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                if(response.isSuccessful()){
                    loadingGlobalData.setVisibility(View.GONE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, getContext());
                        rvGlobalData.setAdapter(countryDataAdapter);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fail to Fetch https://disease.sh/v3/covid-19/countries data",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortCountriesByCase(View view){
        sortBy = "cases";
        RecyclerView rvGlobalData = view.findViewById(R.id.rvCountryData);
        loadingGlobalData = view.findViewById(R.id.loadingCountryData);

        Call<ArrayList<CountryResult>> call = apiService.sortCountries(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                if(response.isSuccessful()){
                    loadingGlobalData.setVisibility(View.GONE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, getContext());
                        rvGlobalData.setAdapter(countryDataAdapter);
                    }
                    Toast.makeText(getContext(), "Successfully Sorted Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortCountriesByRecovered(View view){
        sortBy = "recovered";
        RecyclerView rvGlobalData = view.findViewById(R.id.rvCountryData);
        loadingGlobalData = view.findViewById(R.id.loadingCountryData);

        Call<ArrayList<CountryResult>> call = apiService.sortCountries(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                if(response.isSuccessful()){
                    loadingGlobalData.setVisibility(View.GONE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, getContext());
                        rvGlobalData.setAdapter(countryDataAdapter);
                    }
                    Toast.makeText(getContext(), "Successfully Sorted Data by Recovered Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortCountriesByDeath(View view){
        sortBy = "deaths";
        RecyclerView rvGlobalData = view.findViewById(R.id.rvCountryData);
        loadingGlobalData = view.findViewById(R.id.loadingCountryData);

        Call<ArrayList<CountryResult>> call = apiService.sortCountries(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<CountryResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Response<ArrayList<CountryResult>> response) {
                if(response.isSuccessful()){
                    loadingGlobalData.setVisibility(View.GONE);
                    globalDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(globalDataResults).size(); i++){
                        countryDataAdapter = new CountryDataAdapter(globalDataResults, getContext());
                        rvGlobalData.setAdapter(countryDataAdapter);
                    }
                    Toast.makeText(getContext(), "Successfully Sorted Data by Death Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}