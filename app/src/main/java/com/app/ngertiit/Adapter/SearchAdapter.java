package com.app.ngertiit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.ngertiit.Data.JSON.DataDictionary;
import com.app.ngertiit.Data.JSON.DataSearch;
import com.app.ngertiit.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyviewHolder> {

        Context context;
        List<DataSearch> searchList;
        OnItemSelected onItemSelected;
//        ArrayList<Object> arrayList;

        public SearchAdapter(Context context, List<DataSearch> searchList,
                                OnItemSelected onItemSelected){

            this.context = context;
            this.searchList = searchList;
            this.onItemSelected = onItemSelected;
        }

        public void setMovieList(List<DataSearch> searchList) {

            this.searchList = searchList;
            notifyDataSetChanged();
        }

        @Override
        public SearchAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_history,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(SearchAdapter.MyviewHolder holder,
                                     int position) {

            try {
                String div = "<div>";
                String closeDiv = "</div>";
                DataSearch dataSearch = searchList.get(position);
                String description = dataSearch.getDescription();
//                String descriptionSubs = description.substring(0,80) + "...";

                if (description.length() >= 80) {
                    String descriptionSubs = description.substring(0,80) + "...";

                    if (descriptionSubs.contains(closeDiv) || description.contains(div)){
                        descriptionSubs = descriptionSubs.replaceAll(div, "");
                        descriptionSubs = descriptionSubs.replaceAll(closeDiv,"");
                        holder.tvDescription.setText(descriptionSubs);
                    } else {
                        holder.tvDescription.setText(descriptionSubs);
                    }
                } else {
                    if (description.contains(closeDiv) || description.contains(div)){
                        description = description.replaceAll(div, "");
                        description = description.replaceAll(div,"");
                        holder.tvDescription.setText(description);
                    } else {
                        holder.tvDescription.setText(description);
                    }
                }

                holder.tvTitle.setText(dataSearch.getTitle());
                holder.tvKategori.setText(dataSearch.getCategory());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemSelected.onSelected(dataSearch);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*public void setFilter(List<DataDictionary> filterList){
            dictionaryList = filterList;
            dictionaryList.addAll(filterList);
            notifyDataSetChanged();
        }*/

        @Override
        public int getItemCount() {
            if(searchList != null){
                return searchList.size();
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
            void onSelected(DataSearch dataSearch);
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            TextView tvDescription;
            TextView tvKategori;

            public MyviewHolder(View itemView) {
                super(itemView);

                tvTitle = itemView.findViewById(R.id.tv_kamus);
                tvDescription = itemView.findViewById(R.id.tv_tanggal);
                tvKategori = itemView.findViewById(R.id.tv_category);
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

