package com.app.ngertiit.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ngertiit.Data.JSON.DataDictionary;
import com.app.ngertiit.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import static android.content.ContentValues.TAG;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.MyviewHolder> {
    Context context;
    List<DataDictionary> mylist;
    OnItemSelected onItemSelected;

    long DURATION = 250;
    private boolean on_attach = true;

    boolean clicked = false;

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
        View view = LayoutInflater.from(context).inflate(R.layout.view_konten_kamus,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(DictionaryAdapter.MyviewHolder holder,
                                 int position) {

        setAnimation(holder.itemView, position);

        DataDictionary dataDictionary = mylist.get(position);

        holder.contentPertama.setText(dataDictionary.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected.onSelected(dataDictionary);
            }
        });

        holder.expandableLayout0.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        holder.contentPertama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.expandableLayout0.isExpanded()) {
                    holder.contentPertama.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.expandableLayout0.expand();
                    clicked = true;
                } else if (clicked = true){
                    holder.contentPertama.setBackgroundColor(context.getResources().getColor(R.color.putih));
                    holder.expandableLayout0.collapse();
                }
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
        TextView contentPertama;
        LinearLayout layout;

        ExpandableLayout expandableLayout0;

        public MyviewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tv_lifehack);
            contentPertama = itemView.findViewById(R.id.expand_button_0);
            expandableLayout0 = itemView.findViewById(R.id.expandable_layout_0);
            layout = itemView.findViewById(R.id.layout);
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

