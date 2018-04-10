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
    private static final String DATABASE_NAME = "bungieAccount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_ACCOUNT = "Account";
    private static final String TABLE_COLLECTABLES = "Collectables";
//    private static final String



    @Override
    public void onCreate(SQLiteDatabase db) {

        //TODO: create all tables here (account, weapons, armour, items)
        String accountQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_ACCOUNT+ " ("+ OldDatabaseModel.COLUMN_KEY+" VARCHAR PRIMARY KEY, "+ OldDatabaseModel.COLUMN_VALUE+" VARCHAR)";
//        String weaponQuery = "CREATE TABLE IF NOT EXISTS weapons ("+OldDatabaseModel.COLUMN_KEY+" VARCHAR PRIMARY KEY, "+OldDatabaseModel.COLUMN_VALUE+" VARCHAR)";
        String collectablesQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_COLLECTABLES+" ("+ OldDatabaseModel.COLUMN_KEY+" VARCHAR PRIMARY KEY, "+ OldDatabaseModel.COLUMN_VALUE+" VARCHAR)";

//        db.execSQL(OldDatabaseModel.CREATE_ACCOUNT_TABLE);
        db.execSQL(accountQuery);
//        db.execSQL(weaponQuery);
        db.execSQL(collectablesQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COLLECTABLES);

        onCreate(db);
    }




    //account- bungieAccount.db

    public void insertAccountData(String tableName, String key, String value){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OldDatabaseModel.COLUMN_KEY, key);
        values.put(OldDatabaseModel.COLUMN_VALUE, value);

        db.insert(tableName, null, values);

//        db.close();
    }

    public OldDatabaseModel getAccountData(String tableName, String key) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName,
                new String[]{OldDatabaseModel.COLUMN_KEY, OldDatabaseModel.COLUMN_VALUE},
                OldDatabaseModel.COLUMN_KEY + "=?",
                new String[]{String.valueOf(key)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        OldDatabaseModel accountData = new OldDatabaseModel(
                cursor.getString(cursor.getColumnIndex(OldDatabaseModel.COLUMN_KEY)),
                cursor.getString(cursor.getColumnIndex(OldDatabaseModel.COLUMN_VALUE)));

        cursor.close();

        return accountData;
    }



    public void updateData(String tableName, String key, String value){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OldDatabaseModel.COLUMN_KEY, key);
        values.put(OldDatabaseModel.COLUMN_VALUE, value);

        //update row
        db.update(tableName,
                values,
                OldDatabaseModel.COLUMN_KEY + " =?",
                new String[] {String.valueOf(key)});
        db.close();
    }








}
