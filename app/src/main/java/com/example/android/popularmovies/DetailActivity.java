package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView detailTitle;
    private ImageView detailImage;
    private TextView releaseDateText;
    private TextView ratingText;
    private TextView overviewText;
    private ImageView backgroundImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = (TextView)findViewById(R.id.detail_title);
        detailImage = (ImageView)findViewById(R.id.detail_image);
        releaseDateText = (TextView)findViewById(R.id.release_date);
        ratingText = (TextView)findViewById(R.id.rating);
        overviewText = (TextView)findViewById(R.id.overview);

        Intent intentThatCalledActivity = getIntent();
        String title = intentThatCalledActivity.getStringExtra("original_title");
        setTitle(title);
        detailTitle.setText(title);

        String photoUrl = intentThatCalledActivity.getStringExtra("poster_path");
        Picasso.with(getBaseContext()).load(photoUrl).fit().centerInside().into(detailImage);

        String releaseDate = intentThatCalledActivity.getStringExtra("release_date");
        releaseDateText.setText(releaseDate);

        String rating = intentThatCalledActivity.getStringExtra("vote_average");
        ratingText.setText(rating.concat("/10"));

        String overview = intentThatCalledActivity.getStringExtra("overview");
        overviewText.setText(overview);

        backgroundImage = (ImageView)findViewById(R.id.background2);
        String backgroundUrl = intentThatCalledActivity.getStringExtra("backdrop_path");

        Picasso.with(getBaseContext()).load(backgroundUrl.replace("w500","w780")).into(backgroundImage);

        FloatingActionButton favButton = (FloatingActionButton) findViewById(R.id.fav_button);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                // Create a new intent to start an AddTaskActivity
                //Intent addTaskIntent = new Intent(DetailActivity.this, AddTaskActivity.class);
                //startActivity(addTaskIntent);
            }
        });
    }

}
