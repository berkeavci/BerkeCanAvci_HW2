package com.avci.hw2;

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
        //onCreate called if database doesn't exist
        try {
            db.execSQL(ItemDB.CREATE_ITEM_TABLE_SQL);
            db.execSQL(ItemDB.CREATE_CATEGORIES_TABLE_SQL);
            // Initial Information Load to Database
            for (String sqlToInsert:ItemDB.INSERT_RECORD_SQL_LIST) {
                db.execSQL(sqlToInsert);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        Log.d("DATABASE OPERATIONS", "OnCreate, table is created, records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade called when DATABASE_VERSION is changed
        //SQLiteDatabase object used to execute SQL statements
        try {
            db.execSQL(ItemDB.DROP_TABLE_ITEMS_SQL);
            db.execSQL(ItemDB.DROP_TABLE_CATEGORIES_SQL);
        }catch (SQLException e){
            e.printStackTrace();
        }
        onCreate(db);
        Log.d("DATABASE OPERATIONS", "onUpgrade, table dropped and recreated. OldVersion:"+oldVersion+" NewVersion:"+newVersion);
    }

    public Cursor getAllRecords(String tableName, String[] colums){
        Cursor cursor = db.query(tableName, colums, null, null, null, null,null);
        return cursor;
    }
    public Cursor getSomeRecords( String tableName, String[] columns,String whereCondition ){
        Cursor cursor = db.query(tableName, columns, whereCondition, null, null, null, null);
        Log.d("DATABASE OPERATIONS", "GET SOME RECORDS WITH WHERE CLAUSE");
        return cursor;
    }

    public boolean insert(String tableName, ContentValues contentValues) {
        Log.d("DATABASE OPERATIONS", "INSERT DONE");
        return db.insert(tableName, null, contentValues)>0;
    }

    public boolean update(String tableName, ContentValues contentValues, String whereCondition) {
        Log.d("DATABASE OPERATIONS", "UPDATE DONE");

        return db.update(tableName, contentValues, whereCondition,null)>0;
    }

    public boolean delete(String tableName, String whereCondition) {
        Log.d("DATABASE OPERATIONS", "DELETE DONE");
        return db.delete(tableName, whereCondition, null)>0;
    }
}
