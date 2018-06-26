package com.example.android.popularmovies.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;

public class RecyclerViewFavoriteAdapter extends RecyclerView.Adapter<RecyclerViewFavoriteAdapter.FavoriteHolders>{

    private Cursor cursor;
    final private OnItemClickListener mOnClickListener;
    final private OnItemLongClickListener mOnLongClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }

    public RecyclerViewFavoriteAdapter (OnItemClickListener mOnClickListener, OnItemLongClickListener mOnLongClickListener ){
        this.mOnClickListener = mOnClickListener;
        this.mOnLongClickListener = mOnLongClickListener;
    }

    @Override
    public FavoriteHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_view, null);
        return new FavoriteHolders(layoutView);
    };

    @Override
    public void onBindViewHolder(FavoriteHolders holder, int position) {
        int index = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME);
        cursor.moveToPosition(position);

        String favoriteName = cursor.getString(nameIndex);
        final int id = Integer.parseInt(cursor.getString(index));

        holder.itemView.setTag(id);
        holder.favoriteName.setText(favoriteName);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (cursor == c) {
            return; // bc nothing has changed
        }

        Cursor temp = cursor;
        this.cursor = c; // new cursor value assigned
        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
    }

    class FavoriteHolders extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{

        private TextView favoriteName;
        private View v;

        private FavoriteHolders(View itemView) {
            super(itemView);

            this.v = itemView;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            favoriteName = itemView.findViewById(R.id.favorite_name);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onItemClicked(clickedPosition);
        }

        @Override
        public boolean onLongClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnLongClickListener.onItemLongClicked(clickedPosition);
            return true;
        }
    }
}

