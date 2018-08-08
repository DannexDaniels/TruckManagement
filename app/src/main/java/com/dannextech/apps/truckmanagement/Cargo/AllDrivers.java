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
public class AllDrivers extends Fragment {
    ProgressDialog progressDialog;

    List<DriverModel> list = new ArrayList<>();

    RecyclerView rvDrivers;
    RecyclerView.Adapter adapter;

    public DatabaseReference mReference;

    public AllDrivers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_drivers, container, false);

        //TruckQueries query = new TruckQueries(getContext());

        rvDrivers = (RecyclerView) v.findViewById(R.id.rvAllDrivers);

        mReference = FirebaseDatabase.getInstance().getReference().child("Drivers");

        mReference.keepSynced(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        rvDrivers.setHasFixedSize(true);
        rvDrivers.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(v.getContext());

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    DriverModel driverModel = dataSnapshot.getValue(DriverModel.class);
                    driverModel.setUrl(dataSnapshot.getRef().toString());
                    list.add(driverModel);
                    Log.e("Dannex", "onDataChange: "+driverModel.getUrl());
                }

                adapter = new AllDriversAdapter(list,getContext());

                rvDrivers.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });


        return v;
    }

}
