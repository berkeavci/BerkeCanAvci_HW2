package com.avci.hw2.data;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageReplacement {

    private Context context;

    public ImageReplacement(Context context) {
        this.context = context;
    }

    public void loadImage(String thumbnail, ImageView location){
        Picasso.with(context)
                .load(thumbnail)
                .into(location);
    }
}
