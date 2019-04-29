package com.gl.practice.petrolbuddy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gl.practice.petrolbuddy.Adapter.ListViewAdapter;
import com.gl.practice.petrolbuddy.MainActivity;
import com.gl.practice.petrolbuddy.R;

public class FragmentMainList extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainActivity mainActivity;
    private ListViewAdapter mRecyclerAdapter;
    private TextView mNoDataText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = getView().findViewById(R.id.recycler_view);
        mNoDataText = getView().findViewById(R.id.tv_no_data);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerAdapter = new ListViewAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setDatabaseInstance(mainActivity.newDatabase);

        if(mainActivity.newDatabase.getCount() == 0) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mNoDataText.setVisibility(View.VISIBLE);
        }
        else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoDataText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
