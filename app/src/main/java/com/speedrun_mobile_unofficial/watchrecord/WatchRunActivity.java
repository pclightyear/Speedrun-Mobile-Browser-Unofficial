package com.speedrun_mobile_unofficial.watchrecord;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.entities.DataStorageHepler;
import com.speedrun_mobile_unofficial.entities.Enums;
import com.speedrun_mobile_unofficial.leaderboard.CategoryBoardItem;
import com.speedrun_mobile_unofficial.leaderboard.CategoryBoardModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WatchRunActivity extends AppCompatActivity {

    private long resumeTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_run);
    }

    @Override
    protected void onResume() {
        super.onResume();

        resumeTime = System.currentTimeMillis();

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(Enums.EXTRA.CATEGORYNAME);
        String categoryRule = intent.getStringExtra(Enums.EXTRA.CATEGORYRULE);
        CategoryBoardItem item = (CategoryBoardItem) intent.getSerializableExtra(Enums.EXTRA.CATEGORYBOARDITEM);

        System.out.println(item.getRunId());

        WatchRunHelper.fetchRunData(getApplicationContext(), item.getRunId(), (success, result) -> {
            if(success) {
                TextView title = findViewById(R.id.watch_run_bar_text);
                title.setText(CategoryBoardModel.getSharedInstance().getGameName());

                ImageView coverImage = findViewById(R.id.watch_run_info_game_cover_image);
                Glide.with(this).load(CategoryBoardModel.getSharedInstance().getCoverImageSmallUri()).into(coverImage);

                findViewById(R.id.watch_run_progress_bar).setVisibility(ProgressBar.INVISIBLE);
                findViewById(R.id.watch_run_platform_title).setVisibility(TextView.VISIBLE);
                findViewById(R.id.watch_run_release_date_title).setVisibility(TextView.VISIBLE);

                this.setTextView(R.id.watch_run_platforms, CategoryBoardModel.getSharedInstance().getPlatforms());
                this.setTextView(R.id.watch_run_release_date, CategoryBoardModel.getSharedInstance().getReleaseDate());
                this.setTextView(R.id.watch_run_category, categoryName);
                findViewById(R.id.watch_run_view_rule).setVisibility(ImageView.VISIBLE);

                RunModel model = new RunModel(result);
                this.setTextView(R.id.watch_run_platform, model.getPlatform());
                this.setTextView(R.id.watch_run_region, model.getRegion());

                this.setTextView(R.id.watch_run_player, item.getPlayer());
                this.setTextView(R.id.watch_run_time, item.getTime());

                TextView playerText = findViewById(R.id.watch_run_player);
                if(("solid").equals(item.getNameStyle())) {
                    playerText.setTextColor(Color.parseColor(item.getColor()));
                } else if(("gradient").equals(item.getNameStyle())) {
                    int colorFrom = Color.parseColor(item.getColorFrom());
                    int colorTo = Color.parseColor(item.getColorTo());
                    Shader shader = new LinearGradient(0, 0, playerText.getHeight(), playerText.getTextSize(), colorFrom, colorTo, Shader.TileMode.CLAMP);
                    playerText.getPaint().setShader(shader);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        long pauseTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat(Enums.STORAGE.WATCHTIMEFORMAT);
        String today = format.format(new Date());
        long totalForgroundTime = DataStorageHepler.getStorageLong(this, today) + (pauseTime - resumeTime);
        DataStorageHepler.setStorageLong(this, today, totalForgroundTime);
        System.out.println(totalForgroundTime);
    }

    private void setTextView(int id, String text) {
        TextView view = findViewById(id);
        if(text != null) {
            view.setText(text);
            view.setVisibility(TextView.VISIBLE);
        }
    }
}
