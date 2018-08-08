package com.dannextech.apps.truckmanagement.Cargo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dannextech.apps.truckmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private static final String TAG = "LOGIN_LOG";

    EditText etEmail,etPassword;
    Button btSignIn,btSignUp;

    FirebaseAuth mAuth;

    FirebaseDatabase database;
    DatabaseReference databaseRef;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        etEmail = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);

        btSignIn = (Button) findViewById(R.id.btnLogin);
        btSignUp = (Button) findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()){
                    showProgressDialog();
                    mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.e(TAG, "onComplete: user created successfully");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                                retrieveUserDetails(user.getUid());
                            }else {
                                hideProgressDialog();
                                Toast.makeText(getApplicationContext(),"Authentication failed: "+task.getException(),Toast.LENGTH_LONG).show();
                                Log.e(TAG, "onComplete: user creation failed",task.getException());
                            }
                        }
                    });
                }else {
                    Snackbar.make(v,"Failed: Check your internet Connection",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateAccount.class));
            }
        });
    }

    private void retrieveUserDetails(String uid) {

        //creating a reference to the folder users where the details will be saved
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference().child("Users/"+uid);

        //creating references to where specific details will be saved
        DatabaseReference nameRef = databaseRef.child("Name");
        DatabaseReference emailRef = databaseRef.child("Email");
        DatabaseReference phoneRef = databaseRef.child("Phone");
        DatabaseReference idRef = databaseRef.child("IdNo");
        DatabaseReference ageRef = databaseRef.child("Age");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: details are "+dataSnapshot.getValue());
                hideProgressDialog();
                startActivity(new Intent(getApplicationContext(),User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(),"Something went wrong. Please try again",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onCancelled: "+databaseError );
            }
        });

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final SharedPreferences.Editor edit = preferences.edit();


        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.e(TAG, "onDataChange: "+value);

                edit.putString("user_name",value);
                edit.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.e(TAG, "onDataChange: "+value);

                edit.putString("user_email",value);
                edit.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        phoneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.e(TAG, "onDataChange: "+value);

                edit.putString("user_phone",value);
                edit.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        idRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.e(TAG, "onDataChange: "+value);

                edit.putString("user_id",value);
                edit.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.e(TAG, "onDataChange: "+value);

                edit.putString("user_age",value);
                edit.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(Login.this,"Authenticating","Please Wait",true);
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
