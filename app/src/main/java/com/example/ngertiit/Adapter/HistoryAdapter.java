package com.example.ngertiit.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngertiit.Data.JSON.DataHistory;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyviewHolder> {
    Context context;
    List<DataHistory> mylist;
    OnItemSelected onItemSelected;

    long DURATION = 250;
    private boolean on_attach = true;

    public HistoryAdapter(Context context, List<DataHistory> mylist,
                             OnItemSelected onItemSelected){
        this.context = context;
        this.mylist = mylist;
        this.onItemSelected = onItemSelected;
    }

    public void setMovieList(List<DataHistory> myList) {
        this.mylist = myList;
        notifyDataSetChanged();
    }

    @Override
    public HistoryAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_history,parent,false);
        return new MyviewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(HistoryAdapter.MyviewHolder holder,
                                 int position) {

        setAnimation(holder.itemView, position);

        try {
            DataHistory dataHistory = mylist.get(position);

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
            String date = dataHistory.getTanggal();
            Date date1= null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date1 != null;
            String dates = formatter.format(date1);

            holder.tvTitle.setText(dataHistory.getJudulArtikel());
            holder.tvCategory.setText(dataHistory.getLinkArtikel());
            holder.tanggal.setText(dates);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelected.onSelected(dataHistory);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
        void onSelected(DataHistory dataHistory);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory;
        TextView tvTitle;
        TextView tanggal;

        public MyviewHolder(View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tv_category);
            tvTitle = itemView.findViewById(R.id.tv_kamus);
            tanggal = itemView.findViewById(R.id.tv_tanggal);
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
    public HistoryAdapter(List<String> horizontalList)
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

