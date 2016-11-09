package com.steve.strongpass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "strongpass.db";

    // Accounts table info
    private static final String ACCOUNTS_TABLE_NAME = "systemAccounts";
    private static final String ACCOUNTS_COL_ACCOUNT = "account";
    private static final String ACCOUNTS_COL_DESCRIPTION = "description";
    private static final String ACCOUNTS_COL_USERNAME = "username";
    private static final String ACCOUNTS_COL_PASSWORD = "password";
    private static final String SQL_CREATE_TABLE = "create table " + ACCOUNTS_TABLE_NAME +
            "(id integer primary key, " + ACCOUNTS_COL_ACCOUNT + " text, " + ACCOUNTS_COL_USERNAME +  " text, "
            + ACCOUNTS_COL_PASSWORD + " text, " + ACCOUNTS_COL_DESCRIPTION + " text)";

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

    public Cursor getAccountListCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select id as _id," +  ACCOUNTS_COL_ACCOUNT + "," + ACCOUNTS_COL_DESCRIPTION + " from " + ACCOUNTS_TABLE_NAME, null );
    }

    public Cursor getAccountDetails(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from " + ACCOUNTS_TABLE_NAME + " where id" + "=" + id, null );
    }

    public boolean insertAccount(String account, String name, String password, String description){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNTS_COL_ACCOUNT, account);
        contentValues.put(ACCOUNTS_COL_USERNAME, name);
        contentValues.put(ACCOUNTS_COL_PASSWORD, password);
        contentValues.put(ACCOUNTS_COL_DESCRIPTION, description);
        db.insert(ACCOUNTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateAccount(String account, String name, String password, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNTS_COL_ACCOUNT, account);
        contentValues.put(ACCOUNTS_COL_USERNAME, name);
        contentValues.put(ACCOUNTS_COL_PASSWORD, password);
        contentValues.put(ACCOUNTS_COL_DESCRIPTION, description);
        db.update(ACCOUNTS_TABLE_NAME, null, ACCOUNTS_COL_ACCOUNT + " = ?", new String[]{account});
        return true;
    }

    public Integer deleteAccount(String account) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ACCOUNTS_TABLE_NAME, ACCOUNTS_COL_ACCOUNT + " = ? ", new String[] { account});
    }
}
