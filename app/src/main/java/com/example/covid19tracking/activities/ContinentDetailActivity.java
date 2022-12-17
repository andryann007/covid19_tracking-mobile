package com.example.covid19tracking.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import com.example.covid19tracking.R;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.ContinentResult;
import com.example.covid19tracking.databinding.ActivityContinentDetailBinding;

import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ContinentDetailActivity extends AppCompatActivity {

    private ActivityContinentDetailBinding binding;

    private ApiService apiService;

    private String continent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContinentDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);

        continent = getIntent().getStringExtra("continent_name");

        binding.toolbar.setOnClickListener(v-> onBackPressed());

        setContinentDetail();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    private void setContinentDetail(){
        String twoDaysAgo = "false";
        String yesterday = "false";
        String allowNull = "false";
        String strict = "false";
        Call<ContinentResult> call = apiService.getContinentsDetail(continent, yesterday, twoDaysAgo,
                strict, allowNull);
        call.enqueue(new Callback<ContinentResult>() {
            @Override
            public void onResponse(@NonNull Call<ContinentResult> call, @NonNull Response<ContinentResult> response) {
                if(response.body() != null){
                    binding.toolbar.setTitle(response.body().getContinent() + " Continent");

                    setTitleText(binding.textContinentName, response.body().getContinent());

                    int totalCase = response.body().getContinentCases();
                    String mTotalCase = String.format(Locale.US, "%,d",totalCase).replace(',','.');
                    setTotalCaseText(binding.textContinentTotalCase, mTotalCase);

                    int activeCase = response.body().getActive();
                    String mActiveCase = String.format(Locale.US, "%,d",activeCase).replace(',','.');
                    setActiveCaseText(binding.textContinentActiveCase, mActiveCase);

                    int deathCase = response.body().getDeaths();
                    String mDeathCase = String.format(Locale.US, "%,d",deathCase).replace(',','.');
                    setDeathCaseText(binding.textContinentDeath, mDeathCase);

                    int recoveredCase = response.body().getRecovered();
                    String mRecoveredCase = String.format(Locale.US, "%,d",recoveredCase).replace(',','.');
                    setRecoveredCaseText(binding.textContinentRecovered, mRecoveredCase);

                    String[] countryName = response.body().getCountries();
                    String mCountryName = Arrays.toString(countryName);
                    setCountryNameText(binding.textCountriesName, mCountryName);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContinentResult> call, @NonNull Throwable t) {

            }
        });
    }

    private void setTotalCaseText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>Total Case : " + textValue + " Cases</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setActiveCaseText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>Active Case : " + textValue + " Cases</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setDeathCaseText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>Death Case : " + textValue + " Cases</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setRecoveredCaseText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>Recovered Case : " + textValue + " Cases</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setTitleText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + " Continent Case</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setCountryNameText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + ", </b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}