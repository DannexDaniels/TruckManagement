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
public class NewDriver extends Fragment {

    EditText etname,etid,etphone,etage,etJoinedDate;
    Button btSubmit;
    ProgressDialog progressDialog;

    private static final String TAG = "TRUCK-MNGT";

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public NewDriver() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_user, container, false);

        etname = v.findViewById(R.id.etDriverName);
        etid = v.findViewById(R.id.etDriverIdNumber);
        etphone = v.findViewById(R.id.etDriverPhone);
        etage = v.findViewById(R.id.etDriverAge);
        etJoinedDate = v.findViewById(R.id.etJoinedDate);
        btSubmit = v.findViewById(R.id.btCreateAccountDriver);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails(v,etname.getText().toString(),etid.getText().toString(),etphone.getText().toString(),etage.getText().toString(),etJoinedDate.getText().toString());
            }
        });


        return v;
    }

    private void saveDetails(final View view, String name, String id, String phone, String age, String dateJoined) {
        showProgressDialog();
        //creating a reference to the folder users where the details will be saved
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Drivers").child(id);

        DatabaseReference nameRef = databaseReference.child("name");
        DatabaseReference phoneRef = databaseReference.child("phone");
        DatabaseReference ageRef = databaseReference.child("age");
        DatabaseReference idRef = databaseReference.child("id");
        DatabaseReference joinedDateRef = databaseReference.child("joined_date");
        DatabaseReference dateEnteredRef = databaseReference.child("entered_on");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        nameRef.setValue(name);
        phoneRef.setValue(phone);
        ageRef.setValue(age);
        idRef.setValue(id);
        joinedDateRef.setValue(dateJoined);
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
        progressDialog = ProgressDialog.show(getContext(),"Saving Driver","Please Wait",true);
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
