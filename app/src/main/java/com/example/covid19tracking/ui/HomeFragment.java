package com.example.covid19tracking.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    TextView tvContinentName, tvContinentCases, tvContinentDeath, tvContinentRecovered;

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

        tvContinentName = root.findViewById(R.id.tvContinentName);
        tvContinentCases = root.findViewById(R.id.tvContinentCases);
        tvContinentDeath = root.findViewById(R.id.tvContinentDeath);
        tvContinentRecovered = root.findViewById(R.id.tvContinentRecovered);

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
        setContinentData(root);

        return root;
    }

    private void setContinentData(View view) {
        rvContinentsData = view.findViewById(R.id.rvContinentData);
        continentDataAdapter = new ContinentDataAdapter(continentDataResults, getContext());
        loadingContinentsData = view.findViewById(R.id.loadingContinentData);

        rvContinentsData.setAdapter(continentDataAdapter);
        getContinentData();
    }

    private void getContinentData(){
        Call<ContinentResult> call = apiService.getCovid19Global();
        call.enqueue(new Callback<ContinentResult>(){

            @Override
            public void onResponse(@NonNull Call<ContinentResult> call, @NonNull Response<ContinentResult> response) {
                if(response.body() != null){
                    loadingContinentsData.setVisibility(View.GONE);

                    tvContinentName.setText(response.body().getCase());
                    tvContinentCases.setText(response.body().getTodayCase());
                    tvContinentDeath.setText(response.body().getDeaths());
                    tvContinentRecovered.setText(response.body().getRecovered());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContinentResult> call, @NonNull Throwable t) {

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}