package com.example.covid19tracking.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.activities.CountryDetailActivity;
import com.example.covid19tracking.api.CountryResult;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class CountrySearchAdapter extends RecyclerView.Adapter<CountrySearchAdapter.ViewHolder>{
    private final CountryResult countryResults;
    private final Context context;

    public CountrySearchAdapter(CountryResult countryResults, Context context) {
        this.countryResults = countryResults;
        this.context = context;
    }

    @NonNull
    @Override
    public CountrySearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountrySearchAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountrySearchAdapter.ViewHolder holder, int position) {
        holder.bindItem(countryResults, context);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvCountryName, tvTotalCase, tvActiveCase, tvDeath, tvRecover;
        private final ImageView imgCountryFlag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            tvTotalCase = itemView.findViewById(R.id.tvCountryCases);
            tvActiveCase = itemView.findViewById(R.id.tvCountryActiveCases);
            tvDeath = itemView.findViewById(R.id.tvCountryDeath);
            tvRecover = itemView.findViewById(R.id.tvCountryRecovered);
            imgCountryFlag = itemView.findViewById(R.id.imgCountriesFlag);
        }

        void bindItem(CountryResult countryResult, Context context){
            setTitleText(tvCountryName, countryResult.getCountry());

            int totalCase = countryResult.getGlobalCases();
            String mTotalCase = String.format(Locale.US, "%,d",totalCase).replace(',','.');
            setHtmlText(tvTotalCase, mTotalCase);

            int activeCase = countryResult.getActive();
            String mActiveCase = String.format(Locale.US, "%,d",activeCase).replace(',','.');
            setHtmlText(tvActiveCase, mActiveCase);

            int deathCase = countryResult.getDeaths();
            String mDeathCase = String.format(Locale.US, "%,d",deathCase).replace(',','.');
            setHtmlText(tvDeath, mDeathCase);

            int recoveredCase = countryResult.getRecovered();
            String mRecoveredCase = String.format(Locale.US, "%,d",recoveredCase).replace(',','.');
            setHtmlText(tvRecover, mRecoveredCase);

            Uri flagUrl = Uri.parse(countryResult.getGlobalDetail().getFlagsURL());
            Picasso.get().load(flagUrl).into(imgCountryFlag);

            itemView.setOnClickListener(v -> {
                Intent countryDetail = new Intent(context, CountryDetailActivity.class);
                countryDetail.putExtra("tipe", "country");
                countryDetail.putExtra("country_name", countryResult.getCountry());
                context.startActivity(countryDetail);
            });
        }
    }

    private static void setHtmlText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + " Case</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private static void setTitleText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>Country Name : " + textValue + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}
