package com.example.android.popularmovies.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
public class FavoriteDetailFragment extends Fragment {

    public FavoriteDetailFragment() {
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
        Picasso.with(getContext()).load(photoUrl).fit().centerInside().into(detailImage);
        Picasso.with(getContext()).load(backgroundUrl.replace("w500","w780")).into(backgroundImage);
    }
}
