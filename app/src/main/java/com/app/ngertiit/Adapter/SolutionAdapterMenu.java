package com.app.ngertiit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ngertiit.Data.JSON.DataSolution;
import com.app.ngertiit.R;

import java.util.List;

import butterknife.ButterKnife;

public class SolutionAdapterMenu extends RecyclerView.Adapter<SolutionAdapterMenu.MyviewHolder> {
    Context context;
    List<DataSolution> mylist;
    OnItemSelected onItemSelected;


    public SolutionAdapterMenu(Context context, List<DataSolution> mylist,
                               OnItemSelected onItemSelected){
        this.context = context;
        this.mylist = mylist;
        this.onItemSelected = onItemSelected;
    }

    public void setMovieList(List<DataSolution> myList) {
        this.mylist = myList;
        notifyDataSetChanged();
    }

    @Override
    public SolutionAdapterMenu.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_content,parent,false);
        ButterKnife.bind(view);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(SolutionAdapterMenu.MyviewHolder holder,
                                 int position) {

        final Animation animScaleTitle = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);

        for (int i = 0; i < position; i++){
            holder.card.startAnimation(animScaleTitle);
        }

        DataSolution dataSolution = mylist.get(position);
        String imageUrl = "https:banyakngerti.id" + dataSolution.getImage();

        holder.tvTitle.setText(dataSolution.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected.onSelected(dataSolution);
            }
        });

        String em = "<em>";
        String closeEm = "</em>";
        String description = dataSolution.getDescription();
        if (description.length() >= 80) {
            String descriptionSubs = description/*.substring(0,80) + "..."*/;

            if (descriptionSubs.contains(closeEm) || description.contains(em)){
                descriptionSubs = descriptionSubs.replaceAll(em, "");
                descriptionSubs = descriptionSubs.replaceAll(closeEm,"");
                holder.tvDescription.setText(descriptionSubs);
            } else {
                holder.tvDescription.setText(descriptionSubs);
            }
        } else {
            if (description.contains(closeEm) || description.contains(em)){
                description = description.replaceAll(em, "");
                description = description.replaceAll(closeEm,"");
                holder.tvDescription.setText(description);
            } else {
                holder.tvDescription.setText(description);
            }
        }

        switch (dataSolution.getCategory()) {
            case "Windows":
                holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_windows_solution));
                break;
            case "macOS":
                holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_macos_solution));
                break;
            case "Android":
                holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_android_solution));
                break;
            case "iOS":
                holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_ios_solution));
                break;
        }

        /*Picasso.with(context)
                .load(imageUrl)
                .into(holder.ivBanner);
*/
//        if (dataSolution.getCategory().equals("Windows")) {
//            holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_windows_browser));
//        } else if (dataSolution.getCategory().equals("macOS")) {
//            holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_macos_browser));
//        }
    }

    @Override
    public int getItemCount() {
        if(mylist != null){
            return 5;
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
        void onSelected(DataSolution DataSolution);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView tvDescription;
        TextView tvTitle;
        ImageView ivBanner;
        CardView card;

        public MyviewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tv_lifehack_desc);
            tvTitle = itemView.findViewById(R.id.tv_lifehack);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            card = itemView.findViewById(R.id.card);
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
    public SolutionAdapterMenu(List<String> horizontalList)
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

