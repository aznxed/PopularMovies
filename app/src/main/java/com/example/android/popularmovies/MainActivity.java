package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private ImageView image2;

    private GridLayoutManager movieLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Context context = getApplicationContext();
        image = findViewById(R.id.movieImage);
        //image2 = findViewById(R.id.test2);

        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(image);
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(image2);
        */

        List<movieObject> movieList = getAllItemList();
        movieLayout = new GridLayoutManager(MainActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(movieLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, movieList);
        rView.setAdapter(rcAdapter);
    }

    private List<movieObject> getAllItemList(){
        List<movieObject> movieObjectList = new ArrayList<movieObject>();
        movieObjectList.add(new movieObject("United States", R.drawable.one));
        movieObjectList.add(new movieObject("Canada", R.drawable.two));
        movieObjectList.add(new movieObject("United Kingdom", R.drawable.three));
        movieObjectList.add(new movieObject("Germany", R.drawable.four));

        return movieObjectList;
    }


}
