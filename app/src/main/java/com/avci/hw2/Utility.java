package com.avci.hw2;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class Utility {
    static ArrayList<String> jsonToStringArrayList(JSONArray array) throws JSONException {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            arrList.add(array.getString(i));
        }
        return arrList;
    }

    static String htmlToText(String text){
        Document doc = Jsoup.parse(text);
        return doc.body().text();
    }

    static ArrayList<String> jsonToStringArrayList(String cat) throws JSONException {
        JSONArray json = new JSONArray(cat);
        return Utility.jsonToStringArrayList(json);
    }



}
