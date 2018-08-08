package com.dannextech.apps.truckmanagement.Cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dannextech.apps.truckmanagement.R;

import java.util.List;

class AllGoodsAdapter extends RecyclerView.Adapter <AllGoodsAdapter.ViewHolder>{

    Context context;
    List<GoodsModel> goods;
    public AllGoodsAdapter(Context context, List<GoodsModel> list) {
        this.context = context;
        goods = list;
    }

    @NonNull
    @Override
    public AllGoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_details, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllGoodsAdapter.ViewHolder holder, int position) {
        final GoodsModel goodsModel = goods.get(position);

        holder.tvGoodSender.setText(goodsModel.getName());
        holder.tvGoodRoute.setText(goodsModel.getRoute());
        holder.tvGoodType.setText(goodsModel.getCargo_type());
        holder.tvGoodDate.setText(goodsModel.getSubmit_date());

        holder.cvGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TruckLocation.class));
            }
        });
        /*holder.cvGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor= preferences.edit();

                editor.putString("ref",goodsModel.getUrl());
                editor.apply();

                Fragment fragment = new ViewSpecificGood();
                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.adminFragment,fragment);
                fragmentTransaction.commitAllowingStateLoss();


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGoodSender, tvGoodDate, tvGoodType, tvGoodRoute;
        CardView cvGoods;
        public ViewHolder(View itemView) {
            super(itemView);

            tvGoodDate = itemView.findViewById(R.id.tvGoodDate);
            tvGoodRoute = itemView.findViewById(R.id.tvGoodRoute);
            tvGoodType = itemView.findViewById(R.id.tvGoodType);
            tvGoodSender = itemView.findViewById(R.id.tvGoodSender);
            cvGoods = itemView.findViewById(R.id.cvGoods);
        }
    }
}
