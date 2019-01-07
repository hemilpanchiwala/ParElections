package com.example.android.parliamentaryelections;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity  {

    private Button btnLogout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference myRef, myRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // setUpUIViews();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("election-dates");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    Userdetails1 userdetails1 = dataSnapshot1.getValue(Userdetails1.class);
                    String add = userdetails1.getAdd();
                    if (add != null && !add.equals("DD/MM/YYYY")) {
                        Dialog dialog = new Dialog(Login.this);
                        dialog.setTitle("Election Dates Declared");
                        dialog.setContentView(R.layout.dialog_message);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        Window dialogWindow = dialog.getWindow();
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        dialogWindow.setGravity(Gravity.CENTER);

                        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Bold.ttf");
                        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Sansation-Light.ttf");

                        TextView declareDates = (TextView) dialog.findViewById(R.id.declareDates);
                        TextView tvDates = (TextView) dialog.findViewById(R.id.tvDates);
                        TextView declareDates1 = (TextView) dialog.findViewById(R.id.declareDates1);
                        TextView tvPollling1 = (TextView) dialog.findViewById(R.id.tvPolling1);
                        TextView tvPollling2 = (TextView) dialog.findViewById(R.id.tvPolling2);

                        declareDates.setTypeface(font1);
                        declareDates1.setTypeface(font1);
                        tvDates.setTypeface(font);
                        tvPollling1.setTypeface(font1);
                        tvPollling2.setTypeface(font1);

                        tvDates.setText(add);
                        dialog.show();

                        dialog.setCanceledOnTouchOutside(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));
        }

       // btnLogout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_profile:

        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpdatesActivity(), "UPDATES");
        adapter.addFragment(new ResultsActivity(), "POLLING INFORMATION & RESULTS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

 /*   private void setUpUIViews() {
        btnLogout = (Button) findViewById(R.id.btnLogout);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(Login.this, MainActivity.class
            ));
        }
    }  */
}
