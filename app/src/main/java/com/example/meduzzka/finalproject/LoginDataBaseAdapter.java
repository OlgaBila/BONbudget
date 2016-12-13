package com.example.meduzzka.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class is used for creating database
 */
public class LoginDataBaseAdapter {
    /**
     * Used to store database name
     */
    static final String DATABASE_NAME = "login.db";

    /**
     * Used to store database version
     */
    static final int DATABASE_VERSION = 1;

    /**
     * Used to store key id
     */
    static final String KEY_ID = "ID";

    /**
     * Used to store database table name
     */
    static final public String TABLE_NAME = "LOGIN";

    /**
     * Used to store database data
     */
    static final String DATABASE_CREATE = "create table " + TABLE_NAME + "( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "USERNAME  text, EMAIL text, PASSWORD text); ";

    /**
     * SQLiteDatabase object
     */
    public SQLiteDatabase db;

    /**
     * Used to open or create the database
     */
    private final Context context;

    /**
     * DataBaseHelper object, used to create, open, and/or manage a database
     */
    private DataBaseHelper dbHelper;

    /**
     * Overloaded constructor
     *
     * @param _context used to open or create the database
     */
    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    /**
     * Called when the database has been opened
     * @return database that will be used for reading and writing
     * @throws SQLException an exception that indicates there was an error with SQL parsing or execution
     */
    public LoginDataBaseAdapter open() throws SQLException {
        /**
         * Create and/or open a database that will be used for reading and writing
         */
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Close any open database object
     */
    public void close() {
        db.close();
    }

    /**
     * Gets instance of database
     * @return database
     */
    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    /**
     * Used for inserting data to database
     * @param userName user name
     * @param userEmail user email
     * @param password user password
     */
    public void insertEntry(String userName, String userEmail, String password) {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", userName);
        newValues.put("EMAIL", userEmail);
        newValues.put("PASSWORD", password);
        db.insert(TABLE_NAME, null, newValues);
    }

    /**
     * Used to delete entry from database
     * @param UserName user name
     * @return integer of deleted entries
     */
    public int deleteEntry(String UserName) {
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete(TABLE_NAME, where,
                new String[] { UserName });
        return numberOFEntriesDeleted;
    }

    /**
     * Used to getting single entry from database
     * @param userEmail user email
     * @return String "NOT EXIST" if user email wasn't found in database
     * and return user password if user email was found
     */
    public String getSinlgeEntry(String userEmail) {
        Cursor cursor = db.query(TABLE_NAME, null, " EMAIL=?",
                new String[] { userEmail }, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    /**
     * Used to update data in database
     * @param userName user name
     * @param userEmail user email
     * @param password user password
     */
    public void updateEntry(String userName, String userEmail, String password) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USERNAME", userName);
        updatedValues.put("EMAIL", userEmail);
        updatedValues.put("PASSWORD", password);

        String where = "USERNAME = ?";
        db.update(TABLE_NAME, updatedValues, where, new String[] { userName });
    }
}
