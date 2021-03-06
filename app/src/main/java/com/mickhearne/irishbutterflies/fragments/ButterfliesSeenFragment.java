package com.mickhearne.irishbutterflies.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mickhearne.irishbutterflies.R;
import com.mickhearne.irishbutterflies.db.ButterflyDataSource;
import com.mickhearne.irishbutterflies.list.ListViewAdapter;
import com.mickhearne.irishbutterflies.model.Butterfly;
import com.mickhearne.irishbutterflies.utilities.AnalyticsData;
import com.mickhearne.irishbutterflies.utilities.MyToast;

import java.util.List;

/**
 * Created by michaelhearne on 13/04/2014.
 */
public class ButterfliesSeenFragment extends Fragment implements TextWatcher {


    private List<Butterfly> butterflies;
    private boolean seenAtoZ = false;
    private ButterflyDataSource datasource;
    private ArrayAdapter<Butterfly> adapter;
    private AbsListView lv;
    private OnButterflySeenSelectedListener mCallback;
    private int bgColor;
    private View v;


    public static ButterfliesSeenFragment newInstance() {
        ButterfliesSeenFragment f = new ButterfliesSeenFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_butterflies_seen, container, false);

        initScreen();

        return v;
    }


    private void initScreen() {
        datasource = new ButterflyDataSource(getActivity());
        datasource.open();

        butterflies = datasource.findButterfliesSeen();

        if (butterflies.isEmpty()) {
            MyToast.showToast("You have not added any butterflies to this list");
        }


        aToZToggle();

        initUI();

        refreshDisplay();
    }


    private void initUI() {

        // Get handles to UI objects
        lv = (AbsListView) v.findViewById(R.id.list);
        EditText inputSearch = (EditText) v.findViewById(R.id.seen_input_search);

        // Enable filtering on list
        lv.setTextFilterEnabled(true);
        inputSearch.addTextChangedListener(this);

        bgColor = R.color.seen_list_bg;
    }


    private void aToZToggle() {
        // Toggle display a to z or z to a
        ImageButton atoz = (ImageButton) v.findViewById(R.id.seen_atoz);
        atoz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!seenAtoZ) {
                    butterflies = datasource.seenAtoZ();
                    refreshDisplay();
                    seenAtoZ = true;
                } else {
                    butterflies = datasource.findButterfliesSeen();
                    refreshDisplay();
                    seenAtoZ = false;
                }
            }
        });
    }


    public void refreshDisplay() {
        adapter = new ListViewAdapter(getActivity(), butterflies);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Butterfly butterfly = adapter.getItem(position);

                mCallback.OnButterflySeenSelected(butterfly, bgColor);
            }
        });
    }


    public void onResume() {
        super.onResume();

        initScreen();
    }


    @Override
    public void onPause() {
        super.onPause();

        datasource.close();
    }


    @Override
    public void onStart() {
        super.onStart();

        // Google Analytics
        AnalyticsData.sendWithScreenName("Butterfly Seen Screen");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count < before) {
            ((ListViewAdapter) adapter).resetData();
        }
        adapter.getFilter().filter(s.toString());
    }


    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnButterflySeenSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnButterflySeenSelectedListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    public interface OnButterflySeenSelectedListener {
        public void OnButterflySeenSelected(Butterfly selection, int bgColor);
    }
}
