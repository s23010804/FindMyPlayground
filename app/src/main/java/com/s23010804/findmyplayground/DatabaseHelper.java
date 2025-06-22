package com.s23010804.findmyplayground;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserData.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ownerName";
    public static final String COL_5 = "contactNumber";
    public static final String COL_6 = "password";

    public static final String BOOKING_TABLE = "bookings";
    public static final String FAVORITES_TABLE = "favorites";
    public static final String REVIEWS_TABLE = "reviews";  // Added review table name

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        String createUsersTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_5 + " TEXT, " +
                COL_6 + " TEXT)";
        db.execSQL(createUsersTable);

        // Bookings table
        String createBookingsTable = "CREATE TABLE IF NOT EXISTS " + BOOKING_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pg_name TEXT, " +
                "date TEXT, " +
                "time TEXT)";
        db.execSQL(createBookingsTable);

        // Favorites table
        String createFavoritesTable = "CREATE TABLE IF NOT EXISTS " + FAVORITES_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pg_name TEXT, " +
                "location TEXT)";
        db.execSQL(createFavoritesTable);

        // ✅ Reviews table
        String createReviewsTable = "CREATE TABLE IF NOT EXISTS " + REVIEWS_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pg_name TEXT, " +
                "rating INTEGER, " +
                "comment TEXT)";
        db.execSQL(createReviewsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REVIEWS_TABLE); // Drop reviews too
        onCreate(db);
    }

    // User insert
    public boolean insertUser(String ownerName, String contactNumber, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, ownerName);
        values.put(COL_5, contactNumber);
        values.put(COL_6, password);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // User check
    public boolean checkUser(String ownerName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                        " WHERE " + COL_2 + " = ? AND " + COL_6 + " = ?",
                new String[]{ownerName, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Insert booking
    public boolean insertBooking(String pgName, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pg_name", pgName);
        values.put("date", date);
        values.put("time", time);
        long result = db.insert(BOOKING_TABLE, null, values);
        return result != -1;
    }

    // Get all bookings
    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + BOOKING_TABLE, null);
    }

    // Insert favorite
    public boolean addToFavorites(String pgName, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pg_name", pgName);
        values.put("location", location);
        long result = db.insert(FAVORITES_TABLE, null, values);
        return result != -1;
    }

    // Get all favorites
    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + FAVORITES_TABLE, null);
    }

    // Remove favorite
    public boolean removeFavorite(String pgName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(FAVORITES_TABLE, "pg_name = ?", new String[]{pgName});
        return result > 0;
    }

    // ✅ Insert review
    public boolean insertReview(String pgName, int rating, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pg_name", pgName);
        values.put("rating", rating);
        values.put("comment", comment);
        long result = db.insert(REVIEWS_TABLE, null, values);
        return result != -1;
    }

    // ✅ Get reviews for a playground
    public Cursor getReviews(String pgName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + REVIEWS_TABLE + " WHERE pg_name = ?", new String[]{pgName});
    }
}
