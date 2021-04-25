package com.avci.hw2;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends ArrayAdapter<Item> {

    Context context;
    ArrayList<Item> newsSpinner;

    public CustomSpinnerAdapter(@NonNull Context context, @NonNull ArrayList<Item> newsSpinner) {
        super(context, R.layout.spinner_news_item, newsSpinner);
        this.context = context;
        this.newsSpinner = newsSpinner;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    public View customView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_news_item, parent, false);

        ImageReplacement ir = new ImageReplacement(context);
        Item selectedNews = newsSpinner.get(position); // put image name and author to here
        ImageView imgNews = view.findViewById(R.id.imgNews);
        TextView authorNews = view.findViewById(R.id.authorNews);

        ir.loadImage(selectedNews.getThumbnail(), imgNews);
        authorNews.setText(selectedNews.getAuthor());

        return view;

    }
}
