package com.avci.coinhouse.data.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RssObject {

    public String status;
    public Feed feed;
    public ArrayList<Item> items;

    public RssObject(String status, Feed feed, ArrayList<Item> itemList) {
        this.status = status;
        this.feed = feed;
        this.items = itemList;
    }

    public RssObject(JSONObject obj) throws JSONException {
        this.status = obj.getString("status");
        this.feed = new Feed(obj.getJSONObject("feed"));
        this.items = Item.fromJsonArray(obj.getJSONArray("items"));
    }
}
