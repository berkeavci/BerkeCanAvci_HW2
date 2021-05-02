package com.avci.hw2.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avci.hw2.data.entities.RssObject;

import org.json.JSONException;
import org.json.JSONObject;

public class RssFeedDataManager {
    public Context context;
    public RequestQueue requestQueue;

    private static final String RSS_PARSER_URL = "https://api.rss2json.com/v1/api.json";
    private static final String FEED_URL = "https://cointelegraph.com/rss";

    public RssFeedDataManager(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    // Sends GET + URL and access the JSON Object - Interface to access it on MainActivity
    public void fetchRss(OnResponse callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                RSS_PARSER_URL + "?rss_url=" + FEED_URL,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    RssObject a = new RssObject(obj);
                    callback.onSuccess(a);

                } catch (JSONException e) {
                    Log.d("Parse", "Error!");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });
        requestQueue.getCache().clear();
        requestQueue.add(request);
    }


    public interface OnResponse {
        void onSuccess(RssObject rssObject) throws JSONException;
        void onError(VolleyError error);
    }
}
