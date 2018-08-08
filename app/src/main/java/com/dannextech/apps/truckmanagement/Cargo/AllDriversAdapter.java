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

class AllDriversAdapter extends RecyclerView.Adapter<AllDriversAdapter.ViewHolder> {
    List<DriverModel> drivers;
    Context context;

    public AllDriversAdapter(List<DriverModel> driverModels, Context context) {
        drivers = driverModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DriverModel driver = drivers.get(position);
        holder.tvName.setText(driver.getName());
        holder.tvAge.setText(driver.getAge());
        holder.tvPhone.setText(driver.getPhone());
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvDriver;
        TextView tvName, tvAge, tvPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            cvDriver = itemView.findViewById(R.id.cvDrivers);
            tvName = itemView.findViewById(R.id.tvDriverName);
            tvAge = itemView.findViewById(R.id.tvDriverAge);
            tvPhone = itemView.findViewById(R.id.tvDriverPhone);
        }
    }
}
