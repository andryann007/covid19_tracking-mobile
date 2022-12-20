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

import java.util.ArrayList;

public class CountryFlagAdapter extends RecyclerView.Adapter<CountryFlagAdapter.ViewHolder>{
    private final ArrayList<CountryResult> countryResults;
    private final Context context;

    public CountryFlagAdapter(ArrayList<CountryResult> countryResults, Context context) {
        this.countryResults = countryResults;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryFlagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryFlagAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countryflags_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryFlagAdapter.ViewHolder holder, int position) {
        holder.bindItem(countryResults.get(position), context);
    }

    @Override
    public int getItemCount() {
        return countryResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView countryName;
        private ImageView countryFlag;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            countryName = itemView.findViewById(R.id.countriesName);
            countryFlag = itemView.findViewById(R.id.countriesFlag);
        }

        void bindItem(CountryResult countryResult, Context context){
            setHtmlText(countryName, countryResult.getCountry());

            Uri flagUrl = Uri.parse(countryResult.getGlobalDetail().getFlagsURL());
            Picasso.get().load(flagUrl).into(countryFlag);

            itemView.setOnClickListener(v -> {
                Intent countryDetail = new Intent(context, CountryDetailActivity.class);
                countryDetail.putExtra("tipe", "country");
                countryDetail.putExtra("country_name", countryResult.getCountry());
                context.startActivity(countryDetail);
            });
        }
    }

    private static void setHtmlText(TextView tv, String textValue){
        tv.setText(HtmlCompat.fromHtml("<b>" + textValue + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}
