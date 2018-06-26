package com.example.android.popularmovies.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;

public class FavoritesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        RecyclerViewFavoriteAdapter.OnItemClickListener,
        RecyclerViewFavoriteAdapter.OnItemLongClickListener{

    private RecyclerViewFavoriteAdapter mAdapter;
    private RecyclerViewFavoriteAdapter.OnItemClickListener itemClickListener;
    private RecyclerViewFavoriteAdapter.OnItemLongClickListener itemLongClickListener;
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        itemClickListener = this;
        itemLongClickListener = this;
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {
        return new AsyncTaskLoader<Cursor>(getContext()) {
            Cursor movieData = null;

            @Override
            protected void onStartLoading() {
                if (movieData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(movieData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                    return cursor;

                } catch (Exception e) {
                    Log.d("Test", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                super.deliverResult(data);
                movieData = data;
            }


        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        TextView errorText = getView().findViewById(R.id.error_message);
        if(data.getCount() == 0){
            errorText.setVisibility(View.VISIBLE);
        }
        else{
            errorText.setVisibility(View.GONE);
        }

        mRecyclerView = getView().findViewById(R.id.favorites_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewFavoriteAdapter(itemClickListener, itemLongClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClicked(int position) {
        int id = (int)mRecyclerView.findViewHolderForAdapterPosition(position).itemView.getTag();

        String stringId = Integer.toString(id);
        String selectionClause = MovieContract.MovieEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {stringId};
        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, selectionClause, selectionArgs, null);
        cursor.moveToFirst();

        int index = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME);
        int overview = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_PLOT);
        int background = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKGROUND);
        int poster = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE);
        int release = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE);
        int rating = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING);

        Intent movieDetail = new Intent(getActivity(), DetailActivity.class);
        movieDetail.putExtra("id", cursor.getString(index));
        movieDetail.putExtra("poster_path",cursor.getString(poster));
        movieDetail.putExtra("backdrop_path", cursor.getString(background));
        movieDetail.putExtra("overview", cursor.getString(overview));
        movieDetail.putExtra("original_title", cursor.getString(nameIndex));
        movieDetail.putExtra("release_date", cursor.getString(release));
        movieDetail.putExtra("vote_average", cursor.getString(rating));
        cursor.close();
        startActivity(movieDetail);


    }

    @Override
    public void onItemLongClicked(final int position) {
        String[] list = {"Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = (int)mRecyclerView.findViewHolderForAdapterPosition(position).itemView.getTag();
                String stringId = Integer.toString(id);
                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                getActivity().getContentResolver().delete(uri, null, new String[]{stringId});
                getActivity().getSupportLoaderManager().restartLoader(0, null, FavoritesFragment.this);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
