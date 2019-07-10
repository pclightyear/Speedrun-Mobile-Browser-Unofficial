package com.speedrun_mobile_unofficial;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A placeholder fragment containing grid view to
 * display "subscribed games" or "games" pages at
 * homepage.
 */
public class GamePlaceholderFragment extends Fragment {
    private GridView gameGridView;
    private GamePlaceholderFragmentAdapter gamePlaceholderFragmentAdapter;

    public GamePlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        gameGridView = rootView.findViewById(R.id.game_grid);
        gamePlaceholderFragmentAdapter = new GamePlaceholderFragmentAdapter(this.getContext());
        gameGridView.setAdapter(gamePlaceholderFragmentAdapter);
        gameGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(GamePlaceholderFragment.this.getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });
//        textView.setText(R.string.SUBSCRIBED_GAMES_NOT_FINISHED);
        return rootView;
    }


}
