package com.avci.coinhouse.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends  SQLiteOpenHelper {
    public static String DATABASE_NAME="ItemDB";
    public static int DATABASE_VERSION = 1;

    SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(ItemDB.DROP_TABLE_ITEMS_SQL);
            db.execSQL(ItemDB.CREATE_ITEM_TABLE_SQL);

        }catch (SQLException e){
            e.printStackTrace();
        }
        Log.d("DATABASE OPERATIONS", "OnCreate, table is created, records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(ItemDB.DROP_TABLE_ITEMS_SQL);

        }catch (SQLException e){
            e.printStackTrace();
        }
        onCreate(db);
        Log.d("DATABASE OPERATIONS", "onUpgrade, table dropped and recreated. OldVersion:"+oldVersion+" NewVersion:"+newVersion);
    }

    public Cursor getAllRecords(String tableName, String[] columns){
        Cursor cursor = db.query(tableName, columns, null, null, null, null,null);
        return cursor;
    }
    public Cursor getSomeRecords( String tableName, String[] columns, String whereCondition ){
        Cursor cursor = db.query(tableName, columns, whereCondition, null, null, null, null);
        return cursor;
    }

    public boolean insert(String tableName, ContentValues contentValues) {
        return db.insert(tableName, null, contentValues)>0;
    }

    public boolean delete(String tableName, String whereCondition) {
        Log.d("DATABASE OPERATIONS", "DELETE DONE");
        return db.delete(tableName, whereCondition, null)>0;
    }
}
