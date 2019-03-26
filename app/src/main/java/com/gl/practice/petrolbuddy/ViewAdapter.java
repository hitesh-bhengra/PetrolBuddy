package com.gl.practice.petrolbuddy;

import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {

    private DatabaseHelper myDatabase;
    private int index_date, index_petrol, index_price, index_km;

    public void setDatabaseInstance(DatabaseHelper newDatabase) {
        this.myDatabase = newDatabase;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_date_value;
        private TextView tv_petrol_value;
        private TextView tv_price_value;
        private TextView tv_km_value;
        private RelativeLayout rl_list_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date_value = itemView.findViewById(R.id.tv_date_value);
            tv_petrol_value= itemView.findViewById(R.id.tv_petrol_value);
            tv_price_value = itemView.findViewById(R.id.tv_price_value);
            tv_km_value = itemView.findViewById(R.id.tv_km_value);
            rl_list_item = itemView.findViewById(R.id.list_item_layout);
        }
    }

    @Override
    public int getItemCount() {
        if (myDatabase.getCount() == 0)
            return 0;
        else return myDatabase.getCount();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Cursor cursor = myDatabase.getListData();

        index_date = cursor.getColumnIndex("Date");
        index_petrol = cursor.getColumnIndex("Petrol");
        index_price = cursor.getColumnIndex("Price");
        index_km = cursor.getColumnIndex("km");

        cursor.moveToPosition(i);

        myViewHolder.tv_date_value.setText(cursor.getString(index_date));
        myViewHolder.tv_petrol_value.setText(String.format("%.2f",cursor.getDouble(index_petrol))+" lit.");
        myViewHolder.tv_price_value.setText("₹ "+String.format("%.2f",cursor.getDouble(index_price)));
        myViewHolder.tv_km_value.setText(String.format("%.2f",cursor.getDouble(index_km))+" km");

        if(i%2 != 0) {
            myViewHolder.rl_list_item.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
    }
}
