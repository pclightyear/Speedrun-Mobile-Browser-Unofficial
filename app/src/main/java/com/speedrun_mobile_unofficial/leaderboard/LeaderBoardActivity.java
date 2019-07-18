package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.homepage.GameGridAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {
    private ViewPager leaderboardViewPager;
    private TabLayout leaderboardTabLayout;
    private TextView title;
    private String game_name;

    private BoardPagerAdapter leaderboardPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_leaderboard);

        Intent intent = getIntent();
        game_name = intent.getStringExtra(GameGridAdapter.GAME_NAME);

        Toolbar mToolbar = findViewById(R.id.leaderboard_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BoardListHelper.fetchGameData(getApplicationContext(), game_name, ((success, result) -> {
            if(success) {
                this.prepareGameModel(result);

                title = findViewById(R.id.text);
                title.setText(CategoryBoardModel.getSharedInstance().getGameName());

                ImageView coverImage = findViewById(R.id.info_game_cover_image);
                Glide.with(this).load(CategoryBoardModel.getSharedInstance().getCoverImageUri()).into(coverImage);

                TextView platforms = findViewById(R.id.platforms);
                platforms.setText(CategoryBoardModel.getSharedInstance().getPlatforms());

                TextView releaseDate = findViewById(R.id.release_date);
                releaseDate.setText(CategoryBoardModel.getSharedInstance().getReleaseDate());
            }
        }));

        BoardListHelper.fetchLeaderBoardData(getApplicationContext(), game_name, (success, result) -> {
            if(success) {
                this.prepareBoardModel(result);

                leaderboardViewPager = findViewById(R.id.boardPager);
                leaderboardPagerAdapter = new BoardPagerAdapter(getSupportFragmentManager());
                leaderboardViewPager.setAdapter(leaderboardPagerAdapter);

                leaderboardTabLayout = findViewById(R.id.boardTabs);
                leaderboardTabLayout.setupWithViewPager(leaderboardViewPager);
//                leaderboardTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(leaderboardViewPager) {
//                    @Override
//                    public void onTabSelected(TabLayout.Tab tab) {
//                    }
//
//                    @Override
//                    public void onTabUnselected(TabLayout.Tab tab) {
//
//                    }
//
//                    @Override
//                    public void onTabReselected(TabLayout.Tab tab) {
//
//                    }
//                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_leaderboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_subscribe:
                if(!item.isChecked()) {
                    item.setChecked(true);
                    item.setIcon(R.drawable.star_26);
                    Toast.makeText(this, String.format("Subscribe to %s", title.getText().toString()), Toast.LENGTH_LONG).show();
                } else {
                    item.setChecked(false);
                    item.setIcon(R.drawable.hollow_star_26);
                    Toast.makeText(this, String.format("Unsubscribe to %s", title.getText().toString()), Toast.LENGTH_LONG).show();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void prepareGameModel(Map result) {
        CategoryBoardModel model = new CategoryBoardModel(result);
        if(CategoryBoardModel.getSharedInstance() != null) {
            model.setAllCategoryBoard(CategoryBoardModel.getSharedInstance().getAllCategoryBoard());
        }
        CategoryBoardModel.setSharedInstance(model);
    }

    private void prepareBoardModel(Map result) {
        CategoryBoardModel model;
        if(CategoryBoardModel.getSharedInstance() == null) {
            model = new CategoryBoardModel(result);
        } else {
            model = CategoryBoardModel.getSharedInstance();
        }
        List results = (ArrayList) result.get("allCategoryBoard");
        ArrayList<CategoryBoard> allCategoryBoard = new ArrayList<>();

        if ((results != null ? results.size() : 0) > 0) {
            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> map = (Map) results.get(i);

                CategoryBoard categoryBoard = new CategoryBoard(map);
                List leaderboardresult = (ArrayList) map.get("leaderboard");
                ArrayList<CategoryBoardItem> leaderboard = new ArrayList<>();

                for(int j = 0; j < leaderboardresult.size(); ++j) {
                    CategoryBoardItem categoryBoardItem = new CategoryBoardItem((Map) leaderboardresult.get(j));
                    leaderboard.add(categoryBoardItem);
                }
                categoryBoard.setLeaderboard(leaderboard);

                allCategoryBoard.add(categoryBoard);
            }

            model.setAllCategoryBoard(allCategoryBoard);
        }

        CategoryBoardModel.setSharedInstance(model);
    }
}
