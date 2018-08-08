package com.dannextech.apps.truckmanagement.Cargo;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.dannextech.apps.truckmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateAccount extends AppCompatActivity {

    private static final String TAG = "SIGN_UP_LOG";

    EditText etUserName, etUserId, etUserAge, etUserPhone,etUserEmail,etUserPassword, etUserPasswordConfirm;
    Button btnSaveUser;
    ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etUserAge = findViewById(R.id.etUserAge);
        etUserId = findViewById(R.id.etUserIdNumber);
        etUserName = findViewById(R.id.etUserName);
        etUserPhone = findViewById(R.id.etUserPhone);
        etUserPassword = findViewById(R.id.etUserPassword);
        etUserEmail = findViewById(R.id.etUserEmail);
        etUserPasswordConfirm = findViewById(R.id.etUserPasswordConf);
        btnSaveUser = findViewById(R.id.btUserCreateAccount);

        mAuth = FirebaseAuth.getInstance();

        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    if (checkPassword(etUserPassword.getText().toString().trim(),etUserPasswordConfirm.getText().toString().trim()) && validateEntries(etUserEmail.getText().toString().trim(),etUserPassword.getText().toString().trim(),etUserName.getText().toString().trim(), etUserPhone.getText().toString().trim(), etUserAge.getText().toString().trim(), etUserId.getText().toString().trim())){
                        createUser(etUserEmail.getText().toString().trim(),etUserPassword.getText().toString().trim());
                    }
                }else
                    Snackbar.make(v,"Failed: Check your internet Connection",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validateEntries(String email, String password, String name, String phone, String age, String idNo) {
        if (email.isEmpty()){
            etUserEmail.setError("required");
            return false;
        }else if (password.isEmpty()){
            etUserPassword.setError("required");
            return false;
        }else if (name.isEmpty()){
            etUserName.setError("required");
            return false;
        }else if (phone.isEmpty()){
            etUserPhone.setError("required");
            return false;
        }else if (age.isEmpty()){
            etUserAge.setError("required");
            return false;
        }else if (idNo.isEmpty()){
            etUserId.setError("required");
            return false;
        }
        Log.e(TAG, "validateEntries: all valid");
        return true;
    }


    private boolean checkPassword(String pass, String passconf) {
        if (!pass.equals(passconf)){
            etUserPasswordConfirm.setError("Do not match with Password");
            Toast.makeText(getApplicationContext(),"Password do not match the Confirm Password",Toast.LENGTH_SHORT).show();
            return false;
        }else if (pass.length()<6){
            etUserPassword.setError("Password should be more than 6 characters");
            Toast.makeText(getApplicationContext(),"Password should be more than 6 characters",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Log.e(TAG, "checkPassword: password is authentic");
            return true;
        }
    }

    private void createUser(String email, String password){
        showProgressDialog();
        final FirebaseUser[] user = new FirebaseUser[1];
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.e(TAG, "onComplete: user created successfully");
                    user[0] = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    uploadUserDetails(user[0],etUserName.getText().toString(), etUserPhone.getText().toString(),etUserId.getText().toString(), etUserAge.getText().toString());
                    hideProgressDialog();
                }else {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(),"User Creation Failed"+task.getException(),Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onComplete: user creation failed",task.getException());
                }
            }
        });
    }
    private void uploadUserDetails(FirebaseUser user, String name, String phone, String idNo, String age) {
        //creating a reference to the folder users where the details will be saved
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users/"+user.getUid());

        //creating references to where specific details will be saved
        DatabaseReference nameRef = databaseReference.child("Name");
        DatabaseReference emailRef = databaseReference.child("Email");
        DatabaseReference phoneRef = databaseReference.child("Phone");
        DatabaseReference idRef = databaseReference.child("IdNo");
        DatabaseReference ageRef = databaseReference.child("Age");

        nameRef.setValue(name);
        emailRef.setValue(user.getEmail());
        phoneRef.setValue(phone);
        idRef.setValue(idNo);
        ageRef.setValue(age,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(),"User Saved Successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(this,"Creating Account","Please Wait",true);
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
