package com.avci.hw2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.avci.hw2.adapters.RecyclerViewAdapter;
import com.avci.hw2.data.database.DatabaseHelper;
import com.avci.hw2.data.database.ItemDB;
import com.avci.hw2.data.RssFeedDataManager;
import com.avci.hw2.data.entities.Item;
import com.avci.hw2.data.entities.RssObject;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewNews;
    ImageButton searchBtn;
    EditText searchNews;

    Button refillButton;

    LinearLayoutManager mLayoutManager;

    RecyclerViewAdapter recyclerViewAdapter;
    RssFeedDataManager rssFeedDataManager;
    DatabaseHelper dbHelper;

    RssObject rssObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        searchBtn = findViewById(R.id.searchBtn);
        searchNews = findViewById(R.id.searchNews);
        refillButton = findViewById(R.id.refill_btn);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewNews.setLayoutManager(mLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this, new ArrayList<>());
        recyclerViewNews.setAdapter(recyclerViewAdapter);

        rssFeedDataManager = new RssFeedDataManager(this);


        dbHelper = new DatabaseHelper(this);

        // Here onSuccess interface invoked to rssObject.items
        rssFeedDataManager.fetchRss(new RssFeedDataManager.OnResponse() {
            @Override
            public void onSuccess(RssObject rssObject) {
                MainActivity.this.rssObject = rssObject;
                recyclerViewAdapter.setRecyclerItemValues(rssObject.items);
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(MainActivity.this, "cannot fetch error", Toast.LENGTH_SHORT).show();
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String key = searchNews.getText().toString();
                    Log.d("Search News Key", "Key : " + key);
                    ArrayList<Item> searchArrList = ItemDB.findNewsByTitle(dbHelper, key);
                    Log.d("Array List inside", "AL : " + searchArrList.toString());
                    recyclerViewAdapter.setRecyclerItemValues(searchArrList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        refillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter.setRecyclerItemValues(rssObject.items);
            }
        });

    }

}