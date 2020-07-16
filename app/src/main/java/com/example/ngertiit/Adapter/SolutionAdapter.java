package com.example.ngertiit.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngertiit.Data.JSON.DataSolution;
import com.example.ngertiit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.MyviewHolder> {
    Context context;
    List<DataSolution> mylist;
    OnItemSelected onItemSelected;

    long DURATION = 250;
    private boolean on_attach = true;

    public SolutionAdapter(Context context, List<DataSolution> mylist,
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
    public SolutionAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_content,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(SolutionAdapter.MyviewHolder holder,
                                 int position) {

        setAnimation(holder.itemView, position);

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
            String descriptionSubs = description.substring(0,80) + "...";

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

        Picasso.with(context)
                .load(imageUrl)
                .into(holder.ivBanner);

//        if (dataSolution.getCategory().equals("Windows")) {
//            holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_windows_browser));
//        } else if (dataSolution.getCategory().equals("macOS")) {
//            holder.ivBanner.setImageDrawable(context.getResources().getDrawable(R.drawable.banner_macos_browser));
//        }
    }

    @Override
    public int getItemCount() {
        if(mylist != null){
            return mylist.size();
        }
        return 0;

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d(TAG, "onScrollStateChanged: Called " + newState);
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean isNotFirstItem = i == -1;
        i++;
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", 0.f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animator.setStartDelay(isNotFirstItem ? DURATION / 2 : (i * DURATION / 3));
        animator.setDuration(500);
        animatorSet.play(animator);
        animator.start();
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
    public SolutionAdapter(List<String> horizontalList)
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

