package com.avci.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedHolderActivity extends AppCompatActivity {

    ImageView newsImageIV;
    TextView pubdateTV, titTV, articTV;
    Spinner newsSpinner;
    Intent intent;
    Dialog customDialog;
    ArrayList<Item> savedNews;
    ImageReplacement ir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_holder);

        newsImageIV = findViewById(R.id.newsImageIV);
        pubdateTV = findViewById(R.id.pubdateTV);
        titTV = findViewById(R.id.titTV);
        articTV = findViewById(R.id.articTV);
        newsSpinner = findViewById(R.id.newsSpinner);
        savedNews = new ArrayList<>();
        ir = new ImageReplacement(this);

        // Intent Retrieve Saved News
        intent = getIntent();
        Bundle b = intent.getExtras();
        savedNews = b.getParcelable("savedItems");

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


    }

    public void dialogCreation(){
        //customDialog = new Dialog(this);
        //customDialog.setContentView(R.layout.dialog_favorites);
    }

}