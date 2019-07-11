package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.homepage.GamePlaceholderFragment;

public class LeaderBoardActivity extends AppCompatActivity {
    private ListView mBoardListView;
    private BoardListAdapter mBoardListAdapter;

    private String[] ranking = {"1st", "2nd", "3rd", "4th"};
    private String[] player = {"Stillow", "Rengj", "tanaka1", "Blastbolt"};
    private String[] time = {"1m 17s 729", "1m 19s 820", "1m 19s 822", "1m 19s 983"};
    private String[] date = {"2019-03-19", "2018-12-09", "2019-07-07", "2019-06-08"};
    private int[] trophy = {R.drawable.first, R.drawable.second, R.drawable.third};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_leaderboard);

        Intent intent = getIntent();
        String game = intent.getStringExtra(GamePlaceholderFragment.GAME_NAME);

        mBoardListView = (ListView) findViewById(R.id.boardlist);
        mBoardListAdapter = new BoardListAdapter(LeaderBoardActivity.this, ranking, player, time, date, trophy);
        mBoardListView.setAdapter(mBoardListAdapter);

        TextView tt = (TextView) findViewById(R.id.text);
        tt.setText(game);
    }
}
