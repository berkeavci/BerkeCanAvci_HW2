package com.avci.hw2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyRecyclerViewItemHolder> {
    private Context context;
    private ArrayList<Feed> recyclerItemValues;

    public RecyclerViewAdapter(Context context, ArrayList<Feed> values){
        this.context = context;
        this.recyclerItemValues = values;
    }

    @NonNull
    @Override
    public MyRecyclerViewItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflator = LayoutInflater.from(viewGroup.getContext());

        View itemView = inflator.inflate(R.layout.recycler_item, viewGroup, false);

        MyRecyclerViewItemHolder mViewHolder = new MyRecyclerViewItemHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewItemHolder myRecyclerViewItemHolder, int i) {

        final Feed FeedItem = recyclerItemValues.get(i);

        myRecyclerViewItemHolder.tvFeedName.setText(FeedItem.getName());
        String imagenameWithaddress= FeedItem.getImgName();

        Picasso.with(context)
                .load(imagenameWithaddress)
                .into(myRecyclerViewItemHolder.imgFeed);

        myRecyclerViewItemHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, FeedItem.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return recyclerItemValues.size();
    }

    class MyRecyclerViewItemHolder extends  RecyclerView.ViewHolder{
        TextView tvFeedName;
        ImageView imgFeed;
        LinearLayout parentLayout;
        public MyRecyclerViewItemHolder(@NonNull View itemView) {
            super(itemView);
            tvFeedName = itemView.findViewById(R.id.tvFeedName);
            imgFeed = itemView.findViewById(R.id.imgFeed);
            parentLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
