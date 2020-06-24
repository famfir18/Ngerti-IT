package com.example.ngertiit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ngertiit.Data.JSON.DataDictionary;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.Data.JSON.DataSolution;
import com.example.ngertiit.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyviewHolder> {

        Context context;
        List<DataDictionary> dictionaryList;
        OnItemSelected onItemSelected;
        ArrayList<Object> arrayList;

        public SearchAdapter(Context context, List<DataDictionary> dictionaryList, ArrayList<Object> arrayList,
                                OnItemSelected onItemSelected){

            this.arrayList = arrayList;
            this.context = context;
            this.dictionaryList = dictionaryList;
            this.onItemSelected = onItemSelected;
        }

        public void setMovieList(ArrayList<Object> searchList) {

            this.arrayList = searchList;
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
                DataDictionary dataDictionary = dictionaryList.get(position);

                holder.tvTitle.setText(dataDictionary.getTitle());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemSelected.onSelected(dataDictionary);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setFilter(List<DataDictionary> filterList){
            dictionaryList = filterList;
            dictionaryList.addAll(filterList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if(arrayList != null){
                return arrayList.size();
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

            TextView tvTitle;

            public MyviewHolder(View itemView) {
                super(itemView);

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

