package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.android.popularmovies.NetworkUtils;

import org.json.JSONException;

import static com.example.android.popularmovies.NetworkUtils.parseJSON;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private ImageView image2;

    private GridLayoutManager movieLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        movieLayout = new GridLayoutManager(MainActivity.this, 2);
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
            List<movieObject> movieList = null;

            try {
                movieList = parseJSON(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(movieLayout);

            RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, movieList);
            rView.setAdapter(rcAdapter);

        }
    }

    private List<movieObject> getAllItemList() {
        List<movieObject> movieObjectList = new ArrayList<movieObject>();
        movieObjectList.add(new movieObject("United States", null));
        movieObjectList.add(new movieObject("Canada", null));
        movieObjectList.add(new movieObject("United Kingdom", null));
        movieObjectList.add(new movieObject("Germany", null));
        movieObjectList.add(new movieObject("United States", null));
        movieObjectList.add(new movieObject("Canada", null));
        movieObjectList.add(new movieObject("United Kingdom", null));
        movieObjectList.add(new movieObject("Germany", null));

        return movieObjectList;
    }


}