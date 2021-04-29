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

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewNews;
    ImageButton searchBtn;
    EditText searchNews;
    TextView userTV, fav_cryptoTV, fav_news_websiteTV;
    DatabaseHelper dbHelper;
    LinearLayoutManager mLayoutManager;

    RecyclerViewAdapter recyclerViewAdapter;
    RssFeedDataManager rssFeedDataManager;

    ArrayList<Item> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        searchBtn = findViewById(R.id.searchBtn);
        searchNews = findViewById(R.id.searchNews);
        userTV = findViewById(R.id.userTV);
        fav_cryptoTV = findViewById(R.id.fav_cryptoTV);
        fav_news_websiteTV = findViewById(R.id.fav_news_websiteTV);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewNews.setLayoutManager(mLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this, items);
        recyclerViewNews.setAdapter(recyclerViewAdapter);

        rssFeedDataManager = new RssFeedDataManager(this);



        // Here onSuccess interface invoked to rssObject.items
        rssFeedDataManager.fetchRss(new RssFeedDataManager.OnResponse() {
            @Override
            public void onSuccess(RssObject rssObject) {
                recyclerViewAdapter.setRecyclerItemValues(rssObject.items);
                recyclerViewAdapter.notifyDataSetChanged();
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
                    String key;
                    key = searchNews.getText().toString();
                    Log.d("Search News Key", "Key : " + key);
                    ArrayList<Item> searchArrList = new ArrayList<>();
                    searchArrList = null;
                    searchArrList.addAll(ItemDB.findNewsByTitle(dbHelper, key));
                    Log.d("Array List inside", "AL : " + searchArrList.toString());
                    recyclerViewAdapter.setRecyclerItemValues(searchArrList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        // Copy Database from the assests folder to data/data/database folder.
//        try {
//            String fileToDatabase = "/data/data/" + getPackageName() + "/databases/"+DatabaseHelper.DATABASE_NAME;
//            File file = new File(fileToDatabase);
//            File pathToDatabasesFolder = new File("/data/data/" + getPackageName() + "/databases/");
//            if (!file.exists()) {
//                pathToDatabasesFolder.mkdirs();
//                Log.d("BURDA", "BURDA");
//                CopyDB( getResources().getAssets().open(DatabaseHelper.DATABASE_NAME+".db"),
//                        new FileOutputStream(fileToDatabase));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        dbHelper = new DatabaseHelper(this);


    }

//    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
//        // Copy 1K bytes at a time
//        byte[] buffer = new byte[1024];
//        int length;
//        Log.d("BURDA", "BURDA2");
//
//        while ((length = inputStream.read(buffer)) > 0) {
//            outputStream.write(buffer, 0, length);
//            Log.d("BURDA", "BURDA3");
//        }
//        inputStream.close();
//        outputStream.close();
//    }




}