package com.example.covid19tracking.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.adapter.CountryDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.GeneralResult;
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
        globalDataResults = new ArrayList<>();

        getTotalData(root);
        getCountriesData(root);

        return root;
    }

    private void getCountriesData(View view){
        RecyclerView rvGlobalData = view.findViewById(R.id.rvCountryData);
        loadingGlobalData = view.findViewById(R.id.loadingCountryData);

        Call<ArrayList<CountryResult>> call = apiService.getCountriesData(yesterday, twoDaysAgo, sort ,allowNull);
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

    private void getTotalData(View view){
        tvActiveCase = view.findViewById(R.id.textValueActiveCase);
        tvTotalCase = view.findViewById(R.id.textValueTotalCase);
        tvDeath = view.findViewById(R.id.textValueDeath);
        tvRecovered = view.findViewById(R.id.textValueRecovered);

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