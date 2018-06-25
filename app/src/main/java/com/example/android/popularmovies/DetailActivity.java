package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.popularmovies.fragments.DetailFragment;
import com.example.android.popularmovies.fragments.ReviewFragment;
import com.example.android.popularmovies.fragments.TrailersFragment;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;
    private ReviewFragment reviewFragment;
    private TrailersFragment trailersFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_details:
                    setFragment(detailFragment);
                    return true;
                case R.id.navigation_reviews:
                    setFragment(reviewFragment);
                    return true;
                case R.id.navigation_trailers:
                    setFragment(trailersFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailFragment = new DetailFragment();
        reviewFragment = new ReviewFragment();
        trailersFragment = new TrailersFragment();

        setFragment(detailFragment);

        Intent intentThatCalledActivity = getIntent();
        String id = intentThatCalledActivity.getStringExtra("id");
        String title = intentThatCalledActivity.getStringExtra("original_title");
        setTitle(title);

        String photoUrl = intentThatCalledActivity.getStringExtra("poster_path");
        String releaseDate = intentThatCalledActivity.getStringExtra("release_date");
        String rating = intentThatCalledActivity.getStringExtra("vote_average");
        String overview = intentThatCalledActivity.getStringExtra("overview");
        String backgroundUrl = intentThatCalledActivity.getStringExtra("backdrop_path");

        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        reviewFragment.setArguments(bundle);
        trailersFragment.setArguments(bundle);
        bundle.putString("original_title", title);
        bundle.putString("poster_path", photoUrl);
        bundle.putString("release_date", releaseDate);
        bundle.putString("vote_average", rating);
        bundle.putString("overview", overview);
        bundle.putString("backdrop_path", backgroundUrl);
        detailFragment.setArguments(bundle);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
