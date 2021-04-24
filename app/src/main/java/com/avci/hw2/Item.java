package com.avci.hw2;

import java.util.ArrayList;

public class Item{

    private String title;
    private String pubDate;
    private String link;
    private String guid;
    private String author;
    private String thumbnail;
    private String description;
    private String content;
    //private Enclosure enclosure
    private ArrayList<String> categories;

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
}
