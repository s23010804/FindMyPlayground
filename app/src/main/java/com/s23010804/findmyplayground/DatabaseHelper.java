package com.s23010804.findmyplayground;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserData.db";
    public static final int DATABASE_VERSION = 2; // ✅ bumped version to recreate tables if schema changes

    // Users table
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ownerName";
    public static final String COL_5 = "contactNumber";
    public static final String COL_6 = "password";

    // Other tables
    public static final String BOOKING_TABLE = "bookings";
    public static final String FAVORITES_TABLE = "favorites";
    public static final String REVIEWS_TABLE = "reviews";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_5 + " TEXT, " +
                COL_6 + " TEXT)");

        // Bookings
        db.execSQL("CREATE TABLE IF NOT EXISTS " + BOOKING_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pg_name TEXT, " +
                "date TEXT, " +
                "time TEXT)");

        // Favorites ✅ UNIQUE to prevent duplicates
        db.execSQL("CREATE TABLE IF NOT EXISTS " + FAVORITES_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pg_name TEXT UNIQUE, " +
                "location TEXT)");

        // Reviews
        db.execSQL("CREATE TABLE IF NOT EXISTS " + REVIEWS_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pg_name TEXT, " +
                "rating INTEGER, " +
                "comment TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REVIEWS_TABLE);
        onCreate(db);
    }

    // ------------------- USERS -------------------
    public boolean insertUser(String ownerName, String contactNumber, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, ownerName);
        values.put(COL_5, contactNumber);
        values.put(COL_6, password);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean checkUser(String ownerName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                        " WHERE " + COL_2 + "=? AND " + COL_6 + "=?",
                new String[]{ownerName, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // ------------------- BOOKINGS -------------------
    public boolean insertBooking(String pgName, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pg_name", pgName);
        values.put("date", date);
        values.put("time", time);
        long result = db.insert(BOOKING_TABLE, null, values);
        return result != -1;
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + BOOKING_TABLE, null);
    }

    // ------------------- FAVORITES -------------------
    public boolean addToFavorites(String pgName, String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        // ✅ Check if already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + FAVORITES_TABLE + " WHERE pg_name = ?", new String[]{pgName});
        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // already exists
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put("pg_name", pgName);
        values.put("location", location);
        long result = db.insert(FAVORITES_TABLE, null, values);
        return result != -1;
    }

    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + FAVORITES_TABLE, null);
    }

    public boolean removeFavorite(String pgName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(FAVORITES_TABLE, "pg_name = ?", new String[]{pgName});
        return result > 0;
    }

    // ------------------- REVIEWS -------------------
    public boolean insertReview(String pgName, int rating, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pg_name", pgName);
        values.put("rating", rating);
        values.put("comment", comment);
        long result = db.insert(REVIEWS_TABLE, null, values);
        return result != -1;
    }

    public Cursor getReviews(String pgName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + REVIEWS_TABLE + " WHERE pg_name = ?", new String[]{pgName});
    }
}
