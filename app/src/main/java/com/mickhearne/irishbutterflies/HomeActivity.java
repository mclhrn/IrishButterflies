package com.mickhearne.irishbutterflies;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.mickhearne.irishbutterflies.db.ButterflyDataSource;
import com.mickhearne.irishbutterflies.fragments.FeedbackFragment;
import com.mickhearne.irishbutterflies.model.Butterfly;
import com.mickhearne.irishbutterflies.utilities.AnalyticsData;
import com.mickhearne.irishbutterflies.utilities.Const;
import com.mickhearne.irishbutterflies.utilities.JSONPullParser;
import com.mickhearne.irishbutterflies.utilities.MyLocation;
import com.mickhearne.irishbutterflies.utilities.MyToast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by michaelhearne on 10/04/2014.
 */
public class HomeActivity extends Activity implements View.OnClickListener {


    public static double LAT = 0;
    public static double LNG = 0;
    private Typeface font;
    private ButterflyDataSource datasource;
    private static SharedPreferences.Editor editor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        openDB();

        SharedPreferences pref = MyApplication.getAppContext().getSharedPreferences("Irish Butterflies Prefs", 0);
        editor = pref.edit();

        boolean parseData = pref.getBoolean("parseData", true);

        if (parseData) {

            GetData task = new GetData();
            task.execute();
        }

        font = Typeface.createFromAsset(this.getApplicationContext().getAssets(),
                Const.MY_FONT);

        initUI();

        setLocation();
    }


    private void openDB() {
        // open connection to db
        datasource = new ButterflyDataSource(this);
        datasource.open();
    }


    private void createData() throws IOException, JSONException {
        JSONPullParser parser = new JSONPullParser();
        List<Butterfly> butterflies = parser.parseJSON(this);
        for (Butterfly butterfly : butterflies) {
            datasource.create(butterfly);
        }
        editor.putBoolean("parseData", false);
        editor.commit();
    }


    private void initUI() {

        // Set background to white
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        // Get handles to UI objects
        Button reference_guide = (Button) findViewById(R.id.butterfly_icon);
        Button butterflies_seen_btn = (Button) findViewById(R.id.butterfly_seen_icon);
        Button wishlist_btn = (Button) findViewById(R.id.butterfly_wish_icon);
        Button map_view_btn = (Button) findViewById(R.id.map_icon);

        // Set click listeners
        reference_guide.setOnClickListener(this);
        butterflies_seen_btn.setOnClickListener(this);
        wishlist_btn.setOnClickListener(this);
        map_view_btn.setOnClickListener(this);

        // Set Typeface
        reference_guide.setTypeface(font);
        butterflies_seen_btn.setTypeface(font);
        wishlist_btn.setTypeface(font);
        map_view_btn.setTypeface(font);
    }


    @Override
    public void onClick(View v) {
        Intent mIntent = MainActivity.getInstance();
        switch (v.getId()) {
            case R.id.butterfly_icon:
                mIntent.putExtra("fragmentNumber", 0);
                startActivity(mIntent);
                break;
            case R.id.butterfly_seen_icon:
                mIntent.putExtra("fragmentNumber", 1);
                startActivity(mIntent);
                break;
            case R.id.butterfly_wish_icon:
                mIntent.putExtra("fragmentNumber", 2);
                startActivity(mIntent);
                break;
            case R.id.map_icon:
                mIntent = new Intent(this, MapsActivity.class);
                startActivity(mIntent);
                break;
            default:
                break;
        }
    }



    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                createData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void setLocation() {
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                if (null != location) {
                    LAT = location.getLatitude();
                    LNG = location.getLongitude();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.showToast("Problem getting Location");
                        }
                    });
                }
            }
        };

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }


    @Override
    public void onStart() {
        super.onStart();

        // Google Analytics
        AnalyticsData.sendWithScreenName("Home Screen");
    }
}
