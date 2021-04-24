package com.avci.hw2;

import java.util.ArrayList;

public class RssObject {

    public String status;
    public ArrayList<Feed> feedList;
    public ArrayList<Item> itemList;

    public RssObject(String status, ArrayList<Feed> feedList, ArrayList<Item> itemList) {
        this.status = status;
        this.feedList = feedList;
        this.itemList = itemList;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Feed> getFeedList() {
        return feedList;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }
}
