package com.mickhearne.irishbutterflies;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.View;

import com.mickhearne.irishbutterflies.fragments.ButterflyProfileFragment;
import com.mickhearne.irishbutterflies.fragments.ButterfliesFragment;
import com.mickhearne.irishbutterflies.fragments.ButterfliesSeenFragment;
import com.mickhearne.irishbutterflies.fragments.ButterflyWishlistFragment;
import com.mickhearne.irishbutterflies.fragments.FeedbackFragment;
import com.mickhearne.irishbutterflies.fragments.NavigationDrawerFragment;
import com.mickhearne.irishbutterflies.model.Butterfly;
import com.mickhearne.irishbutterflies.utilities.MyToast;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ButterfliesFragment.OnButterflySelectedListener,
        ButterfliesSeenFragment.OnButterflySeenSelectedListener,
        ButterflyWishlistFragment.OnButterflyWishSelectedListener,
        FeedbackFragment.DialogClickListener {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private static Context context = MyApplication.getAppContext();
    private static Intent instance = null;
    private int currentFrag;



    public static Intent getInstance() {
        if (instance == null) {
            instance = new Intent(context, MainActivity.class);
            instance.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return instance;
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putInt("fragmentNumber", currentFrag);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mIntent = getIntent();
        if (null != mIntent) {
            currentFrag = mIntent.getIntExtra("fragmentNumber", 0);
        }

        if (null != savedInstanceState) {
            currentFrag = savedInstanceState.getInt("fragmentNumber");
        }

        addDrawer();

        mTitle = getTitle();
    }


    private void addDrawer() {

        /*
      Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), currentFrag);

        onNavigationDrawerItemSelected(currentFrag);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = ButterfliesFragment.newInstance();
                loadFragment(fragment, position);
                break;
            case 1:
                fragment = ButterfliesSeenFragment.newInstance();
                loadFragment(fragment, position);
                break;
            case 2:
                fragment = ButterflyWishlistFragment.newInstance();
                loadFragment(fragment, position);
                break;
            case 3:
                Intent mIntent = new Intent(this, MapsActivity.class);
                startActivity(mIntent);
                break;
            case 4:
                showDialog();
                break;
            default:
                break;
        }
    }


    public void loadFragment(Fragment fragment, int position) {
        if (fragment != null) {
            currentFrag = position;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .setCustomAnimations(
                            R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit,
                            R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit)
                    .commit();
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }


    /**
     * Butterfly selected from List
     */
    @Override
    public void onButterflySelected(Butterfly selection, int bgColor) {
            Intent mIntent = new Intent(this, ProfileActivty.class);
            mIntent.putExtra("com.mickhearne.irishbutterflies.model.Butterfly", selection);
            mIntent.putExtra("bgColor", bgColor);
            startActivity(mIntent);
    }


    @Override
    public void onButterflyWishSelected(Butterfly selection, int bgColor) {
        onButterflySelected(selection, bgColor);
    }


    @Override
    public void OnButterflySeenSelected(Butterfly selection, int bgColor) {
        onButterflySelected(selection, bgColor);
    }





    /**
     * Feedback Dialog Fragment
     */
    // Display feedback dialog
    void showDialog() {
        DialogFragment newFragment = FeedbackFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }


    // Positive Click on Feedback Dialog
    @Override
    public void onPositiveClick() {
        launchMarket();
    }


    // Negative Click on Feedback Dialog
    @Override
    public void onNegativeClick() {

        // Do Nothing

    }


    // Positive Action on Feedback Dialog click - Launch Google Play to rate app
    private void launchMarket() {

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            MyToast.showToast("Problem connecting to Google Play");
        }
    }
}
