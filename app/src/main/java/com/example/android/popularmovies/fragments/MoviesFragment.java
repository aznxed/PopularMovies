package com.example.android.popularmovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.movieObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;

import static com.example.android.popularmovies.utils.NetworkUtils.parseMovieJSON;

public class MoviesFragment extends Fragment
        implements RecyclerViewMovieAdapter.ListItemClickListener {

    private RecyclerViewMovieAdapter.ListItemClickListener listClickListener;
    private List<movieObject> movieList;
    private final int SORT_BY_RATING = 1;
    private final int SORT_BY_POPULAR = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_movies, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        listClickListener = this;
        movieList = null;

        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            new MovieQueryTask().execute(NetworkUtils.buildMovieUrl(SORT_BY_POPULAR));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            return false;
        }

        int itemSelected = item.getItemId();

        if(itemSelected == R.id.sort_popularity){
            new MovieQueryTask().execute(NetworkUtils.buildMovieUrl(SORT_BY_POPULAR));
            Toast.makeText(getContext(), "Sort By Popular", Toast.LENGTH_SHORT).show();
        }
        else if(itemSelected == R.id.sort_rating){
            new MovieQueryTask().execute(NetworkUtils.buildMovieUrl(SORT_BY_RATING));
            Toast.makeText(getContext(), "Sort By Rating", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String movieJSON = null;
            try {
                movieJSON = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieJSON;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                movieList = parseMovieJSON(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RecyclerViewMovieAdapter rcAdapter = new RecyclerViewMovieAdapter(getContext(), movieList, listClickListener);
            int res = getResources().getConfiguration().orientation;
            GridLayoutManager movieLayout;
            if(res == Configuration.ORIENTATION_LANDSCAPE){
                movieLayout = new GridLayoutManager(getContext(), 3);
            }
            else{
                movieLayout = new GridLayoutManager(getContext(), 2);
            }

            RecyclerView rView = getView().findViewById(R.id.recycler_view);
            rView.setAdapter(rcAdapter);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(movieLayout);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        movieObject movieClicked = movieList.get(clickedItemIndex);
        Intent movieDetail = new Intent(getActivity(), DetailActivity.class);
        movieDetail.putExtra("id", Integer.toString(movieClicked.getId()));
        movieDetail.putExtra("poster_path",movieClicked.getPhoto());
        movieDetail.putExtra("backdrop_path", movieClicked.getBackground());
        movieDetail.putExtra("overview", movieClicked.getDescription());
        movieDetail.putExtra("original_title", movieClicked.getName());
        movieDetail.putExtra("release_date", movieClicked.getReleaseDate());
        movieDetail.putExtra("vote_average", movieClicked.getRating());
        startActivity(movieDetail);
    }


}