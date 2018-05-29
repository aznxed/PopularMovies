package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView detailTitle;
    private ImageView detailImage;
    private TextView releaseDateText;
    private TextView ratingText;
    private TextView overviewText;

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
        detailTitle.setText(title);

        String photoUrl = intentThatCalledActivity.getStringExtra("poster_path");
        Picasso.with(getBaseContext()).load(photoUrl).fit().centerInside().into(detailImage);

        String releaseDate = intentThatCalledActivity.getStringExtra("release_date");
        releaseDateText.setText(releaseDate);

        String rating = intentThatCalledActivity.getStringExtra("vote_average");
        ratingText.setText(rating.concat("/10"));

        String overview = intentThatCalledActivity.getStringExtra("overview");
        overviewText.setText(overview);

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
}
