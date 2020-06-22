package com.example.ngertiit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ngertiit.Data.JSON.DataDictionary;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.MyviewHolder> {
    Context context;
    List<DataDictionary> mylist;
    OnItemSelected onItemSelected;


    public DictionaryAdapter(Context context, List<DataDictionary> mylist,
                                 OnItemSelected onItemSelected){
        this.context = context;
        this.mylist = mylist;
        this.onItemSelected = onItemSelected;
    }

    public void setMovieList(List<DataDictionary> myList) {
        this.mylist = myList;
        notifyDataSetChanged();
    }

    @Override
    public DictionaryAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_kamus,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(DictionaryAdapter.MyviewHolder holder,
                                 int position) {

        DataDictionary dataDictionary = mylist.get(position);

        holder.tvTitle.setText(dataDictionary.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected.onSelected(dataDictionary);
            }
        });

        String div = "<span style=\"font-family: Arial; font-size: 15px; white-space: pre-wrap; background-color: #ffffff;\">";
        String closeDiv = "</span>";
        String description = dataDictionary.getDescription();
        String descriptionSubs = description;

        if (descriptionSubs.contains(closeDiv) || description.contains(div)){
            descriptionSubs = descriptionSubs.replaceAll(div, "");
            descriptionSubs = descriptionSubs.replaceAll(closeDiv,"");
            holder.tvDescription.setText(descriptionSubs);
        } else {
            holder.tvDescription.setText(descriptionSubs);
        }
    }

    public void setFilter(List<DataDictionary> filterList){
        mylist = filterList;
        mylist.addAll(filterList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mylist != null){
            return mylist.size();
        }
        return 0;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnItemSelected {
        void onSelected(DataDictionary dataDictionary);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView tvDescription;
        TextView tvTitle;

        public MyviewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tv_lifehack);
            tvTitle = itemView.findViewById(R.id.tv_kamus);
        }
    }

    /*public class MyView
            extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            textView = (TextView)view
                    .findViewById(R.id.tv_lifehack);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public DictionaryAdapter(List<String> horizontalList)
    {
        this.list = horizontalList;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_content,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final MyView holder, final int position)
    {
     *//*   int lastPosition = 0;
        if (holder.getAdapterPosition() > lastPosition) {

            AnimationUtilSide.animate(holder, true);
        }else {
            AnimationUtilSide.animate(holder, false);

        }
        lastPosition = holder.getAdapterPosition();*//*

        // Set the text of each item of
        // Recycler view with the list items
        holder.textView.setText(list.get(position));
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }*/
}

