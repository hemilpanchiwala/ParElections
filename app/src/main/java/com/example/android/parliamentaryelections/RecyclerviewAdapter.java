package com.example.android.parliamentaryelections;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder>{

    List<Listdata> listdata;
    Context context;

    public RecyclerviewAdapter(List<Listdata> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        Listdata data = listdata.get(position);
        holder.vname.setText(data.getName());
        holder.vemail.setText(data.getEmail());
        holder.vaddress.setText(data.getAddress());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


        class MyHolder extends RecyclerView.ViewHolder{
            TextView vname , vaddress,vemail;
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Sansation-Bold.ttf");


            public MyHolder(View itemView) {
                super(itemView);
                vname = (TextView) itemView.findViewById(R.id.vname);
                vname.setTypeface(font);
                vemail = (TextView) itemView.findViewById(R.id.vemail);
                vemail.setTypeface(font);
                vaddress = (TextView) itemView.findViewById(R.id.vaddress);
                vaddress.setTypeface(font);
            }
        }
}