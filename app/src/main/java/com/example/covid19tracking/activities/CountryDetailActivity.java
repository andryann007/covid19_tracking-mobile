package com.example.covid19tracking.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import com.example.covid19tracking.R;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.api.CountryResult;
import com.example.covid19tracking.databinding.ActivityCountryDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountryDetailActivity extends AppCompatActivity {

    private ActivityCountryDetailBinding binding;

    private ApiService apiService;

    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCountryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);

        country = getIntent().getStringExtra("country_name");

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

        Call<CountryResult> call = apiService.getCountriesDetail(country, yesterday, twoDaysAgo,
                strict, allowNull);
        call.enqueue(new Callback<CountryResult>() {
            @Override
            public void onResponse(@NonNull Call<CountryResult> call, @NonNull Response<CountryResult> response) {
                if(response.body() != null){
                    binding.toolbar.setTitle(response.body().getCountry() + " Country");

                    setTitleText(binding.textCountryName, response.body().getCountry());

                    int totalCase = response.body().getGlobalCases();
                    String mTotalCase = String.format(Locale.US, "%,d",totalCase).replace(',','.');
                    setTotalCaseText(binding.textCountryTotalCase, mTotalCase);

                    int activeCase = response.body().getActive();
                    String mActiveCase = String.format(Locale.US, "%,d",activeCase).replace(',','.');
                    setActiveCaseText(binding.textCountryActiveCase, mActiveCase);

                    int deathCase = response.body().getDeaths();
                    String mDeathCase = String.format(Locale.US, "%,d",deathCase).replace(',','.');
                    setDeathCaseText(binding.textCountryDeath, mDeathCase);

                    int recoveredCase = response.body().getRecovered();
                    String mRecoveredCase = String.format(Locale.US, "%,d",recoveredCase).replace(',','.');
                    setRecoveredCaseText(binding.textCountryRecovered, mRecoveredCase);

                    Uri countryFlagURL = Uri.parse(response.body().getGlobalDetail().getFlagsURL());
                    Picasso.get().load(countryFlagURL).into(binding.imageCountryFlag);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CountryResult> call, @NonNull Throwable t) {

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
}