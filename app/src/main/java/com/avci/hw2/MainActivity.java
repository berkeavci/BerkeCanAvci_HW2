package com.avci.hw2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.avci.hw2.adapters.RecyclerViewAdapter;
import com.avci.hw2.data.database.DatabaseHelper;
import com.avci.hw2.data.database.ItemDB;
import com.avci.hw2.data.RssFeedDataManager;
import com.avci.hw2.data.entities.Binance;
import com.avci.hw2.data.entities.Item;
import com.avci.hw2.data.entities.RssObject;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewNews;
    ImageButton searchBtn;
    EditText searchNews;
    Intent intent;
    Button refillButton;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    RssFeedDataManager rssFeedDataManager;
    DatabaseHelper dbHelper;
    RssObject rssObject;
    CoinFragment fragment_bottom;
    IntentFilter intentF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_bottom = (CoinFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bottom);
//         intent = new Intent(this, BinanceService.class);
//         startService(intent);
//         Toast.makeText(this, "After service start", Toast.LENGTH_LONG).show();

        // Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Service
        intentF = new IntentFilter();
        intentF.addAction("COIN_INFORMATION");
        registerReceiver(broadcastreceiver, intentF);


        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        searchBtn = findViewById(R.id.searchBtn);
        searchNews = findViewById(R.id.searchNews);



        //refillButton = findViewById(R.id.refill_btn);
        intent =  getIntent();
        Bundle b = intent.getExtras();
        String user, fav_w, fav_c;

        if (b != null) {
            user = b.getString("user");
            fav_w = b.getString("fav_crypto");
            fav_c = b.getString("fav_website");
            Toast.makeText(this, "Information Of User: " + user + fav_w + fav_c + " ",  Toast.LENGTH_LONG).show();
        }

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

//        refillButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerViewAdapter.setRecyclerItemValues(rssObject.items);
//            }
//        });

    }

        BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle b = intent.getExtras();
                Binance bin = b.getParcelable("binanceInfo");
                Toast.makeText(MainActivity.this, "BINANCE FILLED "+ bin.toString(), Toast.LENGTH_LONG).show();

                b.putParcelable("binanceInfo", bin);
                fragment_bottom.setArguments(b);

                // Fragment


                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_bottom, fragment_bottom);
                fragmentTransaction.commit();
            }
        };


}