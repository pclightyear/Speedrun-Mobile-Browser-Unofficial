package com.speedrun_mobile_unofficial.homepage;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.speedrun_mobile_unofficial.R;

public class HomePageActivity extends AppCompatActivity {

    private ViewPager homeViewPager;
    private TabLayout homeTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeViewPager = (ViewPager) findViewById(R.id.homePager);
        this.setupViewPager(homeViewPager);

        homeTabLayout = (TabLayout) findViewById(R.id.homeTabs);
        homeTabLayout.setupWithViewPager(homeViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GameFragment(), getString(R.string.SUBSCRIBED_GAMES));
        adapter.addFragment(new WatchTimeFragment(), getString(R.string.WATCH_TIME));

        viewPager.setAdapter(adapter);
    }

    public TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}
