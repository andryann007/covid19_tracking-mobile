package com.example.covid19tracking.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.adapter.CountryDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.GlobalResponse;
import com.example.covid19tracking.api.GlobalResult;
import com.example.covid19tracking.databinding.FragmentGlobalBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GlobalFragment extends Fragment {

    private ApiService apiService;

    private FragmentGlobalBinding binding;

    private RecyclerView rvGlobalData;
    private ProgressBar loadingGlobalData;
    private CountryDataAdapter countryDataAdapter;
    private final List<GlobalResult> globalDataResults = new ArrayList<>();

    public GlobalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = FragmentGlobalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
        setGlobalData(root);

        return root;
    }

    private void setGlobalData(View view) {
        rvGlobalData = view.findViewById(R.id.rvContinentData);
        countryDataAdapter = new CountryDataAdapter(globalDataResults, getContext());
        loadingGlobalData = view.findViewById(R.id.loadingContinentData);

        getData();
        rvGlobalData.setAdapter(countryDataAdapter);
    }

    private void getData(){
        Call<GlobalResponse> call = apiService.getCountriesData("false", "false", "cases" ,"false");
        call.enqueue(new Callback<GlobalResponse>(){

            @Override
            public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                if(response.body() != null){
                    if(response.body().getResults() != null){
                        loadingGlobalData.setVisibility(View.GONE);
                        int count = globalDataResults.size();
                        globalDataResults.addAll(response.body().getResults());
                        countryDataAdapter.notifyItemChanged(count, globalDataResults.size());
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}