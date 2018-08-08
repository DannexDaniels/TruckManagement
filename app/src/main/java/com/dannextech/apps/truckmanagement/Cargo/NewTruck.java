package com.dannextech.apps.truckmanagement.Cargo;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dannextech.apps.truckmanagement.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewTruck extends Fragment {

    private static final String TAG = "CALL_4";

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    EditText etTruckName,etTruckPlate;
    Spinner spType;
    Button btAddTruck;
    ProgressDialog progressDialog;
    public NewTruck() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_truck, container, false);

        etTruckName = v.findViewById(R.id.etTruckName);
        etTruckPlate = v.findViewById(R.id.etTruckPlate);
        spType = v.findViewById(R.id.spTruckType);
        btAddTruck = v.findViewById(R.id.btAddTruck);


        btAddTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails(v,etTruckName.getText().toString(),etTruckPlate.getText().toString(),spType.getSelectedItem().toString());
            }
        });

        return v;
    }

    private void saveDetails(final View view, String name, String plate, String type) {
        showProgressDialog();
        //creating a reference to the folder users where the details will be saved
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Truck").child(plate);

        DatabaseReference nameRef = databaseReference.child("name");
        DatabaseReference plateRef = databaseReference.child("plate");
        DatabaseReference typeRef = databaseReference.child("type");
        DatabaseReference dateEnteredRef = databaseReference.child("entered_on");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        nameRef.setValue(name);
        plateRef.setValue(plate);
        typeRef.setValue(type);
        dateEnteredRef.setValue(format.format(date), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                hideProgressDialog();
                Toast.makeText(view.getContext(),"Saved Successful",Toast.LENGTH_SHORT);
                /*Fragment fragment = new ViewCall4TalentFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.flOrganizationFragment,fragment);
                fragmentTransaction.commitAllowingStateLoss();*/
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(getContext(),"Saving Truck","Please Wait",true);
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
