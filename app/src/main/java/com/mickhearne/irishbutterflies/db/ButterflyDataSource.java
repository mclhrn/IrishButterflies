package com.mickhearne.irishbutterflies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mickhearne.irishbutterflies.model.Butterfly;
import com.mickhearne.irishbutterflies.model.ButterfliesSeenModel;

import java.util.ArrayList;
import java.util.List;

public class ButterflyDataSource {

	private static final String LOGTAG = "butterflies";
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	// array of string references for all columns in butterflies table
	private static final String[] butterfliesAllColumns = {
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_NAME,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_LATIN_NAME,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_HABITAT,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_DISTRIBUTION,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_FOODPLANT,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_WINGSPAN,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_FLIGHT_PERIOD,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_DESCRIPTION,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_IMAGE_THUMB,
			ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_IMAGE_LARGE };

	public ButterflyDataSource(Context context) {
		dbhelper = new ButterflyDBOpenHelper(context);
	}

	// open db
	public void open() {
		Log.i(LOGTAG, "Database Open.");
		database = dbhelper.getWritableDatabase();
    }

	// close db
	public void close() {
		Log.i(LOGTAG, "Database Closed.");
		dbhelper.close();
	}


	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Main Butterfly Table
	 */
	// create record in bird table
	public Butterfly create(Butterfly butterfly) {

		ContentValues values = new ContentValues();

		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_NAME,
                butterfly.getName());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_LATIN_NAME,
                butterfly.getLatinName());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_HABITAT,
                butterfly.getHabitat());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_DISTRIBUTION,
                butterfly.getDistribution());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_FOODPLANT,
                butterfly.getFoodplant());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_WINGSPAN,
                butterfly.getWingspan());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_FLIGHT_PERIOD,
                butterfly.getFlightPeriod());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_DESCRIPTION,
                butterfly.getDescription());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_IMAGE_THUMB,
                butterfly.getImageThumb());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_IMAGE_LARGE,
                butterfly.getImageLarge());

		long insertid = database.insert(ButterflyDBOpenHelper.TABLE_BUTTERFLIES, null,
				values);
        butterfly.setId(insertid);
		return butterfly;
	}

	// returns all rows from from bird table
	public List<Butterfly> findAll() {

		// Set up cursor to hold db query result
		Cursor cursor = database.query(ButterflyDBOpenHelper.TABLE_BUTTERFLIES,
				butterfliesAllColumns, null, null, null, null, null);
		Log.i(LOGTAG, "There are " + cursor.getColumnCount()
                + " columns in the ButterfliesFragment table.");

//		List<Butterfly> butterflies = cursorToList(cursor);

		return cursorToList(cursor);
	}

	// toggle output of reference guide
	public List<Butterfly> refAtoZ() {
		// Set up cursor to hold db query result
		Cursor cursor = database.query(ButterflyDBOpenHelper.TABLE_BUTTERFLIES,
				butterfliesAllColumns, null, null, null, null,
				ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_NAME + " desc");
		Log.i(LOGTAG, "There are " + cursor.getColumnCount()
                + " columns in the ButterfliesFragment table.");

//		List<Butterfly> butterflies = cursorToList(cursor);

		return cursorToList(cursor);
	}

	// converts cursor to a list for bird table
	private List<Butterfly> cursorToList(Cursor cursor) {

		List<Butterfly> butterflies = new ArrayList<Butterfly>();

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Butterfly butterfly = new Butterfly();

                butterfly.setId(cursor.getLong(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID)));
                butterfly.setName(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_NAME)));
                butterfly.setLatinName(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_LATIN_NAME)));
                butterfly.setHabitat(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_HABITAT)));
                butterfly.setDistribution(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_DISTRIBUTION)));
                butterfly.setFoodplant(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_FOODPLANT)));
                butterfly.setWingspan(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_WINGSPAN)));
                butterfly.setFlightPeriod(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_FLIGHT_PERIOD)));
                butterfly.setDescription(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_DESCRIPTION)));
                butterfly.setImageThumb(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_IMAGE_THUMB)));
                butterfly.setImageLarge(cursor.getString(cursor
                        .getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_IMAGE_LARGE)));

                butterflies.add(butterfly);
			}
		}
		return butterflies;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ButterfliesSeen Table
	 */
	// adds a bird to the ButterfliesSeen table
	public boolean addToButterfliesSeen(Butterfly butterfly, double lat, double lng) {

		ContentValues values = new ContentValues();

		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID, butterfly.getId());
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_LATITUDE, lat);
		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_LONGITUDE, lng);

		long result = database.insert(ButterflyDBOpenHelper.TABLE_BUTTERFLIES_SEEN, null,
				values);

		return (result != -1);
	}

	// remove a bird from the ButterfliesSeen table
	public boolean removeFromButterfliesSeen(Butterfly butterfly) {
		String where = ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID + "=" + butterfly.getId();
		int result = database.delete(ButterflyDBOpenHelper.TABLE_BUTTERFLIES_SEEN, where,
				null);
		return (result == 1);
	}

	// retrieves all butterflies from ButterfliesSeen table
	public List<Butterfly> findButterfliesSeen() {

		String query = "SELECT butterflies.* FROM " + "butterflies JOIN butterfliesSeen ON "
				+ "butterflies.butterfly_id = butterfliesSeen.butterfly_id";
		Cursor cursor = database.rawQuery(query, null);

		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");

//		List<Butterfly> butterflies = cursorToList(cursor);
		return cursorToList(cursor);
	}

	// toggle output of butterflies seen list
	public List<Butterfly> seenAtoZ() {
		String query = "SELECT butterflies.* FROM " + "butterflies JOIN butterfliesSeen ON "
				+ "butterflies.butterfly_id = butterfliesSeen.butterfly_id ORDER BY "
				+ ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID + " DESC";
		Cursor cursor = database.rawQuery(query, null);

//		List<Butterfly> butterflies = cursorToList(cursor);
		return cursorToList(cursor);
	}

	// retrieves all butterflies from butterfliesSeen table for display on map
	public List<ButterfliesSeenModel> findButterfliesForMap() {

		// Cursor cursor = database.query(ButterflyDBOpenHelper.TABLE_BUTTERFLIES_SEEN,
		// butterfliesSeenColumns, null, null, null, null, null);

		String query = "Select butterflies.name, butterfliesSeen.latitude, butterfliesSeen.longitude FROM butterflies, butterfliesSeen where butterflies.butterfly_id=butterfliesSeen.butterfly_id ";
		Cursor cursor = database.rawQuery(query, null);

//		List<ButterfliesSeenModel> butterfliesSeen = cursorToLocationList(cursor);

		return cursorToLocationList(cursor);
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Wishlist Table
	 */
	// adds a bird to the wishList table
	public boolean addToWishList(Butterfly butterfly) {
		ContentValues values = new ContentValues();

		values.put(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID, butterfly.getId());
		long result = database.insert(ButterflyDBOpenHelper.TABLE_BUTTERFLIES_WISHLIST,
				null, values);

		return (result != -1);
	}

	// remove a butterfly from the wishList table
	public boolean removeFromWishList(Butterfly butterfly) {
		String where = ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID + "=" + butterfly.getId();
		int result = database.delete(ButterflyDBOpenHelper.TABLE_BUTTERFLIES_WISHLIST,
				where, null);
		return (result == 1);
	}

	// retrieves all butterflies from wishList table
	public List<Butterfly> findWishList() {
		String query = "SELECT butterflies.* FROM " + "butterflies JOIN wishList ON "
				+ "butterflies.butterfly_id = wishList.butterfly_id";
		Cursor cursor = database.rawQuery(query, null);

//		List<Butterfly> butterflies = cursorToList(cursor);
		return cursorToList(cursor);
	}

	// toggle output of wishlist
	public List<Butterfly> wishAtoZ() {
		String query = "SELECT butterflies.* FROM " + "butterflies JOIN wishList ON "
				+ "butterflies.butterfly_id = wishList.butterfly_id ORDER BY "
				+ ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID + " DESC";
		Cursor cursor = database.rawQuery(query, null);

//		List<Butterfly> butterflies = cursorToList(cursor);
		return cursorToList(cursor);
	}

    // Check that a butterfly is on this list
    public boolean checkWishlist(long id) {
        String where = ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID + " = '" + id + "'";
        Cursor cursor = database.query(ButterflyDBOpenHelper.TABLE_BUTTERFLIES_WISHLIST, new String[]{ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID}, where, null, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    // Check that a butterflies is on this list
    public boolean checkSeenlist(long id) {
        String where = ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID + " = '" + id + "'";
        Cursor cursor = database.query(ButterflyDBOpenHelper.TABLE_BUTTERFLIES_SEEN, new String[]{ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_ID}, where, null, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * butterfliesSeen Table for MapView display
	 */
	// cursor to list for map view
	private List<ButterfliesSeenModel> cursorToLocationList(Cursor cursor) {

		List<ButterfliesSeenModel> butterfliesSeenList = new ArrayList<ButterfliesSeenModel>();

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				ButterfliesSeenModel butterfliesSeen = new ButterfliesSeenModel();

                butterfliesSeen.setName(cursor.getString(cursor
						.getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_NAME)));
                butterfliesSeen
						.setLatitude(cursor.getDouble(cursor
								.getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_LATITUDE)));
                butterfliesSeen
						.setLongitude(cursor.getDouble(cursor
								.getColumnIndex(ButterflyDBOpenHelper.BUTTERFLIES_COLUMN_LONGITUDE)));

                butterfliesSeenList.add(butterfliesSeen);
			}
		}
		Log.i(LOGTAG, "Returned " + cursor.getCount()
                + " rows in map view display");
		return butterfliesSeenList;
	}
}