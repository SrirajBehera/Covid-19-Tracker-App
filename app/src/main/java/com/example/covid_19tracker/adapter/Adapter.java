package com.example.covid_19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19tracker.R;
import com.example.covid_19tracker.models.Model;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    // the stateList that will be displayed
    public ArrayList<Model> stateList;
    public Context context;

    public Adapter(ArrayList<Model> stateList, Context context) {
        this.stateList = stateList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.testing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Model model = stateList.get(position);
        holder.state_name.setText(model.getState());
        holder.district.setText(model.getDistrict());
        holder.nos_active.setText("" + Math.toIntExact((int) model.getActive()));
        holder.nos_recovered.setText("" + Math.toIntExact((int) model.getRecovered()));
        holder.nos_confirmed.setText("" + Math.toIntExact((int) model.getConfirmed()));
        holder.nos_deceased.setText("" + Math.toIntExact((int) model.getDeceased()));
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView state_name, district, nos_active, nos_confirmed, nos_deceased, nos_recovered;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            state_name = itemView.findViewById(R.id.state_name);
            district = itemView.findViewById(R.id.district);
            nos_active = itemView.findViewById(R.id.nos_active);
            nos_confirmed = itemView.findViewById(R.id.nos_confirmed);
            nos_deceased = itemView.findViewById(R.id.nos_deceased);
            nos_recovered = itemView.findViewById(R.id.nos_recovered);
        }
    }
}