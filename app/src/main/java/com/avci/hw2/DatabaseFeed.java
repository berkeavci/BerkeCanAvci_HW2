package com.avci.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class DatabaseFeed extends AppCompatActivity {

    RecyclerView database_recycler;
    LinearLayoutManager feedLinear;
    RecyclerViewAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_feed);

        database_recycler = findViewById(R.id.database_recycler);

        feedLinear = new LinearLayoutManager(this);
        database_recycler.setLayoutManager(feedLinear);
        //feedAdapter = new RecyclerViewAdapter(this, items);
        //recyclerViewNews.setAdapter(recyclerViewAdapter);

    }
}