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
import com.example.covid19tracking.adapter.ContinentDataAdapter;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.ContinentResult;
import com.example.covid19tracking.api.GeneralResult;
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

    private ProgressBar loadingContinentsData;
    private ContinentDataAdapter continentDataAdapter;
    private ArrayList<ContinentResult> continentDataResults;

    TextView tvActiveCase, tvTotalCase, tvDeath, tvRecovered;

    private final String yesterday = "false";
    private final String twoDaysAgo = "false";
    private final String sort = "cases";
    private final String allowNull = "false";

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
        getTotalData(root);
        getContinentsData(root);

        return root;
    }

    private void getContinentsData(View view){
        RecyclerView rvContinentsData = view.findViewById(R.id.rvContinentData);
        loadingContinentsData = view.findViewById(R.id.loadingContinentData);

        Call<ArrayList<ContinentResult>> call = apiService.getContinentsData(yesterday, twoDaysAgo, sort, allowNull);
        call.enqueue(new Callback<ArrayList<ContinentResult>>(){

            @Override
            public void onResponse(@NonNull Call<ArrayList<ContinentResult>> call,
                                   @NonNull Response<ArrayList<ContinentResult>> response) {
                if(response.isSuccessful()){
                    loadingContinentsData.setVisibility(View.GONE);
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
                Toast.makeText(getContext(), "Fail to Fetch https://disease.sh/v3/covid-19/continents data",
                        Toast.LENGTH_SHORT).show();
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