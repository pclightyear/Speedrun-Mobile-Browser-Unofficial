package com.speedrun_mobile_unofficial.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.leaderboard.LeaderBoardActivity;

public class GameGridAdapter extends RecyclerView.Adapter<GameGridAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private Object[] gameNames;
    private int layoutId;

    public static final String GAME_NAME = "GAME_NAME";

    public GameGridAdapter() { }

    public GameGridAdapter(Context context, int layoutId, Object[] gameNames) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
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
        return gameNames == null ? 0 : gameNames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView gameCover;
        String gameName;
        CircularProgressDrawable placeholder;

        ViewHolder(View itemView) {
            super(itemView);
            this.gameCover = itemView.findViewById(R.id.home_game_cover_image);
            placeholder = new CircularProgressDrawable(context);
            placeholder.setStrokeWidth(5f);
            placeholder.setCenterRadius(30f);
            itemView.setOnClickListener(this);
        }

        void bind(final int position) {
            this.gameName = (String) gameNames[position];
            String url = String.format("https://www.speedrun.com/themes/%s/cover-256.png", gameName);
            System.out.println(url);

            placeholder.start();
            RequestOptions sharedOptions = new RequestOptions().placeholder(placeholder).fitCenter();
            Glide.with(context).load(url).apply(sharedOptions).into(gameCover);
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

