package com.speedrun_mobile_unofficial.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.entities.Enums;
import com.speedrun_mobile_unofficial.entities.DataStorageHepler;

import java.util.HashSet;


/**
 * A placeholder fragment containing grid view to
 * display "subscribed games" or "games" pages at
 * homepage.
 */
public class GameFragment extends Fragment {
    private RecyclerView gameGridView;
    private GameGridAdapter gameFragmentAdapter;
//    private int[] imageId;
    private String[] gameNames;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        gameGridView = rootView.findViewById(R.id.game_grid);
        gameGridView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 3));

        gameFragmentAdapter = new GameGridAdapter();
        gameGridView.setAdapter(gameFragmentAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        HashSet<String> set = DataStorageHepler.getStorageStrSet(getActivity(), Enums.STORAGE.SUBSCRIPTION);
        gameFragmentAdapter = new GameGridAdapter(context, R.layout.fragment_game_grid_item, set.toArray());
        gameGridView.setAdapter(gameFragmentAdapter);
    }
}
