package com.steve.strongpass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Steve on 9/24/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "strongpass.db";

    // Accounts table info
    public static final String ACCOUNTS_TABLE_NAME = "systemAccounts";
    public static final String ACCOUNTS_COL_ACCOUNT = "account";
    public static final String ACCOUNTS_COL_USERNAME = "username";
    public static final String ACCOUNTS_COL_PASSWORD = "password";
    private static final String SQL_CREATE_TABLE = "create table " + ACCOUNTS_TABLE_NAME +
            "(id integer primary key, " + ACCOUNTS_COL_ACCOUNT + " text, " + ACCOUNTS_COL_USERNAME +  " text, " + ACCOUNTS_COL_PASSWORD + "text)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table systemAccounts
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getAccountsCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor accountCursor =  db.rawQuery( "select * from " + ACCOUNTS_COL_ACCOUNT, null );
        return accountCursor;
    }
}
