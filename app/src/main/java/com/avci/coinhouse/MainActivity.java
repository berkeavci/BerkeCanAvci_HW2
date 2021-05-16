package com.avci.coinhouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.avci.coinhouse.adapters.RecyclerViewAdapter;
import com.avci.coinhouse.data.database.DatabaseHelper;
import com.avci.coinhouse.data.database.ItemDB;
import com.avci.coinhouse.data.RssFeedDataManager;
import com.avci.coinhouse.data.entities.Binance;
import com.avci.coinhouse.data.entities.Item;
import com.avci.coinhouse.data.entities.RssObject;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewNews;
    ImageButton searchBtn;
    ImageView refresh_btn;
    EditText searchNews;
    Intent intent;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    RssFeedDataManager rssFeedDataManager;
    DatabaseHelper dbHelper;
    ConstraintLayout mainActivity_layout;
    CoinFragment fragment_bottom;
    IntentFilter intentF;
    private GestureDetectorCompat gDetector;
    static String username="";
    RssObject rssObject;
    boolean doubleTap = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_bottom = (CoinFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bottom);
        intent = new Intent(this, BinanceService.class);
        startService(intent);

        // Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Service
        intentF = new IntentFilter();
        intentF.addAction("COIN_INFORMATION");
        registerReceiver(broadcastreceiver, intentF);

        // Reset Double Tap
        doubleTap = false;

        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        searchBtn = findViewById(R.id.searchBtn);
        searchNews = findViewById(R.id.searchNews);
        mainActivity_layout = findViewById(R.id.mainActivity_layout);
        refresh_btn = findViewById(R.id.refresh_btn);


        // Gesture
        MainActivity.MainGestureListener mgl = new MainActivity.MainGestureListener();
        gDetector = new GestureDetectorCompat(this, mgl);


        //refillButton = findViewById(R.id.refill_btn);
        intent =  getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");
        Log.d("MainActivity Username", "UserNameM: "+username.toString());

        searchBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gDetector.onTouchEvent(event);
            }
        });

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
                    if(!doubleTap) {
                        String key = searchNews.getText().toString();
                        Log.d("Search News Key", "Key : " + key);
                        ArrayList<Item> searchArrList = ItemDB.findNewsByTitle(dbHelper, key);
                        Log.d("Array List inside", "AL : " + searchArrList.toString());
                        recyclerViewAdapter.setRecyclerItemValues(searchArrList);
                        doubleTap = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter.setRecyclerItemValues(rssObject.items);
            }
        });
    }

    public static String getUsername() {
        return username;
    }

        BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle b = intent.getExtras();
                Binance bin = b.getParcelable("binanceInfo");
                //Toast.makeText(MainActivity.this, "BINANCE FILLED "+ bin.toString(), Toast.LENGTH_LONG).show();
                Log.d("BROADCASTSERVICE", "BROADCAST!!!!" + bin.toString());
                b.putParcelable("binanceInfo", bin);
                fragment_bottom.binanceUpdate(bin, username);
            }
        };

    class MainGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            recyclerViewAdapter.setRecyclerItemValues(rssObject.items);
            Log.d("DOUBLETAPLANDI", "onDoubleTapEvent: ");
            doubleTap = false;
            return super.onDoubleTapEvent(e);
        }
    }





}