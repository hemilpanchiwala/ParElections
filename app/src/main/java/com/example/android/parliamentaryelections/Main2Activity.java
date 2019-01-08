package com.example.android.parliamentaryelections;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    TextView eemail1;
    EditText ename1, eaddress1;
    Dialog dialog, dialog1, dialog2, dialog4;
    Button save, view, save1, cancel, btnSetDates, btnAdd,btnCancel4, btnAdd4, btnResults, btnCancel, btnSetResults, btnAdd1, btnCancel1, btnUpdates, imgChoose;
    FirebaseDatabase database;
    DatabaseReference myRef, myRef1, myRef2, myRef3, myRef4;
    List<Listdata3> list;
    RecyclerView recyclerview;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    TextView setDate, setDate4;
    EditText etParty, etSeats;
    public final String CHANNEL_ID = "personal_notifications";
    public static final int NOTIFICATION_ID = 001;
    TextView btnLogout2, tvNo;
    Typeface font, font1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        font = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Bold.ttf");
        font1 = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Light.ttf");

        btnSetDates = (Button) findViewById(R.id.btnSetDates);
        btnSetResults = (Button) findViewById(R.id.btnSetResults);
        recyclerview = (RecyclerView) findViewById(R.id.rview);
        btnUpdates = (Button) findViewById(R.id.btnUpdates);
        btnResults = (Button) findViewById(R.id.btnResults);
        btnLogout2 = (TextView) findViewById(R.id.btnLogout3);
        tvNo = (TextView) findViewById(R.id.tvNo);

        btnSetDates.setTypeface(font);
        btnSetResults.setTypeface(font);
        btnUpdates.setTypeface(font);
        btnResults.setTypeface(font);
        btnLogout2.setTypeface(font);
        tvNo.setTypeface(font);

        database = FirebaseDatabase.getInstance();

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        btnLogout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null) {
                    if (v == btnLogout2) {
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Main2Activity.this, MainActivity.class
                        ));
                    }
                }
            }
        });

        btnUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2 = new Dialog(Main2Activity.this);
                dialog2.setTitle("Add Updates");
                dialog2.setContentView(R.layout.dialog_updates);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ename1 = (EditText) dialog2.findViewById(R.id.etname1);
                eemail1 = (TextView) dialog2.findViewById(R.id.eemail1);
                eaddress1 = (EditText) dialog2.findViewById(R.id.eaddress1);
                save1 = (Button) dialog2.findViewById(R.id.save1);
                cancel = (Button) dialog2.findViewById(R.id.view1);

                ename1.setTypeface(font);
                eemail1.setTypeface(font);
                eaddress1.setTypeface(font);
                save1.setTypeface(font);
                cancel.setTypeface(font);

                dobPicker(eemail1);

                myRef = database.getReference("message");
                save1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String address = ename1.getText().toString();
                        String name = eemail1.getText().toString();
                        String email = eaddress1.getText().toString();

                        String key = myRef.push().getKey();
                        Userdetails userdetails = new Userdetails();

                        userdetails.setName(name);
                        userdetails.setEmail(email);
                        userdetails.setAddress(address);

                        myRef.child(key).setValue(userdetails);
                        ename1.setText("");
                        eaddress1.setText("");
                        eemail1.setText("DD/MM/YYYY");
                        dialog2.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });


                dialog2.show();
            }
        });

        btnSetDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
                dialog.show();
            }
        });

        btnSetResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog1();
                dialog1.show();
            }
        });

        btnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog4();
                dialog4.show();
            }
        });

        myRef3 = database.getReference("Users1");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Userdetails3 userdetails3 = dataSnapshot1.getValue(Userdetails3.class);
                    Listdata3 listdata3 = new Listdata3();
                    String aadhar = userdetails3.getAadhar4();
                    String username = userdetails3.getUsername4();
                    String caddress = userdetails3.getCaddress4();
                    String paddress = userdetails3.getPaddress4();
                    String dob = userdetails3.getDob4();
                    String name = userdetails3.getName4();
                    String mobile = userdetails3.getMobile4();
                    String relation = userdetails3.getRelation4();
                    String rname = userdetails3.getRname4();
                    listdata3.setAadhar4(aadhar);
                    listdata3.setUsername4(username);
                    listdata3.setCaddress4(caddress);
                    listdata3.setPaddress4(paddress);
                    listdata3.setDob4(dob);
                    listdata3.setName4(name);
                    listdata3.setMobile4(mobile);
                    listdata3.setRelation4(relation);
                    listdata3.setRname4(rname);
                    list.add(listdata3);
                }

                RecyclerviewAdapter3 recycler = new RecyclerviewAdapter3(list, Main2Activity.this);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(Main2Activity.this);
                recyclerview.setLayoutManager(layoutmanager);
                recyclerview.setItemAnimator( new DefaultItemAnimator());
                recyclerview.setAdapter(recycler);
                recycler.notifyItemInserted(0);
                recyclerview.smoothScrollToPosition(0);
                if (recycler.getItemCount() == 0) {
                    tvNo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    /*    view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list = new ArrayList<>();
                        // StringBuffer stringbuffer = new StringBuffer();
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                            Userdetails userdetails = dataSnapshot1.getValue(Userdetails.class);
                            Listdata listdata = new Listdata();
                            String name=userdetails.getName();
                            String email=userdetails.getEmail();
                            String address=userdetails.getAddress();
                            listdata.setName(name);
                            listdata.setEmail(email);
                            listdata.setAddress(address);
                            list.add(listdata);
                            // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();

                        }

                        RecyclerviewAdapter recycler = new RecyclerviewAdapter(list);
                        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(Main2Activity.this);
                        recyclerview.setLayoutManager(layoutmanager);
                        recyclerview.setItemAnimator( new DefaultItemAnimator());
                        recyclerview.setAdapter(recycler);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //  Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

            }
        });  */




    }

    private void createDialog() {
        dialog = new Dialog(this);
        dialog.setTitle("Select Date");
        dialog.setContentView(R.layout.dialog_add);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvSetDate = (TextView) dialog.findViewById(R.id.tvsetDate);
        setDate = (TextView) dialog.findViewById(R.id.dob1);
        btnAdd = (Button) dialog.findViewById(R.id.btn_add);
        btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        tvSetDate.setTypeface(font);
        setDate.setTypeface(font1);
        btnAdd.setTypeface(font);
        btnCancel.setTypeface(font);


        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dobPicker(setDate);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef1 = database.getReference("election-dates");
                String add = setDate.getText().toString();

                String key =myRef1.push().getKey();
                Userdetails1 userdetails1 = new Userdetails1();


                userdetails1.setAdd(add);

                myRef1.child(key).setValue(userdetails1);
                setDate.setText("DD/MM/YYYY");


                dialog.dismiss();
                Toast.makeText(Main2Activity.this, "Election Dates Successfully declared", Toast.LENGTH_LONG).show();

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void createDialog4() {
        dialog4 = new Dialog(this);
        dialog4.setTitle("Select Result Date");
        dialog4.setContentView(R.layout.dialog_add);
        dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvsetDate4 = (TextView) dialog4.findViewById(R.id.tvsetDate);
        setDate4 = (TextView) dialog4.findViewById(R.id.dob1);
        btnAdd4 = (Button) dialog4.findViewById(R.id.btn_add);
        btnCancel4 = (Button) dialog4.findViewById(R.id.btn_cancel);

        tvsetDate4.setTypeface(font);
        setDate4.setTypeface(font1);
        btnAdd4.setTypeface(font);
        btnCancel4.setTypeface(font);

        setDate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dobPicker(setDate4);
            }
        });

        btnAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef4 = database.getReference("result-dates");
                String add = setDate4.getText().toString();

                String key =myRef4.push().getKey();
                Userdetails4 userdetails4 = new Userdetails4();

                userdetails4.setAdd(add);

                myRef4.child(key).setValue(userdetails4);
                setDate4.setText("DD/MM/YYYY");
                dialog4.dismiss();
                Toast.makeText(Main2Activity.this, "Result Dates Successfully declared", Toast.LENGTH_LONG).show();
            }
        });

        btnCancel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
            }
        });
    }

    private void createDialog1() {
        dialog1 = new Dialog(this);
        dialog1.setTitle("Add Results");
        dialog1.setContentView(R.layout.dialog_result);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvParty = (TextView) dialog1.findViewById(R.id.tvPartyName);
        TextView tvSeats = (TextView) dialog1.findViewById(R.id.tvVotes);
        etParty = dialog1.findViewById(R.id.etPartyName);
        etSeats = dialog1.findViewById(R.id.etSeats);
        btnAdd1 = dialog1.findViewById(R.id.btn_add1);
        btnCancel1 = dialog1.findViewById(R.id.btn_cancel1);

        tvParty.setTypeface(font);
        tvSeats.setTypeface(font);
        etParty.setTypeface(font1);
        etSeats.setTypeface(font1);
        btnAdd1.setTypeface(font);
        btnCancel1.setTypeface(font);

        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef2 = database.getReference("election-results");
                String party = etParty.getText().toString();
                String seats = etSeats.getText().toString();

                String key = myRef2.push().getKey();
                Userdetails2 userdetails2 = new Userdetails2();

                userdetails2.setParty(party);
                userdetails2.setSeats(seats);

                myRef2.child(key).setValue(userdetails2);
                etParty.setText("");
                etSeats.setText("");
                dialog1.dismiss();

            }
        });

        btnCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


    }

    private void dobPicker(final TextView eemail) {
        eemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String day1 = Integer.toString(day);
                if (day1.length() == 1) {
                    day1 = "0" + day1;
                }
                day = Integer.parseInt(day1);
                String month1 = Integer.toString(month);
                if (month1.length() == 1) {
                    month1 = "0" + month1;
                }
                month = Integer.parseInt(month1);

                DatePickerDialog dialog = new DatePickerDialog(
                        Main2Activity.this,
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

                String day1 = Integer.toString(dayOfMonth);
                if (day1.length() == 1) {
                    day1 = "0" + day1;
                }
                String month1 = Integer.toString(month);
                if (month1.length() == 1) {
                    month1 = "0" + month1;
                }

                String date = day1+"-"+month1+"-"+year;
                eemail.setText(date, TextView.BufferType.EDITABLE);
            }
        };
    }
}
