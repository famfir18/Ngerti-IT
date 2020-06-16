package com.example.ngertiit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LifeHackAdapter extends RecyclerView.Adapter<LifeHackAdapter.MyviewHolder> {
    Context context;
    List<DataLifehacks> mylist;
    OnItemSelected onItemSelected;


    public LifeHackAdapter(Context context, List<DataLifehacks> mylist,
                               OnItemSelected onItemSelected){
        this.context = context;
        this.mylist = mylist;
        this.onItemSelected = onItemSelected;
    }

    public void setMovieList(List<DataLifehacks> myList) {
        this.mylist = myList;
        notifyDataSetChanged();
    }

    @Override
    public LifeHackAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_content,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(LifeHackAdapter.MyviewHolder holder,
                                 int position) {

        DataLifehacks dataLifehacks = mylist.get(position);
        String imageUrl = dataLifehacks.getImage();

        holder.tvTitle.setText(dataLifehacks.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected.onSelected(dataLifehacks);
            }
        });

        String div = "<div>";
        String closeDiv = "</div>";
        String description = dataLifehacks.getDescription();
        String descriptionSubs = description.substring(0,80) + "...";

        if (descriptionSubs.contains(closeDiv) || description.contains(div)){
            descriptionSubs = descriptionSubs.replaceAll(div, "");
            descriptionSubs = descriptionSubs.replaceAll(closeDiv,"");
            holder.tvDescription.setText(descriptionSubs);
        } else {
            holder.tvDescription.setText(descriptionSubs);
        }

        if (dataLifehacks.getCategory().equals("Windows")) {
            holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_windows_browser));
        } else if (dataLifehacks.getCategory().equals("macOS")) {
            holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_macos_browser));
        }
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
        void onSelected(DataLifehacks dataLifehacks);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView tvDescription;
        TextView tvTitle;
        ImageView ivBanner;

        public MyviewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tv_lifehack_desc);
            tvTitle = itemView.findViewById(R.id.tv_lifehack);
            ivBanner = itemView.findViewById(R.id.iv_banner);
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
    public LifeHackAdapter(List<String> horizontalList)
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

