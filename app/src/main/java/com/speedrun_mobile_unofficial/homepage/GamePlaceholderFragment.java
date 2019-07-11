package com.speedrun_mobile_unofficial.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.leaderboard.LeaderBoardActivity;


/**
 * A placeholder fragment containing grid view to
 * display "subscribed games" or "games" pages at
 * homepage.
 */
public class GamePlaceholderFragment extends Fragment {
    private GridView gameGridView;
    private GamePlaceholderFragmentAdapter gamePlaceholderFragmentAdapter;
    private Context context;
    private int[] imageId;
    private String[] gameNames;

    public static final String GAME_NAME = "GAME_NAME";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Resources resource = context.getResources();
        this.context = context;
        this.gameNames = resource.getStringArray(R.array.game_name);

        TypedArray typedArray = resource.obtainTypedArray(R.array.images);
        int imageCount = typedArray.length();
        imageId = new int[imageCount];
        for(int i = 0; i < imageCount; i++) {
            imageId[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        gamePlaceholderFragmentAdapter = new GamePlaceholderFragmentAdapter(context, imageId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        gameGridView = rootView.findViewById(R.id.game_grid);
        gameGridView.setAdapter(gamePlaceholderFragmentAdapter);
        gameGridView.setOnItemClickListener(onItemClickListener);
        return rootView;
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(context, LeaderBoardActivity.class);
            intent.putExtra(GAME_NAME, gameNames[position]);
            startActivity(intent);
        }
    };
}
