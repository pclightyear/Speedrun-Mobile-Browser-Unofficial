package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Window;
import android.widget.TextView;

import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.homepage.GameFragment;
import com.speedrun_mobile_unofficial.homepage.GameGridAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {
    private ViewPager leaderboardViewPager;
    private TabLayout leaderboardTabLayout;
    private String game_name;

    private BoardPagerAdapter leaderboardPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_leaderboard);

        Intent intent = getIntent();
        game_name = intent.getStringExtra(GameGridAdapter.GAME_NAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BoardListHelper.fetchGameData(getApplicationContext(), game_name, ((success, result) -> {
            if(success) {
                this.prepareGameModel(result);

                TextView title = findViewById(R.id.text);
                title.setText(CategoryBoardModel.getSharedInstance().getGameName());
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
