package com.gl.practice.petrolbuddy;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MileageDetails extends Fragment {

    private DatabaseHelper mDatabase;
    private TextView mMileageText;
    private Cursor mCursor;
    private MainActivity mainActivity;

    public MileageDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mileage_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
        mDatabase = mainActivity.newDatabase;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMileageText = getView().findViewById(R.id.mileage_value);
        mCursor = mDatabase.getData();
        if (mCursor.getCount() == 0) {
            mMileageText.setText("No data");
        } else if (mCursor.getCount() == 1) {
            mMileageText.setText("Only one entry");
        } else {
            Double mileage = calculateMileage(mCursor);
            mMileageText.setText(String.format("%.2f", mileage));
        }
    }

    private Double calculateMileage(Cursor cursor) {
        double km1, km2, petrol, mileage = 0;
        int index_km, index_petrol;
        index_km = cursor.getColumnIndex("km");
        index_petrol = cursor.getColumnIndex("Petrol");
        while (!cursor.isLast()) {
            km1 = cursor.getDouble(index_km);
            petrol = cursor.getDouble(index_petrol);
            cursor.moveToNext();
            km2 = cursor.getDouble(index_km);
            mileage += ((km2 - km1) / petrol);
        }
        mileage /= cursor.getCount() - 1;
        return mileage;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
