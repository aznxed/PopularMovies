package com.example.android.popularmovies;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.android.popularmovies.fragments.FavoritesFragment;
import com.example.android.popularmovies.fragments.MoviesFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Boolean onTabOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onTabOne = false;
        toolbar = findViewById(R.id.toolbar);
        ViewPager mViewPager = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tabs);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // Set up the ViewPager with the sections adapter.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Listeners for current tab and tab changes
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 1){
                    toolbar.getMenu().clear();
                    onTabOne = true;
                }
                else {
                    onTabOne = false;
                }
            }
            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    toolbar.getMenu().clear();
                    onTabOne = true;
                }
                else {
                    onTabOne = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!onTabOne){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position){
                case 0:
                    return new MoviesFragment();
                case 1:
                    return new FavoritesFragment();
                default:
                    return new MoviesFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

}
