package com.example.android.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieContract;


/**
 * Created by Edward on 6/8/2018.
 */

public class FavoritesCursorAdapter extends RecyclerView.Adapter<FavoritesCursorAdapter.FavoriteHolder>{
    private Context context;
    private Cursor mCursor;

    public FavoritesCursorAdapter(Context context){
        this.context = context;
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_layout, null);
        return new FavoriteHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry._ID);
        int nameIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME);
        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(nameIndex);

        holder.itemView.setTag(id);
        holder.movieName2.setText(description);

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            Log.d("Test", "Nothing Changed");
            return null; // bc nothing has changed
        }


        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned
        Log.d("Test", "Is cursor valid?");
        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class FavoriteHolder extends RecyclerView.ViewHolder{
        private TextView movieName2;

        public FavoriteHolder(View itemView) {
            super(itemView);
            movieName2 = itemView.findViewById(R.id.movieName2);
        }
    }
}
