package com.example.covid19tracking.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.adapter.CountryDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.GeneralResult;
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

    private ProgressBar loadingGlobalData;
    private CountryDataAdapter countryDataAdapter;
    private final List<GlobalResult> globalDataResults = new ArrayList<>();
    TextView tvActiveCase, tvTotalCase, tvDeath, tvRecovered;

    private final String yesterday = "false";
    private final String twoDaysAgo = "false";
    private final String sort = "cases";
    private final String allowNull = "false";

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
        setGlobalData(root);
        setCountryData(root);

        return root;
    }

    private void setCountryData(View view) {
        RecyclerView rvGlobalData = view.findViewById(R.id.rvCountryData);
        countryDataAdapter = new CountryDataAdapter(globalDataResults, getContext());
        loadingGlobalData = view.findViewById(R.id.loadingCountryData);

        getData();
        rvGlobalData.setAdapter(countryDataAdapter);
    }

    private void getData(){
        Call<GlobalResponse> call = apiService.getCountriesData(yesterday, twoDaysAgo, sort ,allowNull);
        call.enqueue(new Callback<GlobalResponse>(){

            @Override
            public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                if(response.body() != null){
                    if(response.body().getResults() != null){
                        loadingGlobalData.setVisibility(View.GONE);
                        globalDataResults.addAll(response.body().getResults());
                        int count = globalDataResults.size();
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

    private void setGlobalData(View view) {
        tvActiveCase = view.findViewById(R.id.textValueActiveCase);
        tvTotalCase = view.findViewById(R.id.textValueTotalCase);
        tvDeath = view.findViewById(R.id.textValueDeath);
        tvRecovered = view.findViewById(R.id.textValueRecovered);
        getGeneralData();
    }

    private void getGeneralData(){

        Call<GeneralResult> call = apiService.getGeneralData(yesterday, twoDaysAgo, allowNull);
        call.enqueue(new Callback<GeneralResult>(){
            @Override
            public void onResponse(@NonNull Call<GeneralResult> call, @NonNull Response<GeneralResult> response) {
                if(response.body() != null){
                    setHtmlText(tvActiveCase, String.valueOf(response.body().getActive()));
                    setHtmlText(tvTotalCase, String.valueOf(response.body().getGeneralCases()));
                    setHtmlText(tvDeath, String.valueOf(response.body().getDeaths()));
                    setHtmlText(tvRecovered, String.valueOf(response.body().getRecovered()));
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<GeneralResult> call, @NonNull Throwable t) {
            }
        });
    }

    private void setHtmlText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + " Cases</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}