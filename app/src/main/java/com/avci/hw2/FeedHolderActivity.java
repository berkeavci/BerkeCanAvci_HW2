package com.avci.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FeedHolderActivity extends AppCompatActivity {

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

        newsImageIV = findViewById(R.id.newsImageIV);
        pubdateTV = findViewById(R.id.pubdateTV);
        titTV = findViewById(R.id.titTV);
        articTV = findViewById(R.id.articTV);
        newsSpinner = findViewById(R.id.newsSpinner);
        MainActivityBtn = findViewById(R.id.MainActivityBtn);
        savedNews = new ArrayList<>();
        ir = new ImageReplacement(this);

        // Intent Retrieve Saved News - NewsActivity
        intent = getIntent();
        Bundle b = intent.getExtras();
        savedNewsItem = b.getParcelable("savedItems");
        //Collections.addAll(savedNews, savedNewsItem);
        ItemDB.insert(dbHelper, savedNewsItem);
        savedNews = ItemDB.getAllItem(dbHelper);
        CustomSpinnerAdapter custAdapt = new CustomSpinnerAdapter(this, savedNews);
        newsSpinner.setAdapter(custAdapt);

        newsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ir.loadImage(savedNews.get(position).getThumbnail(), newsImageIV);
                pubdateTV.setText(savedNews.get(position).getPubDate());
                titTV.setText(savedNews.get(position).getTitle()); // get or direct?
                articTV.setText(Utility.htmlToText(savedNews.get(position).getContent()));
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


    }

    public void dialogCreation(){
        //customDialog = new Dialog(this);
        //customDialog.setContentView(R.layout.dialog_favorites);
    }

}