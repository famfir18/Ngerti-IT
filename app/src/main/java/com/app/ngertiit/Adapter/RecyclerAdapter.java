package com.app.ngertiit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.ngertiit.Data.JSON.DataTestSDG;
import com.app.ngertiit.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {

    Context context;
    List<DataTestSDG> myList;
    OnItemSelected onItemSelected;

    public RecyclerAdapter(Context context, List<DataTestSDG> myList,
                           OnItemSelected onItemSelected) {
        this.context = context;
        this.myList = myList;
        this.onItemSelected = onItemSelected;
    }

    public void setMovieList(List<DataTestSDG> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_kamus,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyviewHolder holder, int position) {

        DataTestSDG testSDG = myList.get(position);


        holder.tvKamus.setText(myList.get(position).getEventName());
        holder.tvJudul.setText(myList.get(position).getDeskripsi());

        holder.itemView.setOnClickListener(v -> {
            onItemSelected.onSelected(testSDG);

        });

    }

    @Override
    public int getItemCount() {
        if(myList != null){
            return myList.size();
        }
        return 0;

    }

    public interface OnItemSelected {
        void onSelected(DataTestSDG dataTestSDG);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvKamus;
        TextView tvJudul;

        public MyviewHolder(View itemView) {
            super(itemView);
            tvKamus = (TextView)itemView.findViewById(R.id.tv_kamus);
            tvJudul = itemView.findViewById(R.id.tv_lifehack);
        }
    }
}
