package com.example.android.parliamentaryelections;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerviewAdapter2 extends RecyclerView.Adapter<RecyclerviewAdapter2.MyHolder> {

    List<Listdata2> listdata2;

    public RecyclerviewAdapter2(List<Listdata2> listdata2) {
        this.listdata2 = listdata2;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview1, parent, false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        Listdata2 data = listdata2.get(position);
        holder.vparty.setText(data.getParty());
        holder.vseats.setText(data.getSeats());
    }

    @Override
    public int getItemCount() {
        return listdata2.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView vparty , vseats;

        public MyHolder(View itemView) {
            super(itemView);
            vparty = (TextView) itemView.findViewById(R.id.vparty);
            vseats = (TextView) itemView.findViewById(R.id.vseats);

        }
    }

}
