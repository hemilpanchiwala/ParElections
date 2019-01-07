package com.example.android.parliamentaryelections;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText email;
    private EditText password;
    private Button btnLogin, btnElectoralOfficer;
    private TextView signUp;
    private CheckBox checkBox;
    String add2;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Bold.ttf");
        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Light.ttf");
        setUpUIViews();
        show_hide_pass();


        email.setTypeface(font);
        password.setTypeface(font);
        checkBox.setTypeface(font1);
        btnLogin.setTypeface(font);
        signUp.setTypeface(font);
        btnElectoralOfficer.setTypeface(font);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(MainActivity.this, Login.class));
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp1.class));
            }
        });


        btnElectoralOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ElectoralOfficer.class));
            }
        });

    }

    public void show_hide_pass(){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b){
                    // hide password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }else{
                    // show password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }



   public void onClick(View view) {
        userLogin();
    }

    private void userLogin() {
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        if (TextUtils.isEmpty(email1)) {
            Toast.makeText(MainActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password1)) {
            Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, Login.class));
                            Toast.makeText(MainActivity.this, "Welcome to the app", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkEmailverification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, Login.class));
    }

    private void setUpUIViews() {
        signUp = (TextView) findViewById(R.id.linkSignUp);
        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnElectoralOfficer = (Button) findViewById(R.id.electoralOfficer);
        checkBox = (CheckBox) findViewById(R.id.cbPassword);
    }
}
