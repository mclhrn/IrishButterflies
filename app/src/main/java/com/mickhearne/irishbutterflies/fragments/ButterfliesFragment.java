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

import java.util.List;

public class ButterfliesFragment extends Fragment implements TextWatcher {


    private List<Butterfly> butterflies;
    private boolean refAtoZ = false;
    private ButterflyDataSource datasource;
    public final static String LOGTAG = null;
    private ArrayAdapter<Butterfly> adapter;
    private View v;
    private AbsListView lv;
    private int bgColor;
    OnButterflySelectedListener mCallback;

    public static ButterfliesFragment newInstance() {
        ButterfliesFragment f = new ButterfliesFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_butterflies, container, false);

        openDB();

        initUI();

        getButterflies();

        aToZToggle();

        refreshDisplay();

        return v;
    }


    private void initUI() {
        lv = (AbsListView) v.findViewById(R.id.list);
        lv.setTextFilterEnabled(true);
        EditText inputSearch = (EditText) v.findViewById(R.id.ref_input_search);
        inputSearch.addTextChangedListener(this);
        bgColor = R.color.home_button_butterflies;
    }


    private void openDB() {
        // open connection to db
        datasource = new ButterflyDataSource(getActivity());
        datasource.open();
    }


    private void getButterflies() {
        // get list of butterflies from db
        butterflies = datasource.findAll();
    }


    private void aToZToggle() {
        // Toggle display a to z or z to a
        ImageButton atoz = (ImageButton) v.findViewById(R.id.ref_atoz);
        atoz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!refAtoZ) {
                    butterflies = datasource.refAtoZ();
                    refreshDisplay();
                    refAtoZ = true;
                } else {
                    butterflies = datasource.findAll();
                    refreshDisplay();
                    refAtoZ = false;
                }
            }
        });
    }


    private void refreshDisplay() {
        adapter = new ListViewAdapter(getActivity(), butterflies);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Butterfly butterfly = adapter.getItem(position);
                mCallback.onButterflySelected(butterfly, bgColor);
                lv.setItemChecked(position, true);
            }
        });
    }


    public void onResume() {
        super.onResume();
        datasource.open();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Google Analytics
        AnalyticsData.sendWithScreenName("Butterfly Reference Guide Screen");
    }


    @Override
    public void onPause() {
        super.onPause();
        datasource.close();
    }


    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onTextChanged(CharSequence cs, int start, int before, int count) {
        if (count < before) {
            ((ListViewAdapter) adapter).resetData();
        }
        adapter.getFilter().filter(cs.toString());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnButterflySelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnButterflySelectedListener");
        }
    }


    public interface OnButterflySelectedListener {
        public void onButterflySelected(Butterfly selection, int bgColor);
    }
}