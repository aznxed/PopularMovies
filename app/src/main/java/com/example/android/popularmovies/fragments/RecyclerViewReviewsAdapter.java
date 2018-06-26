package com.example.android.popularmovies.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;

import java.util.List;

public class RecyclerViewReviewsAdapter extends RecyclerView.Adapter<RecyclerViewReviewsAdapter.ReviewHolders>{

    private List<String> reviewList;

    public RecyclerViewReviewsAdapter(List<String> reviewList){
        this.reviewList = reviewList;
    }

    @Override
    public ReviewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_view, null);
        return new ReviewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(ReviewHolders holder, int position) {
        holder.author.setText(reviewList.get(position*2));
        holder.content.setText(reviewList.get((position*2)+1));
    }

    @Override
    public int getItemCount() {
        return this.reviewList.size()/2;
    }

    class ReviewHolders extends RecyclerView.ViewHolder{
        private TextView author;
        private TextView content;

        public ReviewHolders(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
        }
    }
}
