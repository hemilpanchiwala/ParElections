package com.example.android.parliamentaryelections;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RecyclerviewAdapter3 extends RecyclerView.Adapter<RecyclerviewAdapter3.MyHolder> {

    List<Listdata3> listdata3;
    Context context;


    public RecyclerviewAdapter3(List<Listdata3> listdata3, Context context) {
        this.listdata3 = listdata3;
        this.context = context;
    }

    @Override
    public RecyclerviewAdapter3.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview3,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        Listdata3 data = listdata3.get(position);
        holder.name.setText(data.getName4());
        holder.aadhar.setText(data.getAadhar4());
        holder.username.setText(data.getUsername4());
        holder.dob.setText(data.getDob4());
        holder.mobile.setText(data.getMobile4());
        holder.caddress.setText(data.getCaddress4());
        holder.paddress.setText(data.getPaddress4());
        holder.rname.setText(data.getRname4());
        holder.relation.setText(data.getRelation4());

    }

    @Override
    public int getItemCount() {
        return listdata3.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, aadhar, username, dob, mobile, caddress, paddress, rname, relation;
        TextView name1, aadhar1, username1, dob1, mobile1, caddress1, paddress1, rname1, relation1;
        Button btnApprove, btnRemove;
        FirebaseAuth firebaseAuth;
        FirebaseStorage storage;
        StorageReference storageReference;
        FirebaseDatabase firebaseDatabase;



        public MyHolder(View itemView) {
            super(itemView);

            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Sansation-Bold.ttf");
            Typeface font1 = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Sansation-Light.ttf");

            name = (TextView) itemView.findViewById(R.id.name);
            name.setTypeface(font);
            aadhar = (TextView) itemView.findViewById(R.id.aadhar);
            aadhar.setTypeface(font);
            username = (TextView) itemView.findViewById(R.id.username);
            username.setTypeface(font);
            dob = (TextView) itemView.findViewById(R.id.dob);
            dob.setTypeface(font);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            mobile.setTypeface(font);
            caddress = (TextView) itemView.findViewById(R.id.caddress);
            caddress.setTypeface(font);
            paddress = (TextView) itemView.findViewById(R.id.paddress);
            paddress.setTypeface(font);
            rname = (TextView) itemView.findViewById(R.id.rname);
            rname.setTypeface(font);
            relation = (TextView) itemView.findViewById(R.id.torelation);
            relation.setTypeface(font);

            name1 = (TextView) itemView.findViewById(R.id.tvname);
            name1.setTypeface(font1);
            aadhar1 = (TextView) itemView.findViewById(R.id.tvaadhar);
            aadhar1.setTypeface(font1);
            username1 = (TextView) itemView.findViewById(R.id.tvusername);
            username1.setTypeface(font1);
            dob1 = (TextView) itemView.findViewById(R.id.tvdob);
            dob1.setTypeface(font1);
            mobile1 = (TextView) itemView.findViewById(R.id.tvmobile);
            mobile1.setTypeface(font1);
            caddress1 = (TextView) itemView.findViewById(R.id.tvcaddress1);
            caddress1.setTypeface(font1);
            paddress1 = (TextView) itemView.findViewById(R.id.tvpaddress1);
            paddress1.setTypeface(font1);
            rname1 = (TextView) itemView.findViewById(R.id.tvrname);
            rname1.setTypeface(font1);
            relation1 = (TextView) itemView.findViewById(R.id.tvrelation);
            relation1.setTypeface(font1);



            btnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemove);

            btnApprove.setTypeface(font);
            btnRemove.setTypeface(font);

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    firebaseAuth = FirebaseAuth.getInstance();
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.getReference();

                    firebaseAuth.createUserWithEmailAndPassword(username.getText().toString(), aadhar.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(v.getContext(), "User Verified Successfully", Toast.LENGTH_LONG).show();
                                    btnApprove.setText("Approved");
                                    btnApprove.setEnabled(false);

                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef2 = firebaseDatabase.getReference("Users1");
                                    Query mQuery2 = myRef2.orderByChild("aadhar4").equalTo(aadhar.getText().toString());
                                    mQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                                ds.getRef().removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(v.getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });



                }

            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = firebaseDatabase.getReference("Users");
                    Query mQuery = myRef.orderByChild("aadhar4").equalTo(aadhar.getText().toString());
                    mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(v.getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    DatabaseReference myRef1 = firebaseDatabase.getReference("Users1");
                    Query mQuery1 = myRef1.orderByChild("aadhar4").equalTo(aadhar.getText().toString());
                    mQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(v.getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(v.getContext(), "Successfully Removed", Toast.LENGTH_LONG).show();
                    listdata3.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    notifyItemRangeChanged(getPosition(), listdata3.size());

                }
            });



            //      btnApprove.setTag(1, itemView);
            //    btnRemove.setTag(2, itemView);
            //     btnApprove.setOnClickListener(this);
            //     btnRemove.setOnClickListener(this);
        }







   /*     @Override
        public void onClick(final View v) {
            if (v.getId() == btnApprove.getId()) {
                View tempview = (View) btnApprove.getTag(1);
                TextView username1 = (TextView) tempview.findViewById(R.id.username);
                TextView aadhar1 = (TextView) tempview.findViewById(R.id.aadhar);
                firebaseAuth.createUserWithEmailAndPassword(username1.getText().toString(), aadhar1.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(v.getContext(), "Verified Successfully", Toast.LENGTH_LONG).show();
                            }
                        });
            }  */
    }
}