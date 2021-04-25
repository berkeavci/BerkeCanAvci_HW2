package com.avci.hw2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Parcelable {

    public String title;
    public String pubDate;
    public String link;
    public String guid;
    public String author;
    public String thumbnail;
    public String description;
    public String content;
    public ArrayList<String> categories;

    public Item(String title, String pubDate, String link, String guid, String author, String thumbnail, String description, String content, ArrayList<String> categories) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
        this.author = author;
        this.thumbnail = thumbnail;
        this.description = description;
        this.content = content;
        this.categories = categories;
    }

    public Item(JSONObject obj) throws JSONException {
        this.title = obj.getString("title");
        this.pubDate = obj.getString("pubDate");
        this.link = obj.getString("link");
        this.guid = obj.getString("guid");
        this.author = obj.getString("author");
        this.thumbnail = obj.getString("thumbnail");
        this.description = obj.getString("description");
        this.content = obj.getString("content");
        this.categories = Utility.jsonToStringArrayList(obj.getJSONArray("categories"));
    }


    protected Item(Parcel in) {
        title = in.readString();
        pubDate = in.readString();
        link = in.readString();
        guid = in.readString();
        author = in.readString();
        thumbnail = in.readString();
        description = in.readString();
        content = in.readString();
        categories = in.createStringArrayList();
    }



    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getLink() {
        return link;
    }

    public String getGuid() {
        return guid;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    // Parse Json
    static ArrayList<Item> fromJsonArray(JSONArray array) throws JSONException {
        ArrayList<Item> arrList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            arrList.add(new Item(array.getJSONObject(i)));
        }
        return arrList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pubDate);
        dest.writeString(link);
        dest.writeString(guid);
        dest.writeString(author);
        dest.writeString(thumbnail);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeStringList(categories);
    }
}
