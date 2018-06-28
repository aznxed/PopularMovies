package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.fragments.DetailFragment;
import com.example.android.popularmovies.fragments.ReviewFragment;
import com.example.android.popularmovies.fragments.TrailersFragment;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;
    private ReviewFragment reviewFragment;
    private TrailersFragment trailersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ViewPager mViewPager = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tabs);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        detailFragment = new DetailFragment();
        reviewFragment = new ReviewFragment();
        trailersFragment = new TrailersFragment();


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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position){
                case 0:
                    return detailFragment;
                case 1:
                    return reviewFragment;
                case 2:
                    return trailersFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

}
