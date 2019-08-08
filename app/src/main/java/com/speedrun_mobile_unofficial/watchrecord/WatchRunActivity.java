package com.speedrun_mobile_unofficial.watchrecord;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.entities.DataStorageHepler;
import com.speedrun_mobile_unofficial.entities.Enums;
import com.speedrun_mobile_unofficial.leaderboard.CategoryBoardItem;
import com.speedrun_mobile_unofficial.leaderboard.GameInfoModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WatchRunActivity extends AppCompatActivity {

    private long resumeTime = 0;
    private CategoryBoardItem run;
    private GameInfoModel gameInfo;
    private String weblink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_run);

        Toolbar mToolbar = findViewById(R.id.watch_run_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        resumeTime = System.currentTimeMillis();

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(Enums.EXTRA.CATEGORYNAME);
        String categoryRule = intent.getStringExtra(Enums.EXTRA.CATEGORYRULE);
        run = (CategoryBoardItem) intent.getSerializableExtra(Enums.EXTRA.CATEGORYBOARDITEM);
        gameInfo = (GameInfoModel) intent.getSerializableExtra(Enums.EXTRA.GAMEINFO);

        WatchRunHelper.fetchRunData(getApplicationContext(), run.getRunId(), (success, result) -> {
            if(success) {
                TextView title = findViewById(R.id.watch_run_bar_text);
                title.setText(gameInfo.getGameName());

                ImageView coverImage = findViewById(R.id.watch_run_info_game_cover_image);
                Glide.with(this).load(gameInfo.getCoverImageSmallUri()).into(coverImage);

                findViewById(R.id.watch_run_progress_bar).setVisibility(ProgressBar.INVISIBLE);
                findViewById(R.id.watch_run_platform_title).setVisibility(TextView.VISIBLE);
                findViewById(R.id.watch_run_release_date_title).setVisibility(TextView.VISIBLE);

                this.setTextView(R.id.watch_run_platforms, gameInfo.getPlatforms());
                this.setTextView(R.id.watch_run_release_date, gameInfo.getReleaseDate());
                this.setTextView(R.id.watch_run_category, categoryName);

                Button viewrule = findViewById(R.id.watch_run_view_rule);
                viewrule.setVisibility(ImageView.VISIBLE);
                viewrule.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("rules", categoryRule);
                    RulesDialogFragment ruleDialog = new RulesDialogFragment();
                    ruleDialog.setArguments(bundle);
                    ruleDialog.show(getSupportFragmentManager(), "ruleDialog");
                });

                RunModel model = new RunModel(result);
                this.setTextView(R.id.watch_run_platform, model.getPlatform());
                this.setTextView(R.id.watch_run_region, model.getRegion());

                this.setTextView(R.id.watch_run_player, run.getPlayer());
                this.setTextView(R.id.watch_run_time, run.getTime());

                TextView playerText = findViewById(R.id.watch_run_player);
                if(("solid").equals(run.getNameStyle())) {
                    playerText.setTextColor(Color.parseColor(run.getColor()));
                } else if(("gradient").equals(run.getNameStyle())) {
                    int colorFrom = Color.parseColor(run.getColorFrom());
                    int colorTo = Color.parseColor(run.getColorTo());
                    Shader shader = new LinearGradient(0, 0, playerText.getHeight(), playerText.getTextSize(), colorFrom, colorTo, Shader.TileMode.CLAMP);
                    playerText.getPaint().setShader(shader);
                }

                Button openInBrowser = findViewById(R.id.watch_run_in_browser_btn);
                openInBrowser.setOnClickListener(v -> {
                    Intent browserIntent = new Intent();
                    browserIntent.setAction(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(weblink));
                    startActivity(browserIntent);
                });

                weblink = model.getWeblink();
                PlayVideoHelper.playVideo(this, weblink);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_watch_run, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        System.out.println("bacck");
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, String.format("Speedrun by %s in %s, %s\n%s", run.getPlayer(), gameInfo.getGameName(), run.getTime(), weblink));
                intent.setType("text/plain");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        long pauseTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat(Enums.STORAGE.WATCHTIMEFORMAT);
        String today = format.format(new Date());
        long totalForegroundTime = DataStorageHepler.getStorageLong(this, today) + (pauseTime - resumeTime);
        DataStorageHepler.setStorageLong(this, today, totalForegroundTime);
        System.out.println(today);
        System.out.println(totalForegroundTime);
    }

    private void setTextView(int id, String text) {
        TextView view = findViewById(id);
        if(text != null) {
            view.setText(text);
            view.setVisibility(TextView.VISIBLE);
        }
    }
}
