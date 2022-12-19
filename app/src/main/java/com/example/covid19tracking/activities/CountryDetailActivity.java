package com.example.covid19tracking.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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

        binding.loadingDetails.setVisibility(View.VISIBLE);

        Call<CountryResult> call = apiService.getCountriesDetail(country, yesterday, twoDaysAgo,
                strict, allowNull);
        call.enqueue(new Callback<CountryResult>() {
            @Override
            public void onResponse(@NonNull Call<CountryResult> call, @NonNull Response<CountryResult> response) {
                if(response.body() != null){
                    binding.loadingDetails.setVisibility(View.GONE);
                    binding.toolbar.setTitle(response.body().getCountry() + " Detail");

                    Uri countryFlagURL = Uri.parse(response.body().getGlobalDetail().getFlagsURL());
                    Picasso.get().load(countryFlagURL).into(binding.imageCountryFlag);

                    setTitleText(binding.textCountryName, response.body().getCountry());

                    long timeStamp = response.body().getUpdated();
                    Date date = new Date(timeStamp);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                    String mDate = dateFormat.format(date);
                    binding.textCountryUpdatedTime.setText(mDate);

                    String continent = response.body().getContinent();
                    binding.textCountryContinent.setText(continent);

                    long population = response.body().getPopulation();
                    String mPopulation = String.format(Locale.US, "%,d",population).replace(',','.');
                    setPopulationText(binding.textCountryPopulation, mPopulation);

                    int totalCase = response.body().getGlobalCases();
                    String mTotalCase = String.format(Locale.US, "%,d",totalCase).replace(',','.');
                    setCaseText(binding.textCountryTotalCase, mTotalCase);

                    int todayCase = response.body().getTodayCases();
                    String mTodayCase = String.format(Locale.US, "%,d",todayCase).replace(',','.');
                    setCaseText(binding.textCountryTodayCase, mTodayCase);

                    int recoveredCase = response.body().getRecovered();
                    String mRecoveredCase = String.format(Locale.US, "%,d",recoveredCase).replace(',','.');
                    setCaseText(binding.textCountryRecovered, mRecoveredCase);

                    int todayRecovered = response.body().getTodayRecovered();
                    String mTodayRecovered = String.format(Locale.US, "%,d",todayRecovered).replace(',','.');
                    setCaseText(binding.textCountryTodayRecovered, mTodayRecovered);

                    int deathCase = response.body().getDeaths();
                    String mDeathCase = String.format(Locale.US, "%,d",deathCase).replace(',','.');
                    setCaseText(binding.textCountryDeath, mDeathCase);

                    int todayDeaths = response.body().getTodayDeaths();
                    String mTodayDeaths = String.format(Locale.US, "%,d",todayDeaths).replace(',','.');
                    setCaseText(binding.textCountryTodayDeath, mTodayDeaths);

                    int activeCase = response.body().getActive();
                    String mActiveCase = String.format(Locale.US, "%,d",activeCase).replace(',','.');
                    setCaseText(binding.textCountryActiveCase, mActiveCase);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CountryResult> call, @NonNull Throwable t) {

            }
        });
    }

    private void setCaseText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + " case</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setPopulationText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + " people</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setTitleText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + "</b> COVID-19 Case", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}