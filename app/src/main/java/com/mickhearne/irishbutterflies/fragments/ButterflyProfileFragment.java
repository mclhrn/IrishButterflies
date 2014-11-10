package com.mickhearne.irishbutterflies.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mickhearne.irishbutterflies.HomeActivity;
import com.mickhearne.irishbutterflies.R;
import com.mickhearne.irishbutterflies.db.ButterflyDataSource;
import com.mickhearne.irishbutterflies.model.Butterfly;
import com.mickhearne.irishbutterflies.utilities.AnalyticsData;
import com.mickhearne.irishbutterflies.utilities.MyToast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ButterflyProfileFragment.OnSlideshowSelectedListener} interface
 * to handle interaction events.
 * Use the {@link ButterflyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButterflyProfileFragment extends Fragment {

    private View v;
    private Butterfly butterfly;
    private ButterflyDataSource datasource;
    boolean isSeen, isWish;
    private OnSlideshowSelectedListener mListener;
    private int bgColor;


    public static ButterflyProfileFragment newInstance() {
        return new ButterflyProfileFragment();
    }


    public static ButterflyProfileFragment newInstance(Butterfly butterfly, int bgColor) {
        ButterflyProfileFragment fragment = new ButterflyProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("butterfly", butterfly);
        args.putInt("bgColor", bgColor);
        fragment.setArguments(args);
        return fragment;
    }


    public ButterflyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            Bundle args = getArguments();
            args.putParcelable("butterfly", savedInstanceState.getParcelable("butterfly"));
            args.putInt("bgColor", savedInstanceState.getInt("bgColor"));
        }

        butterfly = getArguments().getParcelable("butterfly");
        bgColor = getArguments().getInt("bgColor");

        datasource = new ButterflyDataSource(getActivity());
        datasource.open();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_butterfly_profile, container, false);
        setHasOptionsMenu(true);
        checkLists();
        refreshDisplay();
        return v;
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putParcelable("butterfly", butterfly);
        savedState.putInt("bgColor", bgColor);
    }



    public void updateContent(Butterfly mButterfly, int bgColor) {
        this.butterfly = mButterfly;
        this.bgColor = bgColor;
        refreshDisplay();
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(butterfly.getName());
    }


    @Override
    public void onStart() {
        super.onStart();

        // Google Analytics
        AnalyticsData.sendWithScreenName("Butterfly Profile Screen: " + butterfly.getName());
    }


    private void refreshDisplay() {

        // Get handles to UI objects
        TextView tv = (TextView) v.findViewById(R.id.name);
        TextView tv2 = (TextView) v.findViewById(R.id.latin);
        TextView tv3 = (TextView) v.findViewById(R.id.description);
        TextView tv4 = (TextView) v.findViewById(R.id.habitat);
        TextView tv5 = (TextView) v.findViewById(R.id.food_plant);
        TextView tv6 = (TextView) v.findViewById(R.id.distribution);
        TextView tv7 = (TextView) v.findViewById(R.id.flight_period);
        TextView tv8 = (TextView) v.findViewById(R.id.wingspan);
        ImageButton iv = (ImageButton) v.findViewById(R.id.main_profile_image);

        TextView divBar1 = (TextView) v.findViewById(R.id.div_bar_1);
        TextView divBar2 = (TextView) v.findViewById(R.id.div_bar_2);
        TextView divBar3 = (TextView) v.findViewById(R.id.div_bar_3);
        TextView divBar4 = (TextView) v.findViewById(R.id.div_bar_4);

        divBar1.setBackgroundResource(bgColor);
        divBar2.setBackgroundResource(bgColor);
        divBar3.setBackgroundResource(bgColor);
        divBar4.setBackgroundResource(bgColor);

        tv.setText(butterfly.getName());
        tv2.setText(butterfly.getLatinName());
        tv3.setText(butterfly.getDescription());
        tv4.setText(butterfly.getHabitat());
        tv5.setText(butterfly.getFoodplant());
        tv6.setText(butterfly.getDistribution());
        tv7.setText(butterfly.getFlightPeriod());
        tv8.setText(butterfly.getWingspan());

        int imageResource = getResources().getIdentifier(butterfly.getImageLarge() + "_1",
                "drawable", getActivity().getPackageName());
        if (imageResource != 0) {
            iv.setImageResource(imageResource);
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSlideShowSelected(butterfly.getImageLarge(), butterfly.getName());
            }
        });
    }


    private void checkLists() {
        isSeen = datasource.checkSeenlist(butterfly.getId());
        isWish = datasource.checkWishlist(butterfly.getId());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile, menu);


        // Show delete menu item if we came from Butterflies Seen or Wish
        menu.findItem(R.id.delete_from_seen).setVisible(isSeen);
        menu.findItem(R.id.delete_from_wish).setVisible(isWish);

        // Show add menu item if we came from main reference guide
        menu.findItem(R.id.add_to_seen).setVisible(!isSeen);
        menu.findItem(R.id.add_to_wishlist).setVisible(!isWish);

        // Share Sighting
        menu.findItem(R.id.share_sighting).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_seen:
                if (datasource.addToButterfliesSeen(butterfly, HomeActivity.LAT, HomeActivity.LNG)) {
                    checkLists();
                    getActivity().invalidateOptionsMenu();
                    notifyUser(" added to Butterflies Seen List");
                } else {
                    notifyUser(" not added to Butterflies Seen List");
                }
                break;
            case R.id.add_to_wishlist:
                if (datasource.addToWishList(butterfly)) {
                    checkLists();
                    getActivity().invalidateOptionsMenu();
                    notifyUser(" added to Wishlist");
                } else {
                    notifyUser(" not added to Wishlist");
                }
                break;
            case R.id.share_sighting:
                shareSighting();
                break;
            case R.id.delete_from_seen:
                if (isSeen) {
                    if (datasource.removeFromButterfliesSeen(butterfly)) {
                        checkLists();
                        getActivity().invalidateOptionsMenu();
                        notifyUser(" removed from Butterfly Seen List");
                    } else {
                        notifyUser(" not removed from Butterfly Seen list");
                    }
                }
                break;
            case R.id.delete_from_wish:
                if (isWish) {
                    if (datasource.removeFromWishList(butterfly)) {
                        checkLists();
                        getActivity().invalidateOptionsMenu();
                        notifyUser(" removed from Wishlist");
                    } else {
                        notifyUser(" not removed from Wishlist");
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void notifyUser(String mList) {
        MyToast.showToast(butterfly.getName() + mList);
    }


    private void shareSighting() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "I just saw a " + butterfly.getName()
                + " using the Irish Butterflies App. ";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Irish Butterflies Sighting");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSlideshowSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSlideshowSelectedListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSlideshowSelectedListener {
        public void onSlideShowSelected(String selection, String name);
    }
}