package com.dannextech.apps.truckmanagement.Cargo;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.app.ProgressDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.dannextech.apps.truckmanagement.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllGoods extends Fragment {

    ProgressDialog progressDialog;

    List<GoodsModel> list = new ArrayList<>();

    RecyclerView rvAllGoods;
    RecyclerView.Adapter adapter;

    DatabaseReference mReference;


    public AllGoods() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_view_all_goods, container, false);

        rvAllGoods = view.findViewById(R.id.rvAllGoods);
        mReference = FirebaseDatabase.getInstance().getReference().child("Goods");

        mReference.keepSynced(true);

        android.support.v7.widget.LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        rvAllGoods.setHasFixedSize(true);
        rvAllGoods.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(view.getContext());

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    GoodsModel goodsModel = snapshot.getValue(GoodsModel.class);
                    goodsModel.setUrl(snapshot.getRef().toString());

                    list.add(goodsModel);
                }
                adapter = new AllGoodsAdapter(view.getContext(), list);

                rvAllGoods.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

}
