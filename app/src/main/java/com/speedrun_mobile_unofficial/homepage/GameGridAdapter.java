package com.speedrun_mobile_unofficial.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.leaderboard.LeaderBoardActivity;

public class GameGridAdapter extends RecyclerView.Adapter<GameGridAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private final int[] imageId;
    private final String[] gameNames;
    private int layoutId;

    public static final String GAME_NAME = "GAME_NAME";

    public GameGridAdapter(Context context, int layoutId, int[] imageId, String[] gameNames) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.imageId = imageId;
        this.layoutId = layoutId;
        this.gameNames = gameNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return gameNames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView gameCover;
        String gameName;

        ViewHolder(View itemView) {
            super(itemView);
            this.gameCover = itemView.findViewById(R.id.home_game_cover_image);
            itemView.setOnClickListener(this);
        }

        void bind(final int position) {
            this.gameName = gameNames[position];
            gameCover.setImageResource(imageId[position]);
        }

        @Override
        public void onClick(View itemView) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, LeaderBoardActivity.class);
            intent.putExtra(GAME_NAME, gameName);
            context.startActivity(intent);
        }
    }
}

