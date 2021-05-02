package com.avci.hw2.data.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Feed {

    public String url;
    public String title;
    public String link;
    public String description;
    public String image;


    public Feed(String url, String title, String link, String description, String image) {
        this.url = url;
        this.title = title;
        this.link = link;
        this.description = description;
        this.image = image;
    }

    public Feed(JSONObject obj) throws JSONException {
        this.url = obj.getString("url");
        this.title = obj.getString("title");
        this.link = obj.getString("link");
        this.description = obj.getString("description");
        this.image = obj.getString("image");
    }

    // Parse JsonArray to Feed ArrayList
//    static ArrayList<Feed> fromJsonArray(JSONArray array) throws JSONException {
//        ArrayList<Feed> arrList = new ArrayList<>();
//        for (int i = 0; i < array.length(); i++) {
//            arrList.add(new Feed(array.getJSONObject(i)));
//        }
//        return arrList;
//    }
}
