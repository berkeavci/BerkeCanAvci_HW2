package com.avci.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class FeedHolderActivity extends AppCompatActivity {

    static int pos = 0;
    private GestureDetectorCompat feedDetector;
    ImageView newsImageIV;
    TextView pubdateTV, titTV, articTV;
    Spinner newsSpinner;
    Intent intent;
    Dialog customDialog;
    Item savedNewsItem;
    ArrayList<Item> savedNews;
    ImageReplacement ir;
    ImageButton MainActivityBtn;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_holder);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        newsImageIV = findViewById(R.id.newsImageIV);
        pubdateTV = findViewById(R.id.pubdateTV);
        titTV = findViewById(R.id.titTV);
        articTV = findViewById(R.id.articTV);
        newsSpinner = findViewById(R.id.newsSpinner);
        MainActivityBtn = findViewById(R.id.MainActivityBtn);
        GestureFeedHolder gfh = new GestureFeedHolder();
        feedDetector = new GestureDetectorCompat(this, gfh);
        dbHelper = new DatabaseHelper(this);
        savedNews = new ArrayList<>();
        ir = new ImageReplacement(this);

        // Intent Retrieve Saved News - NewsActivity
        intent = getIntent();
        Bundle b = intent.getExtras();
        savedNewsItem = b.getParcelable("savedItems");
        //Collections.addAll(savedNews, savedNewsItem);
        try {
            ItemDB.insertItems(dbHelper, savedNewsItem);
            savedNews = ItemDB.getAllItem(dbHelper);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomSpinnerAdapter custAdapt = new CustomSpinnerAdapter(this, savedNews);
        newsSpinner.setAdapter(custAdapt);

        newsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ir.loadImage(savedNews.get(position).getThumbnail(), newsImageIV);
                pubdateTV.setText(savedNews.get(position).getPubDate());
                titTV.setText(savedNews.get(position).getTitle()); // get or direct?
                articTV.setText(Utility.htmlToText(savedNews.get(position).getContent()));
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MainActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedHolderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        newsImageIV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                return feedDetector.onTouchEvent(motionEvent);
            }
        });
    }


    class GestureFeedHolder extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Toast.makeText(FeedHolderActivity.this, "onDoubleTapEvent Over Image",Toast.LENGTH_LONG).show();
            dialogCreation(pos);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public void dialogCreation(int position){
        customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.dialog_choice);

        TextView feedInfoTV = customDialog.findViewById(R.id.feedInfoTV);
        Button back_btn, deleteButton;
        back_btn = customDialog.findViewById(R.id.back_btn);
        deleteButton = customDialog.findViewById(R.id.deleteButton);

        String content = Utility.htmlToText(savedNews.get(position).getContent());
        String result = savedNews.get(position).getAuthor() + "   " + savedNews.get(position).getCategories() + " " + content;
        feedInfoTV.append(result);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                // ? Not Working
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res = ItemDB.delete(dbHelper, savedNews.get(position).getId());
                if(res) {
                    Toast.makeText(FeedHolderActivity.this, "News Deleted!", Toast.LENGTH_LONG).show();
                    customDialog.dismiss();
                }
            }
        });

        customDialog.show();

    }


}