package com.example.meduzzka.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class create a helper object to create, open, and/or manage a database
 *
 * Created by Olga Bila
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Overloaded consrtactor
     *
     * @param context to use to open or create the database
     * @param name of the database file
     * @param factory to use for creating cursor objects
     * @param version number of the database
     */
    public DataBaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    /**
     * Called when the database is created for the first time.
     * This is where the creation of tables and the initial population of the tables should happen
     *
     * @param _db the database
     */
    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);

    }

    /**
     * Called when the database needs to be upgraded
     *
     * @param _db the database
     * @param _oldVersion the old database version
     * @param _newVersion the new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to "
                + _newVersion + ", which will destroy all old data");
        _db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");

        onCreate(_db);
    }

}
