package com.gl.practice.petrolbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class GridCalendarAdapter extends BaseAdapter {

    private int mDateArray[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    private LayoutInflater mLayoutInfalter;
    private Context mContext;
    private TextView mDateText;

    GridCalendarAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInfalter = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getItem(int position) {
        return mDateArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInfalter.inflate(R.layout.calendar_grid_item, null);
        }
        mDateText = convertView.findViewById(R.id.tv_date);
        mDateText.setText(Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        return convertView;
    }
}
