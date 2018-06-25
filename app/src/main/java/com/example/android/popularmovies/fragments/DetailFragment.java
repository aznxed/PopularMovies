package com.example.android.popularmovies.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView detailTitle = getView().findViewById(R.id.detail_title);
        ImageView detailImage = getView().findViewById(R.id.detail_image);
        ImageView backgroundImage = getView().findViewById(R.id.background2);
        TextView overviewText = getView().findViewById(R.id.overview);
        TextView ratingText = getView().findViewById(R.id.rating);
        TextView releaseDateText = getView().findViewById(R.id.release_date);

        final Bundle bundle = this.getArguments();
        String title = bundle.getString("original_title");
        String photoUrl = bundle.getString("poster_path");
        String backgroundUrl = bundle.getString("backdrop_path");
        String overview = bundle.getString("overview");
        String rating = bundle.getString("vote_average");
        String releaseDate = bundle.getString("release_date");
        detailTitle.setText(title);

        releaseDateText.setText(releaseDate);
        ratingText.setText(rating.concat("/10"));
        overviewText.setText(overview);
        Picasso.with(getContext()).load(photoUrl).error(R.drawable.ic_broken_image_black_24dp).fit().centerInside().into(detailImage);
        Picasso.with(getContext()).load(backgroundUrl.replace("w500","w780")).error(R.drawable.ic_broken_image_black_24dp).into(backgroundImage);

        final FloatingActionButton favButton = getView().findViewById(R.id.fav_button);
        final FloatingActionButton unfavButton = getView().findViewById(R.id.unfav_button);
        unfavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = bundle.getString("id");
                int deleted = getActivity().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, new String[]{id});
                if(deleted != 0){
                    Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    unfavButton.setVisibility(View.INVISIBLE);
                    favButton.setVisibility(View.VISIBLE);
                }

            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ContentValues contentValues = new ContentValues();
                // Put the task description and selected mPriority into the ContentValues
                contentValues.put(MovieContract.MovieEntry.COLUMN_ID, bundle.getString("id"));
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME, bundle.getString("original_title"));
                contentValues.put(MovieContract.MovieEntry.COLUMN_BACKGROUND, bundle.getString("backdrop_path"));
                contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, bundle.getString("poster_path"));
                contentValues.put(MovieContract.MovieEntry.COLUMN_PLOT, bundle.getString("overview"));
                contentValues.put(MovieContract.MovieEntry.COLUMN_RATING, bundle.getString("vote_average"));
                contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE, bundle.getString("release_date"));

                // Insert the content values via a ContentResolver
                Uri uri = getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                if(uri != null) {
                    Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                    favButton.setVisibility(View.INVISIBLE);
                    unfavButton.setVisibility(View.VISIBLE);
                }

            }
        });

        //Check if the movie is already in the favorites
        String selectionClause = MovieContract.MovieEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = {bundle.getString("id")};
        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, selectionClause, selectionArgs, null);

        if(cursor.getCount() != 0){
            favButton.setVisibility(View.GONE);
            unfavButton.setVisibility(View.VISIBLE);

        }
    }
}
