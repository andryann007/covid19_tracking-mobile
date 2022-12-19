package com.example.covid19tracking.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.adapter.ContinentDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.ContinentResult;
import com.example.covid19tracking.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private ApiService apiService;

    private FragmentHomeBinding binding;
    private ContinentDataAdapter continentDataAdapter;
    private ArrayList<ContinentResult> continentDataResults;

    private final String yesterday = "false";
    private final String twoDaysAgo = "false";
    private final String allowNull = "false";
    String sortBy;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
        continentDataResults = new ArrayList<>();

        getContinentsData(root);

        return root;
    }

    private void getContinentsData(View view){
        RecyclerView rvContinentsData = view.findViewById(R.id.rvContinentData);
        binding.loadingContinentData.setVisibility(View.VISIBLE);

        Call<ArrayList<ContinentResult>> call = apiService.getContinentsData(yesterday, twoDaysAgo, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                if(response.isSuccessful()){
                    binding.loadingContinentData.setVisibility(View.GONE);
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, getContext());
                        rvContinentsData.setAdapter(continentDataAdapter);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fail to Fetch https://disease.sh/v3/covid-19/continents data",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortContinentByCase(View view){
        sortBy = "cases";
        RecyclerView rvContinentsData = view.findViewById(R.id.rvContinentData);

        Call<ArrayList<ContinentResult>> call = apiService.sortContinent(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                if(response.isSuccessful()){
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, getContext());
                        rvContinentsData.setAdapter(continentDataAdapter);
                    }
                    Toast.makeText(getContext(), "Successfully Sorted the Data by Total Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortContinentByRecovered(View view){
        sortBy = "recovered";
        RecyclerView rvContinentsData = view.findViewById(R.id.rvContinentData);

        Call<ArrayList<ContinentResult>> call = apiService.sortContinent(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                if(response.isSuccessful()){
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, getContext());
                        rvContinentsData.setAdapter(continentDataAdapter);
                    }
                    Toast.makeText(getContext(), "Successfully Sorted the Data by Recovered Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fail to Sorted the Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortContinentByDeath(View view){
        sortBy = "deaths";
        RecyclerView rvContinentsData = view.findViewById(R.id.rvContinentData);

        Call<ArrayList<ContinentResult>> call = apiService.sortContinent(yesterday, twoDaysAgo, sortBy, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                if(response.isSuccessful()){
                    continentDataResults = response.body();

                    for(int i = 0; i < Objects.requireNonNull(continentDataResults).size(); i++){
                        continentDataAdapter = new ContinentDataAdapter(continentDataResults, getContext());
                        rvContinentsData.setAdapter(continentDataAdapter);
                    }
                    Toast.makeText(getContext(), "Successfully Sorted the Data by Death Case !!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ArrayList<ContinentResult>> call, @NonNull Throwable t) {
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