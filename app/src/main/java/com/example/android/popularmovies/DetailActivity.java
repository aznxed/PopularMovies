package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.android.popularmovies.fragments.DetailFragment;
import com.example.android.popularmovies.fragments.ReviewFragment;
import com.example.android.popularmovies.fragments.TrailersFragment;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;
    private ReviewFragment reviewFragment;
    private TrailersFragment trailersFragment;
    private BottomNavigationView navigation;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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


        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Retain tab on rotation
        if(savedInstanceState != null){
            View view = navigation.findViewById(savedInstanceState.getInt("tab"));
            view.performClick();
        }
        else{
            setFragment(detailFragment);
        }

        //Get extras from Main Activity
        Intent intentThatCalledActivity = getIntent();
        String id = intentThatCalledActivity.getStringExtra("id");
        String title = intentThatCalledActivity.getStringExtra("original_title");
        String photoUrl = intentThatCalledActivity.getStringExtra("poster_path");
        String releaseDate = intentThatCalledActivity.getStringExtra("release_date");
        String rating = intentThatCalledActivity.getStringExtra("vote_average");
        String overview = intentThatCalledActivity.getStringExtra("overview");
        String backgroundUrl = intentThatCalledActivity.getStringExtra("backdrop_path");

        setTitle(title);

        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("original_title", title);
        bundle.putString("poster_path", photoUrl);
        bundle.putString("release_date", releaseDate);
        bundle.putString("vote_average", rating);
        bundle.putString("overview", overview);
        bundle.putString("backdrop_path", backgroundUrl);


        reviewFragment.setArguments(bundle);
        trailersFragment.setArguments(bundle);
        detailFragment.setArguments(bundle);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int selected = navigation.getSelectedItemId();
        outState.putInt("tab", selected);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
