package com.example.covid19tracking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.covid19tracking.R;
import com.example.covid19tracking.api.ApiClient;
import com.example.covid19tracking.api.ApiService;
import com.example.covid19tracking.databinding.ActivityContinentDetailBinding;

import retrofit2.Retrofit;

public class ContinentDetailActivity extends AppCompatActivity {

    private ActivityContinentDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContinentDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = ApiClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);

        binding.toolbar.setOnClickListener(v-> onBackPressed());
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}