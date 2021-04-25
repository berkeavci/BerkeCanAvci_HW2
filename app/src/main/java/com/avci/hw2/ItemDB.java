package com.avci.hw2;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.squareup.okhttp.internal.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ItemDB {
    public static String TABLE_NAME_ITEMS ="items";
    public static String FIELD_TITLE = "title";
    public static String FIELD_PUBDATE = "pubDate";
    public static String FIELD_LINK = "link";
    public static String FIELD_GUID = "guid";
    public static String FIELD_AUTHOR = "author";
    public static String FIELD_THUMBNAIL = "thumbnail";
    public static String FIELD_DESCRIPTION = "description";
    public static String FIELD_CONTENT = "content";
    public static String FIELD_CATEGORIES = "categories";


    public static String CREATE_ITEM_TABLE_SQL ="CREATE TABLE "+TABLE_NAME_ITEMS+"("+
            FIELD_TITLE+" TEXT, "+
            FIELD_PUBDATE+" TEXT, "+
            FIELD_LINK+" TEXT, "+
            FIELD_GUID+" TEXT, "+
            FIELD_AUTHOR+" TEXT, " +
            FIELD_THUMBNAIL+" TEXT, "+
            FIELD_DESCRIPTION+" TEXT, " +
            FIELD_CONTENT+" TEXT," +
            FIELD_CATEGORIES + " TEXT)";
           // " PRIMARY KEY("+FIELD_GUID+"))";

    public static String DROP_TABLE_ITEMS_SQL = "DROP TABLE if exists "+ TABLE_NAME_ITEMS;


    public static ArrayList<Item>  getAllItem(DatabaseHelper dbHelper) throws JSONException {
        Item anItem;
        ArrayList<Item> itemData = new ArrayList<>();

        Cursor cursor = dbHelper.getAllRecords(TABLE_NAME_ITEMS, null);
        while(cursor.moveToNext()){
            String title = cursor.getString(0);
            String pubDate= cursor.getString(1);
            String link= cursor.getString(2);
            String guid= cursor.getString(3);
            String author= cursor.getString(4);
            String thumbnail= cursor.getString(5);
            String description= cursor.getString(6);
            String content= cursor.getString(7);
            ArrayList<String> categories = Utility.jsonToStringArrayList(cursor.getString(8));

            anItem = new Item(title, pubDate, link, guid, author, thumbnail, description, content, categories);
            itemData.add(anItem);
        }

        return itemData;
    }

    public static ArrayList<Item> findNewsByTitle(DatabaseHelper dbHelper, String key) throws JSONException {
        Item anItem;
        ArrayList<Item> itemData = new ArrayList<>();
        String where = FIELD_TITLE +" like '%"+key+"%'";

        Cursor cursor = dbHelper.getSomeRecords(TABLE_NAME_ITEMS, null, where);
        Log.d("DATABASE OPERATIONS",  where+", "+cursor.getCount()+",  "+cursor.getColumnCount());
        while(cursor.moveToNext()){
            String title = cursor.getString(0);
            String pubDate= cursor.getString(1);
            String link= cursor.getString(2);
            String guid= cursor.getString(3);
            String author= cursor.getString(4);
            String thumbnail= cursor.getString(5);
            String description= cursor.getString(6);
            String content= cursor.getString(7);
            ArrayList<String> categories = Utility.jsonToStringArrayList(cursor.getString(8));

            anItem = new Item(title, pubDate, link, guid, author, thumbnail, description, content, categories);
            itemData.add(anItem);
        }
        //Log.d("DATABASE OPERATIONS",data.toString());
        return itemData;
    }

    public static boolean insertItems(DatabaseHelper dbHelper, Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_TITLE, item.getTitle());
        contentValues.put(FIELD_PUBDATE, item.getPubDate() );
        contentValues.put(FIELD_LINK, item.getLink());
        contentValues.put(FIELD_GUID, item.getGuid());
        contentValues.put(FIELD_AUTHOR, item.getAuthor());
        contentValues.put(FIELD_THUMBNAIL, item.getThumbnail());
        contentValues.put(FIELD_DESCRIPTION, Utility.htmlToText(item.getDescription()));
        contentValues.put(FIELD_CONTENT, Utility.htmlToText(item.getContent()));
        contentValues.put(FIELD_CATEGORIES, (new JSONArray(item.getCategories())).toString());

        Log.d("SELAM ", "insert: " + contentValues);
        // Item Table Fill
        boolean res = dbHelper.insert(TABLE_NAME_ITEMS,contentValues);

        return res;

    }




    public static boolean delete(DatabaseHelper dbHelper, String title){
        Log.d("DATABASE OPERATIONS", "DELETE DONE");
        String where = FIELD_TITLE + " = "+title;
        boolean res =  dbHelper.delete(TABLE_NAME_ITEMS, where);
        return  res;
    }


}
