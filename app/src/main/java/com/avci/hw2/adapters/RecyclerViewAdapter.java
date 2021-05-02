package com.avci.hw2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.avci.hw2.data.ImageReplacement;
import com.avci.hw2.NewsActivity;
import com.avci.hw2.R;
import com.avci.hw2.data.entities.Item;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemsRecyclerItemHolder> {
    private Context context;
    private ArrayList<Item> recyclerItemValues;

    public RecyclerViewAdapter(Context context, ArrayList<Item> values){
        this.context = context;
        this.recyclerItemValues = values;
    }

    public void setRecyclerItemValues(ArrayList<Item> recyclerItemValues) {
        this.recyclerItemValues = recyclerItemValues;
        this.notifyDataSetChanged();
        // as we sat arrayList, we nofity the change
    }

    @NonNull
    @Override
    public ItemsRecyclerItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.recycler_item, viewGroup, false);
        return new ItemsRecyclerItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsRecyclerItemHolder myRecyclerViewItemHolder, int i) {
        final Item items = recyclerItemValues.get(i);

        myRecyclerViewItemHolder.tvItemsName.setText(items.title);
        myRecyclerViewItemHolder.tvPublicationDate.setText(items.pubDate);
        ImageReplacement ir = new ImageReplacement(context);
        ir.loadImage(items.thumbnail, myRecyclerViewItemHolder.imgItems);

        myRecyclerViewItemHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Goes to Second Activity
                Intent intent = new Intent(context, NewsActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("itemsKey", items); // Selected item list goes to news activity
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerItemValues.size();
    }

    class ItemsRecyclerItemHolder extends  RecyclerView.ViewHolder{
        TextView tvItemsName, tvPublicationDate;
        ImageView imgItems;
        ConstraintLayout parentLayout;
        public ItemsRecyclerItemHolder(@NonNull View itemView) {
            super(itemView);
            tvItemsName = itemView.findViewById(R.id.tvItemsName);
            tvPublicationDate = itemView.findViewById(R.id.tvPublicationDate);
            imgItems = itemView.findViewById(R.id.imgItems);
            parentLayout = itemView.findViewById(R.id.ItemsItemParent);
        }
    }
}
