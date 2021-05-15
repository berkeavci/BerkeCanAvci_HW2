package com.avci.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avci.hw2.data.ImageReplacement;
import com.avci.hw2.data.PlaySound;
import com.avci.hw2.data.Utility;
import com.avci.hw2.data.database.DatabaseHelper;
import com.avci.hw2.data.database.ItemDB;
import com.avci.hw2.data.entities.Binance;
import com.avci.hw2.data.entities.Item;

public class NewsActivity extends AppCompatActivity {

    Intent intent;
    Item selectedNewsItem;
    TextView articleTV, titleTV;
    ImageButton addBtn, rss_feed_btn;
    ImageView newsImage;
    FrameLayout frameLayout;
    ImageReplacement ir;
    DatabaseHelper dbhelper;
    CoinFragment fragment_bottom_news;
    IntentFilter intentF;
    private SoundPool soundpool;
    private GestureDetectorCompat gDetector;
    static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragment_bottom_news = (CoinFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bottom_news);
        intent = new Intent(this, BinanceService.class);
        startService(intent);
        // Service
        intentF = new IntentFilter();
        intentF.addAction("COIN_INFORMATION");
        registerReceiver(broadcastreceiver, intentF);

        //Declarations
        articleTV = findViewById(R.id.articleTV);
        titleTV = findViewById(R.id.titleTV);
        addBtn = findViewById(R.id.addBtn);
        newsImage = findViewById(R.id.newsImage);
        frameLayout = findViewById(R.id.frameLayout);
        rss_feed_btn = findViewById(R.id.rss_feed_btn);
        dbhelper = new DatabaseHelper(this);
        NewsGestureListener ngl = new NewsGestureListener();
        gDetector = new GestureDetectorCompat(this, ngl);

        // Intent Item Transfer
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        selectedNewsItem = b.getParcelable("itemsKey");
        username = b.getString("userName");

        // Setting the View
        ir = new ImageReplacement(this);
        ir.loadImage(selectedNewsItem.thumbnail, newsImage);
        titleTV.setText(selectedNewsItem.title);
        // Html Parse
        articleTV.setText(Utility.htmlToText(selectedNewsItem.content));


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sound Effect
                PlaySound ps = new PlaySound(NewsActivity.this);
                soundpool = ps.SoundImplementation();
                Log.d("ADDBUTTON", "onClick: ");

                ItemDB.insertItems(dbhelper, selectedNewsItem);

            }
        });

        rss_feed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, FeedHolderActivity.class);
                Bundle b = new Bundle();
                b.putString("userName", username);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        newsImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gDetector.onTouchEvent(event);
            }
        });

    }


    class NewsGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Toast.makeText(NewsActivity.this, "U have Invoked Double Tap Event!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(NewsActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundpool.release();
        soundpool = null;
    }

    BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            Binance bin = b.getParcelable("binanceInfo");
            //Toast.makeText(MainActivity.this, "BINANCE FILLED "+ bin.toString(), Toast.LENGTH_LONG).show();
            Log.d("BROADCASTSERVICENEWS", "BROADCAST!!!!" + bin.toString() + username);
            b.putParcelable("binanceInfo", bin);
            fragment_bottom_news.binanceUpdate(bin, username);

        }
    };

}