package com.jastley.warmindfordestiny2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jamie on 1/4/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bungieAccount.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TODO: create all tables here (account, weapons, armour, items)
//        String accountQuery = "CREATE TABLE IF NOT EXISTS account (key VARCHAR PRIMARY KEY, value VARCHAR)";
        String weaponQuery = "CREATE TABLE IF NOT EXISTS weapons (key VARCHAR PRIMARY KEY, value VARCHAR)";

        db.execSQL(DatabaseModel.CREATE_TABLE);
        db.execSQL(weaponQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //account- bungieAccount.db

    public void insertAccountData(String tableName, String key, String value){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseModel.COLUMN_KEY, key);
        values.put(DatabaseModel.COLUMN_VALUE, value);

        db.insert(tableName, null, values);

        db.close();
    }

    public DatabaseModel getAccountData(String tableName, String key) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName,
                new String[]{DatabaseModel.COLUMN_KEY, DatabaseModel.COLUMN_VALUE},
                DatabaseModel.COLUMN_KEY + "=?",
                new String[]{String.valueOf(key)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        DatabaseModel accountData = new DatabaseModel(
                cursor.getString(cursor.getColumnIndex(DatabaseModel.COLUMN_KEY)),
                cursor.getString(cursor.getColumnIndex(DatabaseModel.COLUMN_VALUE)));

        cursor.close();

        return accountData;
    }
}
