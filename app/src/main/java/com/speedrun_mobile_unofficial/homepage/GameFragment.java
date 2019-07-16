package com.speedrun_mobile_unofficial.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class GameFragment extends Fragment {
    private RecyclerView gameGridView;
    private GameGridAdapter gameFragmentAdapter;
    private int[] imageId;
    private String[] gameNames;
    private Context context;

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        gameGridView = rootView.findViewById(R.id.game_grid);
        gameGridView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 3));

        gameFragmentAdapter = new GameGridAdapter(context, R.layout.fragment_game_grid_item, imageId, gameNames);
        gameGridView.setAdapter(gameFragmentAdapter);
        return rootView;
    }
}
