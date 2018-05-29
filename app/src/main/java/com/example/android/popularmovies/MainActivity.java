package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;

import static com.example.android.popularmovies.NetworkUtils.parseJSON;

public class MainActivity extends AppCompatActivity
        implements RecyclerViewAdapter.ListItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener{

    private GridLayoutManager movieLayout;
    private RecyclerView rView;
    private RecyclerViewAdapter rcAdapter;
    private RecyclerViewAdapter.ListItemClickListener listener;
    private List<movieObject> movieList;
    private ProgressBar mLoadingIndicator;
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
    private final int SORT_BY_RATING = 1;
    private final int SORT_BY_POPULAR = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        movieLayout = new GridLayoutManager(MainActivity.this, 2);
        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(movieLayout);

        listener = this;
        movieList = null;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        String sort = prefs.getString("sortBy","");

        if(sort.equals("top_rate")){
            new MovieQueryTask().execute(NetworkUtils.buildUrl(SORT_BY_RATING));
        }
        else{
            new MovieQueryTask().execute(NetworkUtils.buildUrl(SORT_BY_POPULAR));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {

            PREFERENCES_HAVE_BEEN_UPDATED = false;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String sort = prefs.getString("sortBy","");

            if(sort.equals("top_rate")){
                new MovieQueryTask().execute(NetworkUtils.buildUrl(SORT_BY_RATING));
            }
            else{
                new MovieQueryTask().execute(NetworkUtils.buildUrl(SORT_BY_POPULAR));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();

        if(itemSelected == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCES_HAVE_BEEN_UPDATED = true;
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
            rcAdapter = new RecyclerViewAdapter(MainActivity.this, movieList, listener);
            rView.setAdapter(rcAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
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