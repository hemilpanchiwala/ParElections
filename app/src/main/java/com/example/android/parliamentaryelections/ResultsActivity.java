package com.example.android.parliamentaryelections;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.parliamentaryelections.Main2Activity.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ResultsActivity extends Fragment {

    private Spinner spinner;
    private Button btnFind;
    private String s1;
    private TextView pollingDates;
    private TextView tvPolling, tvResults, results, tvPollingBooth;
    FirebaseDatabase database;
    DatabaseReference myRef, myRef1, myRef2;
    RecyclerView recyclerView;
    List<Listdata2> list;
    Calendar cal;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public ResultsActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_results, container, false);


        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Sansation-Bold.ttf");
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Sansation-Light.ttf");

        spinner = (Spinner) view.findViewById(R.id.spinnerPollingBooth);
        btnFind = (Button) view.findViewById(R.id.btnFind);
        pollingDates = (TextView) view.findViewById(R.id.tvPolingDates);
        tvPolling = (TextView) view.findViewById(R.id.tvPolling);
        tvResults = (TextView) view.findViewById(R.id.tvResults);
        tvPollingBooth = (TextView) view.findViewById(R.id.tvPollingBooth);
        results = (TextView) view.findViewById(R.id.results);

        tvPolling.setTypeface(font1);
        pollingDates.setTypeface(font);
        tvPollingBooth.setTypeface(font);
        btnFind.setTypeface(font);
        tvResults.setTypeface(font);
        results.setTypeface(font1);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("election-dates");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        Userdetails1 userdetails1 = dataSnapshot1.getValue(Userdetails1.class);
                        String add = userdetails1.getAdd();
                        pollingDates.setText(add);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        myRef2 = database.getReference("result-dates");

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    Userdetails1 userdetails1 = dataSnapshot1.getValue(Userdetails1.class);
                    String add = userdetails1.getAdd();
                    results.setText(add);

                    cal = Calendar.getInstance();

                    cal.set(2018, 1, 8, 9, 59, 0);

                    alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getActivity(), AlertReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

   /*     Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Date c = Calendar.getInstance().getTime();
                                System.out.println("Current time => " + c);

                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String formattedDate = df.format(c);

                                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                                Date currentLocalTime = cal.getTime();
                                DateFormat date = new SimpleDateFormat("HH:mm:ss a");
                                date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

                                Toast.makeText(getActivity(), formattedDate, Toast.LENGTH_LONG).show();
                                String localTime = date.format(currentLocalTime);

                                String polling = pollingDates.getText().toString();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && formattedDate.equals(polling) &&
                                        localTime.equals("08:00:00 PM")) {
                                    createChannel();
                                }
                            }
                        });
                    }
                }catch (InterruptedException e){ }
            }
        };   */

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addListenerOnSpinnerItemSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = String.valueOf(spinner.getSelectedItem());
                if (s1.equals("Andhra Pradesh")){
                    Uri uri = Uri.parse("http://www.elections.in/andhra-pradesh/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Arunachal Pradesh")){
                    Uri uri = Uri.parse("http://ceoarunachal.nic.in/listofpollingstations.html");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Assam")){
                    Uri uri = Uri.parse("http://www.elections.in/assam/polling-booths/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Bihar")){
                    Uri uri = Uri.parse("http://www.elections.in/bihar/polling-booths/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Chattisgarh")){
                    Uri uri = Uri.parse("http://election.cg.nic.in/elesrch/acpartsection.aspx");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Goa")){
                    Uri uri = Uri.parse("http://www.elections.in/goa/polling-booth/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Gujarat")){
                    Uri uri = Uri.parse("https://erms.gujarat.gov.in/ceo-gujarat/master/PSPSL_english.aspx");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Haryana")){
                    Uri uri = Uri.parse("http://www.elections.in/haryana/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Himachal Pradesh")){
                    Uri uri = Uri.parse("http://himachal.nic.in/index2.php?lang=1&dpt_id=6&level=1&lid=264");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Jammu & Kashmir")){
                    Uri uri = Uri.parse("http://www.elections.in/jammu-and-kashmir/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Jharkhand")){
                    Uri uri = Uri.parse("http://www.elections.in/jharkhand/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Karnataka")){
                    Uri uri = Uri.parse("http://www.ceokarnataka.kar.nic.in/Sms_New.aspx");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Kerala")){
                    Uri uri = Uri.parse("http://www.elections.in/kerala/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Madhya Pradesh")){
                    Uri uri = Uri.parse("http://www.elections.in/madhya-pradesh/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Maharashtra")){
                    Uri uri = Uri.parse("https://ceo.maharashtra.gov.in/Lists/ListPSs.aspx");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Manipur")){
                    Uri uri = Uri.parse("http://www.elections.in/manipur/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Meghalaya")){
                    Uri uri = Uri.parse("http://www.elections.in/meghalaya/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Mizoram")){
                    Uri uri = Uri.parse("https://s332b30a250abd6331e03a2a1f16466346.s3waas.gov.in/list-of-polling-station/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Nagaland")){
                    Uri uri = Uri.parse("http://www.elections.in/nagaland/polling-booths/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Orissa")){
                    Uri uri = Uri.parse("http://www.elections.in/orissa/polling-booth/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Punjab")){
                    Uri uri = Uri.parse("http://www.elections.in/punjab/polling-booth/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Rajasthan")){
                    Uri uri = Uri.parse("http://www.elections.in/rajasthan/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Sikkim")){
                    Uri uri = Uri.parse("http://www.elections.in/sikkim/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Tamil Nadu")){
                    Uri uri = Uri.parse("http://www.elections.in/tamil-nadu/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Telangana")){
                    Uri uri = Uri.parse("http://www.elections.in/telangana/polling-stations/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Tripura")){
                    Uri uri = Uri.parse("http://www.elections.in/tripura/polling-booths/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Uttarakhand")){
                    Uri uri = Uri.parse("http://election.uk.gov.in/pdf_roll/PollingStation/PS-2018/Uttranchal_pdf_page.htm");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("Uttar Pradesh")){
                    Uri uri = Uri.parse("http://www.elections.in/uttar-pradesh/polling-booths/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else if (s1.equals("West Bengal")){
                    Uri uri = Uri.parse("http://www.elections.in/west-bengal/polling-booths/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerResult);
        myRef1 = database.getReference("election-results");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    Userdetails2 userdetails2 = dataSnapshot1.getValue(Userdetails2.class);
                    Listdata2 listdata2 = new Listdata2();
                    String party = userdetails2.getParty();
                    String seats = userdetails2.getSeats();
                    listdata2.setParty(party);
                    listdata2.setSeats(seats);
                    list.add(listdata2);
                }

                RecyclerviewAdapter2 recycler = new RecyclerviewAdapter2(list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recycler);
                if (recycler.getItemCount() != 0) {
                    results.setText("Click above to see the Results");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

     //   thread.start();


        return view;
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }


}
