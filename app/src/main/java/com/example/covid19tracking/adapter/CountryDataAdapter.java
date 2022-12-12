package com.example.covid19tracking.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.api.ContinentResult;
import com.example.covid19tracking.api.GlobalResult;

import java.util.List;

public class CountryDataAdapter extends RecyclerView.Adapter<CountryDataAdapter.DataViewHolder>{
    private final List<GlobalResult> globalResults;
    private final Context context;

    public CountryDataAdapter(List<GlobalResult> globalResults, Context context){
        this.globalResults = globalResults;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryDataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new DataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryDataAdapter.DataViewHolder holder, int position) {
        holder.bindItem(globalResults.get(position), context);
    }

    @Override
    public int getItemCount(){ return globalResults.size();}

    static class DataViewHolder extends RecyclerView.ViewHolder{
        private final TextView tableContinentName, tableTotalCase, tableDeath, tableRecover;

        DataViewHolder(@NonNull View itemView){
            super(itemView);
            tableContinentName = itemView.findViewById(R.id.tvContinentName);
            tableTotalCase = itemView.findViewById(R.id.tvContinentCases);
            tableDeath = itemView.findViewById(R.id.tvContinentDeath);
            tableRecover = itemView.findViewById(R.id.tvContinentRecovered);
        }

        void bindItem(GlobalResult globalResult, Context context){
            tableContinentName.setText(globalResult.getCountry());
            tableTotalCase.setText(globalResult.getTotalCase());
            tableDeath.setText(globalResult.getDeaths());
            tableRecover.setText(globalResult.getRecovered());

            itemView.setOnClickListener(v -> {
            });
        }
    }
}
