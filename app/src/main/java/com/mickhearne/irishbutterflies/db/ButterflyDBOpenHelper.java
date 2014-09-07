package com.mickhearne.irishbutterflies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ButterflyDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "butterflies";
	
	// set db name and version
	private static final String DATABASE_NAME = "butterflies.db";
	private static final int DATABASE_VERSION = 1;

	
	// set butterfly table name and columns
	public static final String TABLE_BUTTERFLIES = "butterflies";
	public static final String BUTTERFLIES_COLUMN_ID = "butterfly_id";
	public static final String BUTTERFLIES_COLUMN_NAME = "name";
	public static final String BUTTERFLIES_COLUMN_LATIN_NAME = "latin_name";
	public static final String BUTTERFLIES_COLUMN_HABITAT = "habitat";
	public static final String BUTTERFLIES_COLUMN_DISTRIBUTION = "distribution";
	public static final String BUTTERFLIES_COLUMN_FOODPLANT = "foodplant";
	public static final String BUTTERFLIES_COLUMN_WINGSPAN = "wingspan";
	public static final String BUTTERFLIES_COLUMN_FLIGHT_PERIOD = "flight_period";
	public static final String BUTTERFLIES_COLUMN_DESCRIPTION = "description";
	public static final String BUTTERFLIES_COLUMN_IMAGE_THUMB = "image_thumb";
	public static final String BUTTERFLIES_COLUMN_IMAGE_LARGE = "image_large";
	
	// create butterflies table
	private static final String TABLE_CREATE_BUTTERFLIES =
			"CREATE TABLE " + TABLE_BUTTERFLIES + " (" +
					BUTTERFLIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					BUTTERFLIES_COLUMN_NAME + " TEXT, " +
					BUTTERFLIES_COLUMN_LATIN_NAME + " TEXT, " +
                    BUTTERFLIES_COLUMN_HABITAT + " TEXT, " +
                    BUTTERFLIES_COLUMN_DISTRIBUTION + " TEXT, " +
                    BUTTERFLIES_COLUMN_FOODPLANT + " TEXT, " +
                    BUTTERFLIES_COLUMN_WINGSPAN + " TEXT, " +
                    BUTTERFLIES_COLUMN_FLIGHT_PERIOD + " TEXT, " +
                    BUTTERFLIES_COLUMN_DESCRIPTION + " TEXT, " +
					BUTTERFLIES_COLUMN_IMAGE_THUMB + " TEXT, " +
					BUTTERFLIES_COLUMN_IMAGE_LARGE + " TEXT " +
					")";


	// butterflies seen table name and columns
	public static final String TABLE_BUTTERFLIES_SEEN = "butterfliesSeen";
	public static final String BUTTERFLIES_COLUMN_LATITUDE = "latitude";
	public static final String BUTTERFLIES_COLUMN_LONGITUDE = "longitude";
	private static final String TABLE_CREATE_BUTTERFLIES_SEEN =
			"CREATE TABLE " + TABLE_BUTTERFLIES_SEEN + " (" +
					BUTTERFLIES_COLUMN_ID + " INTEGER PRIMARY KEY, " +
					BUTTERFLIES_COLUMN_LATITUDE + " REAL, " +
					BUTTERFLIES_COLUMN_LONGITUDE + " REAL " +
					")";


	// wish-list table name and columns
	public static final String TABLE_BUTTERFLIES_WISHLIST = "wishList";
    private static final String TABLE_CREATE_BUTTERFLIES_WISHLIST =
			"CREATE TABLE " + TABLE_BUTTERFLIES_WISHLIST + " (" +
					BUTTERFLIES_COLUMN_ID + " INTEGER PRIMARY KEY)";


	public ButterflyDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	// create tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_BUTTERFLIES);
		db.execSQL(TABLE_CREATE_BUTTERFLIES_SEEN);
		db.execSQL(TABLE_CREATE_BUTTERFLIES_WISHLIST);
		Log.i(LOGTAG, "Butterflies Table created!!");
		Log.i(LOGTAG, "Butterflies Seen Table created!!");
		Log.i(LOGTAG, "Wish-list Table created!!");
	}


	// never to be called explicitly. Only on version updates
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUTTERFLIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUTTERFLIES_SEEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUTTERFLIES_WISHLIST);
		onCreate(db);
	}
}
