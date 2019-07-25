package com.speedrun_mobile_unofficial.homepage;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.entities.Constants;
import com.speedrun_mobile_unofficial.entities.DataStorageHepler;
import com.speedrun_mobile_unofficial.entities.Enums;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePageActivity extends AppCompatActivity {

    private SearchView searchView;
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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.home_search_bar);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

//        onSearchRequested();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(searchView != null) {
            searchView.clearFocus();
        }
        initSetup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homepage, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_export:
                checkExternalStoragePermissionsAndExport();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GameFragment(), getString(R.string.SUBSCRIBED_GAMES));
        adapter.addFragment(new WatchTimeFragment(), getString(R.string.WATCH_TIME));

        viewPager.setAdapter(adapter);
    }

    private void initSetup() {
        String startDate = DataStorageHepler.getStorageStr(this, Enums.STORAGE.STARTDATE);
        if(startDate == null) {
            SimpleDateFormat format = new SimpleDateFormat(Enums.STORAGE.WATCHTIMEFORMAT);
            String today = format.format(new Date());
            DataStorageHepler.setStorageStr(this, Enums.STORAGE.STARTDATE, today);
        }
    }

    private void checkExternalStoragePermissionsAndExport() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            DataStorageHepler.exportDataToJSONFile(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DataStorageHepler.exportDataToJSONFile(this);
                }
        }
    }

}
