package com.dannextech.apps.truckmanagement.Cargo;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dannextech.apps.truckmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllTrucks extends Fragment {
    ProgressDialog progressDialog;

    List<TruckModel> list = new ArrayList<>();

    RecyclerView rvTrucks;
    RecyclerView.Adapter adapter;

    DatabaseReference mReference;

    public AllTrucks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_all_trucks, container, false);

       // TruckQueries query = new TruckQueries(getContext());

        rvTrucks = (RecyclerView) view.findViewById(R.id.rvAllTrucks);

        mReference = FirebaseDatabase.getInstance().getReference().child("Truck");

        mReference.keepSynced(true);

        android.support.v7.widget.LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        rvTrucks.setHasFixedSize(true);
        rvTrucks.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(view.getContext());

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TruckModel truckModel = snapshot.getValue(TruckModel.class);
                    truckModel.setUrl(snapshot.getRef().toString());

                    list.add(truckModel);
                }
                adapter = new AllTrucksAdapter(list, view.getContext());

                rvTrucks.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
