package com.avci.hw2;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class ItemDB {
    public static String TABLE_NAME_ITEMS ="items";
    public static String TABLE_NAME_CATEGORY ="category";
    //public static String FIELD_ID = "id";
    public static String FIELD_TITLE = "title";
    public static String FIELD_PUBDATE = "pubDate";
    public static String FIELD_LINK = "link";
    public static String FIELD_GUID = "guid";
    public static String FIELD_AUTHOR = "author";
    public static String FIELD_THUMBNAIL = "thumbnail";
    public static String FIELD_DESCRIPTION = "description";
    public static String FIELD_CONTENT = "content";
    public static String FIELD_CATEGORIES = "categories";
//    public static ArrayList<String> FIELD_CATEGORIES = "categories";


    public static String CREATE_ITEM_TABLE_SQL ="CREATE TABLE TABLE_NAME ( "+FIELD_TITLE+" TEXT, "+FIELD_PUBDATE+" TEXT, "+FIELD_LINK+" TEXT, "+FIELD_GUID+" TEXT, "+FIELD_AUTHOR+" TEXT, "
                                                                      +FIELD_THUMBNAIL+" TEXT, "+FIELD_DESCRIPTION+" TEXT "+FIELD_CONTENT+"TEXT , PRIMARY KEY(FIELD_TITLE))";

    public static String CREATE_CATEGORIES_TABLE_SQL = "CREATE TABLE TABLE_NAME ("+FIELD_TITLE+" TEXT, "+FIELD_CATEGORIES+", FOREIGN KEY(FIELD_TITLE))";
    public static String DROP_TABLE_ITEMS_SQL = "DROP TABLE if exists "+ TABLE_NAME_ITEMS;
    public static String DROP_TABLE_CATEGORIES_SQL = "DROP TABLE if exists "+ TABLE_NAME_CATEGORY;

    // Initial Insertion
    public static ArrayList<String> INSERT_RECORD_SQL_LIST = new ArrayList<String>(){
        {
            add("INSERT INTO "+ TABLE_NAME_ITEMS +" ( "+FIELD_TITLE+", "+FIELD_PUBDATE+", " +
                    ""+FIELD_LINK+", "+FIELD_GUID+", "+FIELD_AUTHOR+", "+FIELD_THUMBNAIL+", "+FIELD_DESCRIPTION+") " +
                    "values ( " +
                    "'NFT trading cards: A new way to own collectibles or an asset bubble?, '" +
                    "'2021-04-24 12:47:00,')" +
                    "'https://cointelegraph.com/news/nft-trading-cards-a-new-way-to-own-collectibles-or-an-asset-bubble,')" +
                    "'https://cointelegraph.com/news/nft-trading-cards-a-new-way-to-own-collectibles-or-an-asset-bubble,')" +
                    "'Cointelegraph By António Madeira,')" +
                    "'https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDQvOTZhN2M1YWItNzJhMS00Yjc3LWFiMTQtZDYwZWQxNTA1ODg1LmpwZw==.jpg,')" +
                    "'<p><img src=\"https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDQvOTZhN2M1YWItNzJhMS00Yjc3LWFiMTQtZDYwZWQxNTA1ODg1LmpwZw==.jpg\"></p> <p>NFT collectibles are blowing up, but are they a bubble waiting to pop or a revolution waiting to explode?</p> ,')" +
                    "'<p><img src=\"https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDQvOTZhN2M1YWItNzJhMS00Yjc3LWFiMTQtZDYwZWQxNTA1ODg1LmpwZw==.jpg\"></p> <p>NFT collectibles are blowing up, but are they a bubble waiting to pop or a revolution waiting to explode?</p>,')" +
                    "");

            add("INSERT INTO" + TABLE_NAME_CATEGORY + "(" +FIELD_AUTHOR+ ", "+FIELD_CATEGORIES+ ")" +
                "values ("+
                "'NFT trading cards: A new way to own collectibles or an asset bubble?,'" +
                "{'NFT', 'Trading Cards'}"
                );
        }
    };


    public static ArrayList<Item>  getAllItem(DatabaseHelper dbHelper){
        Item anItem;
        ArrayList<Item> itemData = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = dbHelper.getAllRecords(TABLE_NAME_ITEMS, null);
        //Log.d("DATABASE OPERATIONS", cursor.getCount()+",  "+cursor.getColumnCount());
        while(cursor.moveToNext()){
            cursor = dbHelper.getAllRecords(TABLE_NAME_ITEMS, null);
            String title = cursor.getString(0);
            String pubDate= cursor.getString(1);
            String link= cursor.getString(2);
            String guid= cursor.getString(3);
            String author= cursor.getString(4);
            String thumbnail= cursor.getString(5);
            String description= cursor.getString(6);
            String content= cursor.getString(7);

            cursor = dbHelper.getAllRecords(TABLE_NAME_CATEGORY, null);
            while(cursor.moveToNext()){
                author = cursor.getString(0);
                categories.add(cursor.getString(1));
                //index++;
            }

            anItem = new Item(title, pubDate, link, guid, author, thumbnail, description, content, categories);
            itemData.add(anItem);
        }


        //Log.d("DATABASE OPERATIONS",itemData.toString());
        return itemData;
    }

    // Edit
    public static ArrayList<Item> findNewsByTitle(DatabaseHelper dbHelper, String key) {
        Item anItem;
        ArrayList<Item> itemData = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        String where = FIELD_TITLE +" like '%"+key+"%'";

        Cursor cursor = dbHelper.getSomeRecords(TABLE_NAME_ITEMS, null, where);
        Log.d("DATABASE OPERATIONS",  where+", "+cursor.getCount()+",  "+cursor.getColumnCount());
        while(cursor.moveToNext()){
            cursor = dbHelper.getAllRecords(TABLE_NAME_ITEMS, null);
            int id=cursor.getInt(0);
            String title = cursor.getString(0);
            String pubDate= cursor.getString(1);
            String link= cursor.getString(2);
            String guid= cursor.getString(3);
            String author= cursor.getString(4);
            String thumbnail= cursor.getString(5);
            String description= cursor.getString(6);
            String content= cursor.getString(7);
            cursor = dbHelper.getAllRecords(TABLE_NAME_CATEGORY, null);
            while(cursor.moveToNext()){
                author = cursor.getString(0);
                categories.add(cursor.getString(1));
                //index++;
            }

            anItem = new Item(title, pubDate, link, guid, author, thumbnail, description, content, categories);
            itemData.add(anItem);
        }
        //Log.d("DATABASE OPERATIONS",data.toString());
        return itemData;
    }

    public static boolean insert(DatabaseHelper dbHelper, String title, String pubDate, String link, String author, String thumbnail, String description, String content, ArrayList<String> categories) {
        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_TITLE, title);
        contentValues.put(FIELD_PUBDATE, pubDate);
        contentValues.put(FIELD_LINK, link);
        contentValues.put(FIELD_GUID, author);
        contentValues.put(FIELD_AUTHOR, thumbnail);
        contentValues.put(FIELD_THUMBNAIL, description);
        contentValues.put(FIELD_DESCRIPTION, content);

        // Item Table Fill
        boolean res = dbHelper.insert(TABLE_NAME_ITEMS,contentValues);
        if(!res)
            return res;
        else {
            contentValues.clear();
            for(int i = 0; i < categories.size(); i++){
                contentValues.put(FIELD_TITLE, author);
                contentValues.put(FIELD_CATEGORIES, categories.get(i));

            }
            return res;
        }
    }

    // Edit
    public static boolean update(DatabaseHelper dbHelper, String title, String pubDate, String link, String author, String thumbnail, String description, String content) {
        //ContentValues  allows to define key value pairs.
        //The key represents the table column identifier and the value represents the content for the table record in this column.
        //ContentVales can be used for insert and update operations over table

        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_TITLE, title);
        contentValues.put(FIELD_PUBDATE, pubDate);
        contentValues.put(FIELD_LINK, link);
        contentValues.put(FIELD_GUID, author);
        contentValues.put(FIELD_AUTHOR, thumbnail);
        contentValues.put(FIELD_THUMBNAIL, description);
        contentValues.put(FIELD_DESCRIPTION, content);

        String where = FIELD_TITLE +" = "+title;
        boolean res = dbHelper.update(TABLE_NAME_ITEMS,contentValues,where );
        return res;
    }

    public static boolean delete(DatabaseHelper dbHelper, String title, String author){
        Log.d("DATABASE OPERATIONS", "DELETE DONE");
        String where = FIELD_TITLE + " = "+title;;
        boolean res =  dbHelper.delete(TABLE_NAME_ITEMS, where);
        if(!res)
            return res;
        else{
            where = FIELD_AUTHOR + " = " + author;
            res = dbHelper.delete(TABLE_NAME_CATEGORY, where);
            return  res;
        }

    }


}
