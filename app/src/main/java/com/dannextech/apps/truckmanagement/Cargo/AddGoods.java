package com.dannextech.apps.truckmanagement.Cargo;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dannextech.apps.truckmanagement.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddGoods extends Fragment {

    private static final String TAG = "CALL_4";

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private EditText etName, etId, etCargoWeight, etCarryDate, etDeliveryDate;
    private Spinner spCargoType,spToFrom;
    private Button btnSubmit;
    ProgressDialog progressDialog;

    public AddGoods() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_goods, container, false);
        etName = v.findViewById(R.id.etGoodUserName);
        etId = v.findViewById(R.id.etGoodUserId);
        etCargoWeight = v.findViewById(R.id.etGoodWeight);
        etCarryDate = v.findViewById(R.id.etGoodCarryDate);
        etDeliveryDate = v.findViewById(R.id.etGoodDeliveryDate);
        spCargoType = v.findViewById(R.id.spGoodCargoType);
        spToFrom = v.findViewById(R.id.spGoodRoute);
        btnSubmit = v.findViewById(R.id.btAddGood);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        etName.setText(preferences.getString("user_name",""));
        etId.setText(preferences.getString("user_id",""));

        etCarryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerCarry();
            }
        });
        etDeliveryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDeliver();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails(v,etName.getText().toString(),etId.getText().toString(),spCargoType.getSelectedItem().toString(),etCargoWeight.getText().toString(),spToFrom.getSelectedItem().toString(),etCarryDate.getText().toString(),etDeliveryDate.getText().toString());

                Fragment fragment = new UserCargo();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.myUserFragment,fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
        return v;
    }

    private void saveDetails(final View view, String name, String id, String type, String weight, String route, String carryDate, String deliveryDate) {
        showProgressDialog();
        //creating a reference to the folder users where the details will be saved
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Goods").child(id);

        DatabaseReference nameRef = databaseReference.child("name");
        DatabaseReference typeRef = databaseReference.child("cargo_type");
        DatabaseReference weightRef = databaseReference.child("cargo_weight");
        DatabaseReference routeRef = databaseReference.child("route");
        DatabaseReference carryRef = databaseReference.child("carry_date");
        DatabaseReference deliveryRef = databaseReference.child("delivery_date");
        DatabaseReference submitRef = databaseReference.child("submit_date");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        nameRef.setValue(name);
        typeRef.setValue(type);
        weightRef.setValue(weight);
        routeRef.setValue(route);
        deliveryRef.setValue(deliveryDate);
        submitRef.setValue(format.format(date));
        carryRef.setValue(carryDate, new DatabaseReference.CompletionListener() {
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
        progressDialog = ProgressDialog.show(getContext(),"Saving Good","Please Wait",true);
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

    private void showDatePickerCarry() {
        Log.e("DANNEX", "showDatePicker: I have been clicked");
        int mYear,mMonth,mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTextCarry(dayOfMonth,month,year);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        //datePickerDialog.getDatePicker().setMaxDate(mDay + 7);
        datePickerDialog.show();
    }

    private void setTextCarry(int dayOfMonth, int month, int year) {
        Toast.makeText(getContext(),year+"/"+(month+1)+"/"+dayOfMonth,Toast.LENGTH_SHORT).show();
        etCarryDate.setText(dayOfMonth + "/" + month + "/" +year);
    }

    private void showDatePickerDeliver() {
        Log.e("DANNEX", "showDatePicker: I have been clicked");
        int mYear,mMonth,mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTextDeliver(dayOfMonth,month,year);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        //datePickerDialog.getDatePicker().setMaxDate(mDay + 7);
        datePickerDialog.show();
    }

    private void setTextDeliver(int dayOfMonth, int month, int year) {
        Toast.makeText(getContext(),year+"/"+(month+1)+"/"+dayOfMonth,Toast.LENGTH_SHORT).show();
        etDeliveryDate.setText(dayOfMonth + "/" + month + "/" +year);
    }

}
