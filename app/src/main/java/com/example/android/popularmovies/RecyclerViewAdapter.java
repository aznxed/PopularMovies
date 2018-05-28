package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders>{
    private List<movieObject> movieList;
    private Context context;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecyclerViewAdapter(Context context, List<movieObject> movieList, ListItemClickListener mOnClickListener){
        this.context = context;
        this.movieList = movieList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        return new RecyclerViewHolders(layoutView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {
        Picasso.with(context).load(movieList.get(position).getPhoto()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder implements OnClickListener {

        public ImageView movieImage;

        public RecyclerViewHolders(View itemView) {
            super(itemView);

            movieImage = (ImageView)itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);
        }

        void bind(int listPostion){

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}


