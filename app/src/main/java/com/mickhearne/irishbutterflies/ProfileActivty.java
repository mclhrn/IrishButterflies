package com.mickhearne.irishbutterflies;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mickhearne.irishbutterflies.fragments.ButterflyProfileFragment;
import com.mickhearne.irishbutterflies.fragments.ButterfliesFragment;
import com.mickhearne.irishbutterflies.fragments.ButterfliesSeenFragment;
import com.mickhearne.irishbutterflies.fragments.ButterflyWishlistFragment;
import com.mickhearne.irishbutterflies.model.Butterfly;


public class ProfileActivty extends Activity implements
        ButterflyProfileFragment.OnSlideshowSelectedListener,
        ButterfliesFragment.OnButterflySelectedListener,
        ButterfliesSeenFragment.OnButterflySeenSelectedListener,
        ButterflyWishlistFragment.OnButterflyWishSelectedListener {


    private Butterfly butterfly;
    private int bgColor;
    private boolean detailPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle b = getIntent().getExtras();
        butterfly = b.getParcelable("com.mickhearne.irishbutterflies.model.Butterfly");
        bgColor = b.getInt("bgColor");

        // Determine if layout is landscape or not
        if (findViewById(R.id.butterfly_list_container) != null) {

            // We are in layout mode
            detailPage = true;

        } else {

            // Not in layout mode
            detailPage = false;

        }
        buildUI();
    }


    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
    }


    private void buildUI() {

        getFragmentManager().beginTransaction()
                .add(R.id.container, ButterflyProfileFragment.newInstance(butterfly, bgColor))
                .commit();

        if (detailPage) {

            Fragment fragment = null;

            switch (bgColor) {
                case R.color.home_button_butterflies:
                    fragment = ButterfliesFragment.newInstance();
                    break;
                case R.color.seen_list_bg:
                    fragment = ButterfliesSeenFragment.newInstance();
                    break;
                case R.color.wish_list_bg:
                    fragment = ButterflyWishlistFragment.newInstance();
                    break;
                default:
                    break;
            }

            getFragmentManager().beginTransaction()
                    .add(R.id.butterfly_list_container, fragment)
                    .commit();
        }
    }


    @Override
    public void onSlideshowSelected(String selection) {
        Intent mIntent = new Intent(this, SlideShowActivity.class);
        mIntent.putExtra("name", selection);
        startActivity(mIntent);
    }


    @Override
    public void onButterflySelected(Butterfly selection, int bgColor) {

        Fragment fragment = null;
        destroyFragment(fragment);

        fragment = ButterflyProfileFragment.newInstance(selection, bgColor);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    private void destroyFragment(Fragment fragment) {

        try {
            fragment = this.getFragmentManager().findFragmentById(R.id.container);
            if (fragment != null) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        } catch (IllegalStateException e) {
            Log.i("butterfly", "Fail destroying Profile Fragment");
        }
    }


    @Override
    public void OnButterflySeenSelected(Butterfly selection, int bgColor) {
        onButterflySelected(selection, bgColor);
    }


    @Override
    public void onButterflyWishSelected(Butterfly selection, int bgColor) {
        onButterflySelected(selection, bgColor);
    }

}