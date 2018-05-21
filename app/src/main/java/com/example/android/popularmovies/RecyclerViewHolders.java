package com.example.android.popularmovies;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView movieName;
    public ImageView movieImage;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        movieName = (TextView)itemView.findViewById(R.id.movie_name);
        movieImage = (ImageView)itemView.findViewById(R.id.movie_image);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked on Movie", Toast.LENGTH_SHORT).show();
    }
}
