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
import com.example.covid19tracking.adapter.ContinentDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.ContinentResponse;
import com.example.covid19tracking.api.ContinentResult;
import com.example.covid19tracking.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private ApiService apiService;

    private FragmentHomeBinding binding;

    private RecyclerView rvContinentsData;
    private ProgressBar loadingContinentsData;
    private ContinentDataAdapter continentDataAdapter;
    private final List<ContinentResult> continentDataResults = new ArrayList<>();

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
        setContinentData(root);

        return root;
    }

    private void setContinentData(View view) {
        rvContinentsData = view.findViewById(R.id.rvContinentData);
        continentDataAdapter = new ContinentDataAdapter(continentDataResults, getContext());
        loadingContinentsData = view.findViewById(R.id.loadingContinentData);

        getData();
        rvContinentsData.setAdapter(continentDataAdapter);
    }

    private void getData(){
        Call<ContinentResponse> call = apiService.getContinentsData("false", "false", "cases" ,"false");
        call.enqueue(new Callback<ContinentResponse>(){

            @Override
            public void onResponse(@NonNull Call<ContinentResponse> call, @NonNull Response<ContinentResponse> response) {
                if(response.body() != null){
                    if(response.body().getResults() != null){
                        loadingContinentsData.setVisibility(View.GONE);
                        int count = continentDataResults.size();
                        continentDataResults.addAll(response.body().getResults());
                        continentDataAdapter.notifyItemChanged(count, continentDataResults.size());
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<ContinentResponse> call, @NonNull Throwable t) {
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}