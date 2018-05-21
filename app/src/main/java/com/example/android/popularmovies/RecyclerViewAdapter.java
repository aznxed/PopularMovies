package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>{
    private List<movieObject> movieList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<movieObject> movieList){
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {
        holder.movieName.setText(movieList.get(position).getName());
        holder.movieImage.setImageResource(movieList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }
}