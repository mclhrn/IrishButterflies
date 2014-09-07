package com.mickhearne.irishbutterflies;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mickhearne.irishbutterflies.db.ButterflyDataSource;
import com.mickhearne.irishbutterflies.model.ButterfliesSeenModel;
import com.mickhearne.irishbutterflies.utilities.AnalyticsData;

import java.util.List;

public class MapsActivity extends FragmentActivity {


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    @Override
    public void onStart() {
        super.onStart();

        // Google Analytics
        AnalyticsData.sendWithScreenName("Map Screen");
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        ButterflyDataSource datasource = new ButterflyDataSource(this);
        datasource.open();

        List<ButterfliesSeenModel> butterfliesSeen = datasource.findButterfliesForMap();

        LatLng myMarker;
        LatLng CURRENT_LOC = new LatLng(HomeActivity.LAT, HomeActivity.LNG);

        for (int i = 0; i < butterfliesSeen.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(myMarker = new LatLng(butterfliesSeen.get(i)
                    .getLatitude(),
                    butterfliesSeen.get(i)
                    .getLongitude()))
                    .title(butterfliesSeen.get(i)
                    .getName()));
        }

        mMap.addMarker(new MarkerOptions().position(CURRENT_LOC).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_LOC, 17));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }
}
