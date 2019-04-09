package com.gl.practice.petrolbuddy;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCalenderView extends Fragment {

    private MainActivity mainActivity;
    private DatabaseHelper mNewDatabase;
    private ImageView mPreviousMonth;
    private ImageView mNextMonth;
    private TextView mCurrentMonth;
    private GridView mCalendarGrid;
    private GridCalendarAdapter mCalendarAdapter;

    private String[] month = {"Jan","Feb","Mar","Apr","May","Jun",
                            "Jul","Aug","Sept","Oct","Nov","Dec"};

    public FragmentCalenderView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender_view, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initResources();
        mCalendarAdapter = new GridCalendarAdapter(mainActivity);
        mCalendarGrid.setAdapter(mCalendarAdapter);

        Calendar calendar = Calendar.getInstance();
        mCurrentMonth.setText(month[Calendar.MONTH] +" "+ Calendar.YEAR);
    }

    private void initResources() {
        mPreviousMonth = getView().findViewById(R.id.iv_previous_month);
        mNextMonth = getView().findViewById(R.id.iv_next_month);
        mCurrentMonth = getView().findViewById(R.id.tv_display_current_month);
        mCalendarGrid = getView().findViewById(R.id.gv_calendar_grid);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
        mNewDatabase = mainActivity.newDatabase;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
