package com.example.psdl_project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

public class DBHelperForm extends SQLiteOpenHelper {

    public static final String TABLE_USER = "User";
    private static final String DATABASE_NAME = "alzQuizNew.db"; // Updated database name
    private static final int DATABASE_VERSION = 2;


    // Table and columns
    public static final String COLUMN_PHONE_NUMBER = "phone_number"; // Phone number as primary key
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_BIRTH_DATE = "birth_date";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_SPOUSE = "spouse";
    public static final String COLUMN_FAVORITE_ACTIVITY = "favorite_activity";

    public DBHelperForm(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table with phone number as primary key
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_PHONE_NUMBER + " TEXT PRIMARY KEY," // Phone number as primary key
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_BIRTH_DATE + " TEXT,"
                + COLUMN_CITY + " TEXT,"
                + COLUMN_SPOUSE + " TEXT,"
                + COLUMN_FAVORITE_ACTIVITY + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Alter the table to make phone number the primary key (if upgrading)
            String ADD_PHONE_COLUMN = "ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_PHONE_NUMBER + " TEXT PRIMARY KEY";
            db.execSQL(ADD_PHONE_COLUMN);
            Log.i("DBHelperForm", "Database upgraded to version 2, set phone number as primary key.");
        }
    }

    // Insert user details
    public boolean insertUserDetails(String name, int age, String birthDate, String city, String spouse, String favoriteActivity, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_BIRTH_DATE, birthDate);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_SPOUSE, spouse);
        values.put(COLUMN_FAVORITE_ACTIVITY, favoriteActivity);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber); // Insert phone number

        try {
            long result = db.insert(TABLE_USER, null, values);
            db.close();
            if (result == -1) {
                Log.e("DBHelperForm", "Failed to insert user data. Insert result: " + result);
                return false;
            } else {
                Log.i("DBHelperForm", "User data inserted successfully with result ID: " + result);
                return true;
            }
        } catch (Exception e) {
            Log.e("DBHelperForm", "Error inserting data", e);
            db.close();
            return false;
        }
    }
    @SuppressLint("Range")
    public String getFirstPhoneNumber() {
        SQLiteDatabase db = this.getReadableDatabase();  // Make sure the database is accessible.
        String phoneNumber = null;

        // Query to get the first phone number from the User table
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT " + COLUMN_PHONE_NUMBER + " FROM " + TABLE_USER + " LIMIT 1", null);

            if (cursor != null && cursor.moveToFirst()) {
                // Extract phone number from the first row
                phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
            }
        } catch (Exception e) {
            Log.e("DBHelperForm", "Error fetching phone number", e);
        } finally {
            if (cursor != null) {
                cursor.close(); // Make sure to close the cursor to avoid memory leaks
            }
            db.close(); // Always close the database when you're done with it
        }

        return phoneNumber;
    }

    public boolean updateUserDetails(String phoneNumber, String name, int age, String birthDate, String city, String spouse, String favoriteActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Adding all the updated values
        values.put(COLUMN_PHONE_NUMBER, phoneNumber); // Ensure the phone number is updated
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_BIRTH_DATE, birthDate);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_SPOUSE, spouse);
        values.put(COLUMN_FAVORITE_ACTIVITY, favoriteActivity);

        try {
            // Update the user details where the phone number matches the provided phone number
            int rows = db.update(TABLE_USER, values, COLUMN_PHONE_NUMBER + " = ?", new String[]{phoneNumber});

            db.close();

            if (rows > 0) {
                Log.i("DBHelperForm", "User details updated successfully for phone number: " + phoneNumber);
                return true;
            } else {
                Log.e("DBHelperForm", "No rows updated. Check if the user phone number exists.");
                return false;
            }
        } catch (Exception e) {
            Log.e("DBHelperForm", "Error updating user data", e);
            db.close();
            return false;
        }
    }


}
