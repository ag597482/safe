package com.example.android.safe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.safe.data.SafeContract.ItemEntry;

public class SafeHelper extends SQLiteOpenHelper {


    public static final String LOG_TAG = SafeHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "pass.db";
    private static final int DATABASE_VERSION = 1;

    public SafeHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE =
                "CREATE TABLE " + ItemEntry.TABLE_NAME + " ("
                        + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ItemEntry.COLUMN_NAME + " TEXT, "
                        + ItemEntry.COLUMN_ID + " TEXT, "
                        + ItemEntry.COLUMN_PASS + " TEXT);";

        db.execSQL(SQL_CREATE_TABLE);

//         String SQL_DELETE_ENTRIES =
//                "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
