package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;

import static com.example.android.popularmovies.NetworkUtils.parseJSON;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemClickListener{

    private Toolbar topToolBar;
    private GridLayoutManager movieLayout;
    private RecyclerView rView;
    private RecyclerViewAdapter rcAdapter;
    private RecyclerViewAdapter.ListItemClickListener listener;
    private List<movieObject> movieList;
    private ProgressBar mLoadingIndicator;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //relativeLayout = (RelativeLayout)findViewById(R.id.rLay);
        //relativeLayout.setVisibility(View.INVISIBLE);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        movieLayout = new GridLayoutManager(MainActivity.this, 2);
        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(movieLayout);

        listener = this;
        movieList = null;

        new MovieQueryTask().execute(NetworkUtils.buildUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                movieList = parseJSON(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            //relativeLayout.setVisibility(View.VISIBLE);
            rcAdapter = new RecyclerViewAdapter(MainActivity.this, movieList, listener);
            rView.setAdapter(rcAdapter);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        movieObject movieClicked = movieList.get(clickedItemIndex);
        Intent movieDetail = new Intent(MainActivity.this, DetailActivity.class);

        movieDetail.putExtra("poster_path",movieClicked.getPhoto());
        movieDetail.putExtra("overview", movieClicked.getDescription());
        movieDetail.putExtra("original_title", movieClicked.getName());
        movieDetail.putExtra("release_date", movieClicked.getReleaseDate());
        movieDetail.putExtra("vote_average", movieClicked.getRating());
        startActivity(movieDetail);
    }

}