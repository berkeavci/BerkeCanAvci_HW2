package com.avci.hw2;

public class Feed {

    private String url;
    private String title;
    private String link;
    private String description;
    private String image;


    // Constructor
    public Feed(String url, String title, String link, String description, String image) {
        this.url = url;
        this.title = title;
        this.link = link;
        this.description = description;
        this.image = image;
    }



    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
