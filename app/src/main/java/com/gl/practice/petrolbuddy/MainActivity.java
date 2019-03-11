package com.gl.practice.petrolbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentMainList.OnFragmentInteractionListener,
        MileageDetails.OnFragmentInteractionListener, CalenderView.OnFragmentInteractionListener {

    /*
     * Initializing all the components
     */
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private AlertDialog.Builder mAlertInputDialog;
    private EditText mPetrol;
    private EditText mPrice;
    private EditText mKMReading;
    private DatePicker mDate;

    private String toastMessage;
    private boolean insertResponse;

    private String petrolText;
    private String priceText;
    private String kmText;
    private String dateText;
    private Float receivedKMValue;
    private int column_km_id;

    public DatabaseHelper newDatabase;

    private LayoutInflater layoutInflater;
    private View alertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeAll();

        newDatabase = new DatabaseHelper(this);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        /*
         * Adding fragments to adapter
         */
        mPagerAdapter.addFragment(new FragmentMainList(), "List");
        mPagerAdapter.addFragment(new MileageDetails(), "Mileage");
        mPagerAdapter.addFragment(new CalenderView(), "Calender");

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*
     * Function to invoke data addition
     */
    public void floatingAction(View view) {
        mAlertInputDialog = new AlertDialog.Builder(this);
        mAlertInputDialog.setTitle("Input Details :-");

        layoutInflater = LayoutInflater.from(this);
        alertView = layoutInflater.inflate(R.layout.alertdialog_layout, null);
        mAlertInputDialog.setView(alertView);

        setIdAfterAlertDialogInflates();

        final Cursor cursor = newDatabase.getKM();

        column_km_id = cursor.getColumnIndex("km");

        if (cursor.getCount() != 0) {

            Log.d("KM index value", Integer.toString(column_km_id));
            receivedKMValue = cursor.getFloat(column_km_id);
            Log.d("KM return value", Float.toString(receivedKMValue));
            mKMReading.setText(Float.toString(receivedKMValue));

        } else {
            mKMReading.setText("0");
        }

        mAlertInputDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getStringFromAlertDialog();
                if (dateText.isEmpty() || petrolText.isEmpty() || priceText.isEmpty() ||
                        kmText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Empty",
                            Toast.LENGTH_SHORT).show();
                } else if (cursor.getCount() != 0) {
                    if (Float.parseFloat(kmText) <= receivedKMValue)
                        Toast.makeText(MainActivity.this, "KM cannot be less",
                                Toast.LENGTH_SHORT).show();
                    else {


                        Log.d("Text Values", mPetrol.getText().toString());
                        Log.d("Text Values", mPrice.getText().toString());
                        Log.d("Text Values", mKMReading.getText().toString());
                        Log.d("Text Values", dateText);
                        insertResponse = newDatabase.insertData(dateText,
                                Float.parseFloat(petrolText), Float.parseFloat(priceText),
                                Float.parseFloat(kmText));
                        if (insertResponse) {
                            toastMessage = "Saved Successfully";
                        } else
                            toastMessage = "Entry Failed";
                        Toast.makeText(MainActivity.this, toastMessage,
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    insertResponse = newDatabase.insertData(dateText,
                            Float.parseFloat(petrolText), Float.parseFloat(priceText),
                            Float.parseFloat(kmText));
                    if (insertResponse) {
                        toastMessage = "Saved Successfully";
                    } else
                        toastMessage = "Entry Failed";
                    Toast.makeText(MainActivity.this, toastMessage,
                            Toast.LENGTH_SHORT).show();
                }


                mPagerAdapter.notifyDataSetChanged();
            }
        });

        mAlertInputDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Action Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * To make the button color of the AlertDialog Black
         */
        AlertDialog dialog = mAlertInputDialog.create();
        dialog.show();
        Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        saveButton.setTextColor(Color.BLACK);
        Button cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setTextColor(Color.BLACK);
    }

    private void setIdAfterAlertDialogInflates() {
        mPetrol = alertView.findViewById(R.id.petrol_text);
        mPrice = alertView.findViewById(R.id.price_text);
        mKMReading = alertView.findViewById(R.id.km_text);
        mDate = alertView.findViewById(R.id.date_picker);
    }

    private void getStringFromAlertDialog() {

        petrolText = mPetrol.getText().toString();
        priceText = mPrice.getText().toString();
        kmText = mKMReading.getText().toString();
        dateText = Integer.toString(mDate.getDayOfMonth()) + "/" +
                Integer.toString(mDate.getMonth()) + "/" + Integer.toString(mDate.getYear());
    }

    /*
     *Initialize all the components
     */
    private void InitializeAll() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }
}
