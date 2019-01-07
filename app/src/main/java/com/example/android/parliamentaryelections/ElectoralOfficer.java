package com.example.android.parliamentaryelections;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ElectoralOfficer extends AppCompatActivity implements
            View.OnClickListener {

    private String id;
    private TextView tvElectoralLogin;
    private EditText editText;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSubmit, btnSignUp;
    private static final String TAG = "ElectoralActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electoral_officer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Electoral Officer\'s Login");

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Bold.ttf");
        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Light.ttf");

        editText = (EditText) findViewById(R.id.etID);
        edtEmail = (EditText) findViewById(R.id.etEmail);
        edtPassword = (EditText) findViewById(R.id.etPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvElectoralLogin = (TextView) findViewById(R.id.tvID);

        btnSubmit.setTypeface(font);
        btnSignUp.setTypeface(font);
        tvElectoralLogin.setTypeface(font);
        editText.setTypeface(font1);
        edtEmail.setTypeface(font1);
        edtPassword.setTypeface(font1);

        findViewById(R.id.btnSubmit).setOnClickListener(this);
        findViewById(R.id.btnSignUp).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }




    public void onClick(View view) {
            id = editText.getText().toString();
            int i = view.getId();
            if(id.equals("963418363284")) {
                if (i == R.id.btnSubmit) {
                    signIn(edtEmail.getText().toString(), edtPassword.getText().toString());
                } else if (i == R.id.btnSignUp) {
                    createAccount(edtEmail.getText().toString(), edtPassword.getText().toString());
                }
            }else {
                Toast.makeText(ElectoralOfficer.this, "Enter valid ID!", Toast.LENGTH_SHORT).show();
            }
    }

    private void createAccount(String email, String password) {
        Log.e(TAG, "createAccount:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "createAccount: Success!");
                            Toast.makeText(ElectoralOfficer.this, "Account created Successfuly",Toast.LENGTH_SHORT).show();

                            // update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            writeNewUser(user.getUid(), getUsernameFromEmail(user.getEmail()), user.getEmail());
                        } else {
                            Log.e(TAG, "createAccount: Fail!", task.getException());
                            Toast.makeText(ElectoralOfficer.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.e(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "signIn: Success!");
                            Toast.makeText(ElectoralOfficer.this, "Welcome Officer", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ElectoralOfficer.this, Main2Activity.class));

                            // update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.e(TAG, "signIn: Fail!", task.getException());
                            Toast.makeText(ElectoralOfficer.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(ElectoralOfficer.this, "Authentication Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ElectoralOfficer.this, "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(ElectoralOfficer.this, "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(ElectoralOfficer.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void writeNewUser(String userId, String username, String email) {
        User1 user = new User1(username, email);

        FirebaseDatabase.getInstance().getReference().child("users").child(userId).setValue(user);
    }

    private String getUsernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}