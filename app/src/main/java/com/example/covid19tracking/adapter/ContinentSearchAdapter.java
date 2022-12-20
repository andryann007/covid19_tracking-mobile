package com.example.covid19tracking.adapter;

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

import java.util.List;
import java.util.Locale;

public class ContinentSearchAdapter extends RecyclerView.Adapter<ContinentSearchAdapter.ViewHolder>{
    private final ContinentResult continentResults;
    private final Context context;

    public ContinentSearchAdapter(ContinentResult continentResults, Context context) {
        this.continentResults = continentResults;
        this.context = context;
    }

    @NonNull
    @Override
    public ContinentSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContinentSearchAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.continent_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContinentSearchAdapter.ViewHolder holder, int position) {
        holder.bindItem(continentResults, context);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvContinentName, tvTotalCase, tvActiveCase, tvDeath, tvRecover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContinentName = itemView.findViewById(R.id.tvContinentName);
            tvTotalCase = itemView.findViewById(R.id.tvContinentCases);
            tvActiveCase = itemView.findViewById(R.id.tvContinentActiveCases);
            tvDeath = itemView.findViewById(R.id.tvContinentDeath);
            tvRecover = itemView.findViewById(R.id.tvContinentRecovered);
        }
        void bindItem(ContinentResult continent, Context context){
            setTitleText(tvContinentName, continent.getContinent());

            int totalCase = continent.getContinentCases();
            String mTotalCase = String.format(Locale.US, "%,d",totalCase).replace(',','.');
            setHtmlText(tvTotalCase, mTotalCase);

            int activeCase = continent.getActive();
            String mActiveCase = String.format(Locale.US, "%,d",activeCase).replace(',','.');
            setHtmlText(tvActiveCase, mActiveCase);

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
