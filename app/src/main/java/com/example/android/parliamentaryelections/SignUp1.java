package com.example.android.parliamentaryelections;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.UUID;
import java.util.regex.Pattern;

public class SignUp1 extends AppCompatActivity {

    private TextView dob, tvBack,tvdisability;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button btnReset;
    private EditText aadhar, username, name1, mobile;
    private EditText tflat, tlocality, tcity, tpincode, tstate, tdistrict, rname, relation;
    private EditText pflat, plocality, pcity, ppincode, pstate, pdistrict;
    private CheckBox check;
    private RadioGroup gender;
    private boolean validateAadhar;
    private String temp;
    private Button btn1Choose;
    private ImageView imageView,imageView2;
    private FirebaseAuth firebaseAuth;
    private String aadhar1, username1, name2, dob1, mobile1, taddress, paddress, rname1, relation1;
    private FirebaseStorage storage;
    private String UID;
    DatabaseReference myRef, myRef1;
    FirebaseDatabase database;

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    Typeface font, font1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        font = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Bold.ttf");
        font1 = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Light.ttf");

        setUpUIViews();
        dobPicker();
        validateAadhar = Verhoeff.validateVerhoeff(aadhar.getText().toString());

        btn1Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {

                }else {
                    pflat.setText(tflat.getText().toString());
                    plocality.setText(tlocality.getText().toString());
                    pcity.setText(tcity.getText().toString());
                    ppincode.setText(tpincode.getText().toString());
                    pstate.setText(tstate.getText().toString());
                    pdistrict.setText(tdistrict.getText().toString());
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dob.setText("DD/MM/YYYY");
                aadhar.setText("");
                username.setText("");
                name1.setText("");
                mobile.setText("");
                gender.clearCheck();
                tflat.setText("");
                tlocality.setText("");
                tcity.setText("");
                tpincode.setText("");
                tstate.setText("");
                tdistrict.setText("");
                rname.setText("");
                relation.setText("");
                pflat.setText("");
                plocality.setText("");
                pcity.setText("");
                ppincode.setText("");
                pstate.setText("");
                pdistrict.setText("");
                imageView.setImageResource(android.R.color.transparent);
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUp1.this, MainActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    public void onClick(View view) {

        if (!validateNull(name1)) {
            name1.setError("Enter Name Properly");
        }
        if (!validateNull(username)) {
            username.setError("Enter username field");
        }
        if (!validateNull(mobile) || mobile.length() != 10) {
            mobile.setError("Enter mobile No. Properly");
        }
        if (!validateNull(tflat)){
            tflat.setError("Fill this field");
        }
        if (!validateNull(tlocality)){
            tlocality.setError("Fill this field");
        }
        if (!validateNull(tcity)){
            tcity.setError("Fill this field");
        }
        if (!validateNull(tpincode) || tpincode.length() != 6){
            tpincode.setError("Fill this field");
        }
        if (!validateNull(tstate)){
            tstate.setError("Fill this field");
        }
        if (!validateNull(tdistrict)){
            tdistrict.setError("Fill this field");
        }
        if (!validateNull(pflat)){
            pflat.setError("Fill this field");
        }
        if (!validateNull(plocality)){
            plocality.setError("Fill this field");
        }
        if (!validateNull(pcity)){
            pcity.setError("Fill this field");
        }
        if (!validateNull(ppincode) || ppincode.length() != 6){
            ppincode.setError("Fill this field");
        }
        if (!validateNull(pstate)){
            pstate.setError("Fill this field");
        }
        if (!validateNull(pdistrict)){
            pdistrict.setError("Fill this field");
        }
        if (!validateNull(rname)){
            rname.setError("Enter Relative Name");
        }
        if (!validateNull(relation)){
            relation.setError("Enter the type Of Relation");
        }
        if (!validateAadhar || aadhar.length() != 12){
            aadhar.setError("Enter aadhar properly");
        }

        if (aadhar.length() == 12 && validateAadhar && validateNull(name1) && validateNull(mobile) &&
                validateNull(tflat) && validateNull(tlocality) && validateNull(tcity) && validateNull(tpincode) &&
                validateNull(tstate) && validateNull(tdistrict) && validateNull(pflat) && validateNull(plocality) &&
                validateNull(pcity) && validateNull(ppincode) && validateNull(pstate) && validateNull(pstate) &&
                validateNull(pdistrict) && validateNull(rname) && validateNull(relation) && imageView.getDrawable() != null
                && mobile.length() == 10 && ppincode.length() == 6 && tpincode.length() == 6) {
            registerUser();
        }else {
            Toast.makeText(SignUp1.this, "Enter all details properly", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateNull(EditText editText) {
        String pulse = editText.getText().toString();
        if (pulse.matches(""))
            return false;
        return true;
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUp1.this, "Successfully Registered\nVerification Mail Sent", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignUp1.this, MainActivity.class));
                    }else {
                        Toast.makeText(SignUp1.this, "Verification mail hasn't been sent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void string() {
        aadhar1 = aadhar.getText().toString();
        username1 = username.getText().toString();
        name2 = name1.getText().toString();
        dob1 = dob.getText().toString();
        mobile1 = mobile.getText().toString();
        taddress = tflat.getText().toString()+", "+tlocality.getText().toString()+", "+tcity.getText().toString()+", "+tdistrict.getText().toString()+", "+tstate.getText().toString()+" - "+tpincode.getText().toString();
        paddress = pflat.getText().toString()+", "+plocality.getText().toString()+", "+pcity.getText().toString()+", "+pdistrict.getText().toString()+", "+pstate.getText().toString()+" - "+ppincode.getText().toString();
        rname1 = rname.getText().toString();
        relation1 = relation.getText().toString();
    }

    private void registerUser() {
        String user_email = username.getText().toString();
        String user_password = aadhar.getText().toString();

        if (TextUtils.isEmpty(user_email)) {
            Toast.makeText(SignUp1.this, "Enter username properly", Toast.LENGTH_LONG).show();
            username.setError("Enter username");
            return;
        }
        if (TextUtils.isEmpty(user_password)) {
            Toast.makeText(SignUp1.this, "Enter proper Aadhar Id", Toast.LENGTH_LONG).show();
            return;
        }

    /*    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(SignUp1.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {    */

                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference("Users");
                           myRef1 = database.getReference("Users1");

                            string();

                            String key = myRef.push().getKey();
                            String key1 = myRef1.push().getKey();

                            User user = new User(aadhar1, username1, name2, dob1,
                                    mobile1, taddress, paddress, rname1, relation1);

                            Userdetails3 userdetails3 = new Userdetails3();
                            userdetails3.setAadhar4(aadhar1);
                            userdetails3.setCaddress4(taddress);
                            userdetails3.setDob4(dob1);
                            userdetails3.setMobile4(mobile1);
                            userdetails3.setPaddress4(paddress);
                            userdetails3.setRelation4(relation1);
                            userdetails3.setRname4(rname1);
                            userdetails3.setName4(name2);
                            userdetails3.setUsername4(username1);

                          /*  FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);    */

                          myRef.child(key).setValue(userdetails3);
                          myRef1.child(key1).setValue(userdetails3);


                /*
                            FirebaseUser currentfirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            UID = currentfirebaseUser.getUid();   */
                            uploadImage(key);

                            Toast.makeText(SignUp1.this, "Thanks for signing Up \n Will be able to login once Verified",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUp1.this, MainActivity.class));
                            finish();
                       //     sendEmailVerification();

                  /*      } else {
                            Toast.makeText(SignUp1.this, "Registration Unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });   */


    }

    private void uploadImage(String value) {

        if (filePath != null) {

            StorageReference ref = storageReference.child("images/"+value);

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(SignUp1.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp1.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void setUpUIViews() {
        check = (CheckBox) findViewById(R.id.check);
        check.setTypeface(font);
        dob = (TextView) findViewById(R.id.dob);
        dob.setTypeface(font);
        tvBack = (TextView) findViewById(R.id.tvback);
        tvBack.setTypeface(font);
        aadhar = (EditText) findViewById(R.id.aadhar1);
        aadhar.setTypeface(font);
        username = (EditText) findViewById(R.id.username1);
        username.setTypeface(font);
        name1 = (EditText) findViewById(R.id.name2);
        name1.setTypeface(font);
        mobile = (EditText) findViewById(R.id.mobile1);
        mobile.setTypeface(font);
        gender = (RadioGroup) findViewById(R.id.gender);
        tflat = (EditText) findViewById(R.id.cflat1);
        tflat.setTypeface(font);
        tlocality = (EditText) findViewById(R.id.clocality1);
        tlocality.setTypeface(font);
        tcity = (EditText) findViewById(R.id.city3);
        tcity.setTypeface(font);
        tpincode = (EditText) findViewById(R.id.cpincode1);
        tpincode.setTypeface(font);
        tstate = (EditText) findViewById(R.id.cstate1);
        tstate.setTypeface(font);
        tdistrict = (EditText) findViewById(R.id.cdistrict1);
        tdistrict.setTypeface(font);
        rname = (EditText) findViewById(R.id.rname1);
        rname.setTypeface(font);
        relation = (EditText) findViewById(R.id.torelation1);
        relation.setTypeface(font);
        pflat = (EditText) findViewById(R.id.pflat1);
        pflat.setTypeface(font);
        plocality = (EditText) findViewById(R.id.plocality1);
        plocality.setTypeface(font);
        pcity = (EditText) findViewById(R.id.city4);
        pcity.setTypeface(font);
        ppincode = (EditText) findViewById(R.id.ppincode1);
        ppincode.setTypeface(font);
        pstate = (EditText) findViewById(R.id.pstate1);
        pstate.setTypeface(font);
        pdistrict = (EditText) findViewById(R.id.pdistrict1);
        pdistrict.setTypeface(font);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setTypeface(font);
        btn1Choose = (Button) findViewById(R.id.btnchoose);
        btn1Choose.setTypeface(font);
        imageView = (ImageView) findViewById(R.id.photo);
        TextView aadhar4 = (TextView) findViewById(R.id.aadhar);
        aadhar4.setTypeface(font);
        TextView tvAccountInfo = (TextView) findViewById(R.id.tvaccountInfo);
        tvAccountInfo.setTypeface(font);
        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setTypeface(font);
        TextView calendar = (TextView) findViewById(R.id.calendar);
        calendar.setTypeface(font);
        TextView tvGender = (TextView) findViewById(R.id.tvGender);
        tvGender.setTypeface(font);
        TextView tvcaddress = (TextView) findViewById(R.id.tvcaddress);
        tvcaddress.setTypeface(font);
        TextView tvpaddress = (TextView) findViewById(R.id.tvpaddress);
        tvpaddress.setTypeface(font);
        TextView tvRelative = (TextView) findViewById(R.id.relative);
        tvRelative.setTypeface(font);
        TextView tvphoto = (TextView) findViewById(R.id.tvphoto);
        tvphoto.setTypeface(font);

    }

    private void dobPicker() {
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignUp1.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth+"/"+month+"/"+year;
                dob.setText(date, TextView.BufferType.EDITABLE);
            }
        };
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

