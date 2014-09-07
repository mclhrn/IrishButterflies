package com.mickhearne.irishbutterflies.utilities;

import android.content.Context;

import com.mickhearne.irishbutterflies.model.Butterfly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONPullParser {


	private List<Butterfly> birds = new ArrayList<Butterfly>();


	public List<Butterfly> parseJSON(Context context) throws JSONException, IOException {

        JSONObject obj = parseJSONData(context);
        JSONArray mArray = obj.getJSONArray("butterflies");

        for (int i = 0; i < mArray.length(); i++) {

            Butterfly bird = new Butterfly();

            bird.setName(mArray.getJSONObject(i).getString("name"));
            bird.setLatinName(mArray.getJSONObject(i).getString("latin"));
            bird.setDescription(mArray.getJSONObject(i).getString("description"));
            bird.setHabitat(mArray.getJSONObject(i).getString("habitat"));
            bird.setFoodplant(mArray.getJSONObject(i).getString("foodplant"));
            bird.setDistribution(mArray.getJSONObject(i).getString("distribution"));
            bird.setFlightPeriod(mArray.getJSONObject(i).getString("flight_period"));
            bird.setWingspan(mArray.getJSONObject(i).getString("wingspan"));
            bird.setImageLarge(mArray.getJSONObject(i).getString("image_large"));
            bird.setImageThumb(mArray.getJSONObject(i).getString("image_thumb"));

            birds.add(bird);
        }

	return birds;
	}


    //Method that will parse the JSON file and will return a JSONObject
    public JSONObject parseJSONData(Context context) {
        String JSONString = null;
        JSONObject JSONObject = null;
        try {

            //open the inputStream to the file
            InputStream inputStream = context.getAssets().open("butterflies.json");

            int sizeOfJSONFile = inputStream.available();

            //array that will store all the data
            byte[] bytes = new byte[sizeOfJSONFile];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            JSONString = new String(bytes, "UTF-8");
            JSONObject = new org.json.JSONObject(JSONString);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (JSONException x) {
            x.printStackTrace();
            return null;
        }
        return JSONObject;
    }
}