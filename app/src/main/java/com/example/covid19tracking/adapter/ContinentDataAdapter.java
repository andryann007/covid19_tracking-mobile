package com.example.covid19tracking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.activities.ContinentDetailActivity;
import com.example.covid19tracking.api.ContinentResult;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContinentDataAdapter extends RecyclerView.Adapter<ContinentDataAdapter.DataViewHolder>{
    private final ArrayList<ContinentResult> continentResults;
    private final Context context;

    public ContinentDataAdapter(ArrayList<ContinentResult> continentResults, Context context){
        this.continentResults = continentResults;
        this.context = context;
    }

    @NonNull
    @Override
    public ContinentDataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new DataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContinentDataAdapter.DataViewHolder holder, int position) {
        holder.bindItem(continentResults.get(position), context);
    }

    @Override
    public int getItemCount(){ return continentResults.size();}

    static class DataViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvContinentName, tvTotalCase, tvDeath, tvRecover;

        DataViewHolder(@NonNull View itemView){
            super(itemView);
            tvContinentName = itemView.findViewById(R.id.tvContinentName);
            tvTotalCase = itemView.findViewById(R.id.tvContinentCases);
            tvDeath = itemView.findViewById(R.id.tvContinentDeath);
            tvRecover = itemView.findViewById(R.id.tvContinentRecovered);
        }

        void bindItem(ContinentResult continent, Context context){
            setTitleText(tvContinentName, continent.getContinent());

            int totalCase = continent.getContinentCases();
            String mTotalCase = String.format(Locale.US, "%,d",totalCase).replace(',','.');
            setHtmlText(tvTotalCase, mTotalCase);

            int deathCase = continent.getDeaths();
            String mDeathCase = String.format(Locale.US, "%,d",deathCase).replace(',','.');
            setHtmlText(tvDeath, mDeathCase);

            int recoveredCase = continent.getRecovered();
            String mRecoveredCase = String.format(Locale.US, "%,d",recoveredCase).replace(',','.');
            setHtmlText(tvRecover, mRecoveredCase);

            itemView.setOnClickListener(v -> {
                Intent continentDetail = new Intent(context, ContinentDetailActivity.class);
                continentDetail.putExtra("tipe", "continent");
                continentDetail.putExtra("continent_name", continent.getContinent());
                context.startActivity(continentDetail);
            });
        }
    }

    private static void setHtmlText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + " Case</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private static void setTitleText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>Continent Name : " + textValue + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}
