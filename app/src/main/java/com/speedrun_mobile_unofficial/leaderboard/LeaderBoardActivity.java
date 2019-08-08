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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.entities.Enums;
import com.speedrun_mobile_unofficial.entities.DataStorageHepler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {
    private ViewPager leaderboardViewPager;
    private TabLayout leaderboardTabLayout;
    private TextView title;
    private String gameName;
    private boolean first_load = true;

    private BoardPagerAdapter leaderboardPagerAdapter;
    private ArrayList<CategoryBoard> allCategoryBoard = new ArrayList<>();
    private GameInfoModel gameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_leaderboard);

        Intent intent = getIntent();
        gameName = intent.getStringExtra(Enums.EXTRA.GAMENAME);

        Toolbar mToolbar = findViewById(R.id.leaderboard_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressBar indicatorUp = findViewById(R.id.leaderboard_progress_bar_up);
        ProgressBar indicatorDown = findViewById(R.id.leaderboard_progress_bar_down);

        if(!first_load) {
            indicatorUp.setVisibility(ProgressBar.INVISIBLE);
        } else {
            first_load = false;
            LeaderBoardHelper.fetchGameData(getApplicationContext(), gameName, ((success, result) -> {
                if(success) {
                    this.prepareGameModel(result);

                    title = findViewById(R.id.leaderboard_bar_text);
                    title.setText(gameInfo.getGameName());

                    ImageView coverImage = findViewById(R.id.leaderboard_info_game_cover_image);
                    Glide.with(this).load(gameInfo.getCoverImageSmallUri()).into(coverImage);

                    TextView platforms = findViewById(R.id.leaderboard_platforms);
                    platforms.setText(gameInfo.getPlatforms());

                    TextView releaseDate = findViewById(R.id.leaderboard_release_date);
                    releaseDate.setText(gameInfo.getReleaseDate());

                    indicatorDown.setVisibility(ProgressBar.VISIBLE);
                    indicatorUp.setVisibility(ProgressBar.INVISIBLE);
                    findViewById(R.id.leaderboard_platform_title).setVisibility(TextView.VISIBLE);
                    findViewById(R.id.leaderboard_release_date_title).setVisibility(TextView.VISIBLE);
                }
            }));

            LeaderBoardHelper.fetchLeaderBoardData(getApplicationContext(), gameName, (success, result) -> {
                if(success) {
                    this.prepareBoardModel(result);

                    leaderboardViewPager = findViewById(R.id.boardPager);
                    leaderboardPagerAdapter = new BoardPagerAdapter(getSupportFragmentManager(), allCategoryBoard, gameInfo);
                    leaderboardViewPager.setAdapter(leaderboardPagerAdapter);

                    leaderboardTabLayout = findViewById(R.id.boardTabs);
                    leaderboardTabLayout.setupWithViewPager(leaderboardViewPager);

                    indicatorDown.setVisibility(ProgressBar.INVISIBLE);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_leaderboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        HashSet<String> set = DataStorageHepler.getStorageStrSet(this, Enums.STORAGE.SUBSCRIPTION);
        MenuItem subscribe = menu.findItem(R.id.action_subscribe);
        if(set.contains(gameName)) {
           subscribe.setChecked(true);
           subscribe.setIcon(R.drawable.star_26);
        }
        subscribe.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
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
                if(title != null) {
                    if(!item.isChecked()) {
                        item.setChecked(true);
                        item.setIcon(R.drawable.star_26);
                        DataStorageHepler.addStrToStorageStrSet(this, Enums.STORAGE.SUBSCRIPTION, gameName);
                        Toast.makeText(this, String.format("Subscribe to %s", title.getText().toString()), Toast.LENGTH_LONG).show();
                    } else {
                        item.setChecked(false);
                        item.setIcon(R.drawable.hollow_star_26);
                        DataStorageHepler.removeStrFromStorageStrSet(this, Enums.STORAGE.SUBSCRIPTION, gameName);
                        Toast.makeText(this, String.format("Unsubscribe to %s", title.getText().toString()), Toast.LENGTH_LONG).show();
                    }
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void prepareGameModel(Map result) {
        gameInfo = new GameInfoModel(result);
    }

    private void prepareBoardModel(Map result) {
        List results = (ArrayList) result.get("allCategoryBoard");

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
        }
    }
}
