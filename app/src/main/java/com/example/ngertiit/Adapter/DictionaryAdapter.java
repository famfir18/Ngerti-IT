package com.example.ngertiit.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataTestSDG;
import com.example.ngertiit.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.MyView> {
    private List<String> list;
    TextView textView;
    TextView tvJudul;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        // Text View


        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            textView = view.findViewById(R.id.tv_lifehack);
            tvJudul = view.findViewById(R.id.tv_kamus);
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
                .inflate(R.layout.view_kamus,
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

        // Set the text of each item of
        // Recycler view with the list items
//        holder.textView.setText(list.get(position));
    }

    private void getData() {

        Gson gson = new Gson();

        RestService restService = APIClient.getClient().create(RestService.class);
        Call<List<DataTestSDG>> call = restService.getDataSDG();

        call.enqueue(new Callback<List<DataTestSDG>>() {
            @Override
            public void onResponse(Call<List<DataTestSDG>> call, Response<List<DataTestSDG>> response) {

            for (int i = 0; i < response.body().size(); i++){

                if (response.isSuccessful()){
                    tvJudul.setText(response.body().get(i).getEventName());
                    textView.setText(response.body().get(i).getDeskripsi());

                    System.out.println("Hasil Datanya Adalah : " + gson.toJson(response));
                }
            }


            }

            @Override
            public void onFailure(Call<List<DataTestSDG>> call, Throwable t) {

            }
        });
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }
}

