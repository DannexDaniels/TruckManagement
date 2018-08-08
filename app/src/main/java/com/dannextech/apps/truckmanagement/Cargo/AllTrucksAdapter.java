package com.dannextech.apps.truckmanagement.Cargo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dannextech.apps.truckmanagement.R;

import java.util.List;

class AllTrucksAdapter extends RecyclerView.Adapter<AllTrucksAdapter.ViewHolder> {
    List<TruckModel> trucks;
    Context context;

    public AllTrucksAdapter(List<TruckModel> truckModels, Context context) {
        trucks = truckModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TruckModel truckModel = trucks.get(position);
        holder.tvName.setText(truckModel.getName());
        holder.tvPlate.setText(truckModel.getPlate());
        holder.tvType.setText(truckModel.getType());
    }

    @Override
    public int getItemCount() {
        return trucks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvTruck;
        TextView tvName, tvPlate,tvType;
        public ViewHolder(View itemView) {
            super(itemView);
            cvTruck = itemView.findViewById(R.id.cvTrucks);
            tvName = itemView.findViewById(R.id.tvTruckName);
            tvPlate = itemView.findViewById(R.id.tvTruckNumber);
            tvType = itemView.findViewById(R.id.tvTruckType);
        }
    }
}
