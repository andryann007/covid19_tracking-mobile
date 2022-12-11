package com.example.covid19tracking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracking.R;
import com.example.covid19tracking.api.ContinentResult;

import java.util.List;

public class ContinentDataAdapter extends RecyclerView.Adapter<ContinentDataAdapter.DataViewHolder>{
    private final List<ContinentResult> continentResults;
    private final Context context;

    public ContinentDataAdapter(List<ContinentResult> continentResults, Context context){
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
        private final TextView tableContinentName, tableTotalCase, tableDeath, tableRecover;

        DataViewHolder(@NonNull View itemView){
            super(itemView);
            tableContinentName = itemView.findViewById(R.id.tvContinentName);
            tableTotalCase = itemView.findViewById(R.id.tvContinentCases);
            tableDeath = itemView.findViewById(R.id.tvContinentDeath);
            tableRecover = itemView.findViewById(R.id.tvContinentRecovered);
        }

        void bindItem(ContinentResult continentResult, Context context){
            tableContinentName.setText(continentResult.getCase());
            tableTotalCase.setText(continentResult.getTodayCase());
            tableDeath.setText(continentResult.getDeaths());
            tableRecover.setText(continentResult.getRecovered());
        }
    }
}
