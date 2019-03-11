package com.gl.practice.petrolbuddy;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {

    private DatabaseHelper myDatabase;
    private int index_date, index_petrol, index_price, index_km;

    public void setDatabaseInstance(DatabaseHelper newDatabase) {
        this.myDatabase = newDatabase;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private TextView tv_petrol;
        private TextView tv_price;
        private TextView tv_km;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_petrol = itemView.findViewById(R.id.tv_petrol);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_km = itemView.findViewById(R.id.tv_km);
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
        Cursor cursor = myDatabase.viewData();

        index_date = cursor.getColumnIndex("Date");
        index_petrol = cursor.getColumnIndex("Petrol");
        index_price = cursor.getColumnIndex("Price");
        index_km = cursor.getColumnIndex("km");

        cursor.moveToPosition(i);

        myViewHolder.tv_date.setText(cursor.getString(index_date));
        myViewHolder.tv_petrol.setText(Double.toString(cursor.getDouble(index_petrol)));
        myViewHolder.tv_price.setText(Double.toString(cursor.getDouble(index_price)));
        myViewHolder.tv_km.setText(String.format("%.1f",cursor.getDouble(index_km)));

        Log.d("Index Values", Integer.toString(index_date));
        Log.d("Index Values", Integer.toString(index_petrol));
        Log.d("Index Values", Integer.toString(index_price));
        Log.d("Index Values", Integer.toString(index_km));


    }

}
