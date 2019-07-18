package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.speedrun_mobile_unofficial.R;

import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private int layoutId;
    private List<CategoryBoardItem> itemList;
    private String firstTrophyUri;
    private String secondTrophyUri;
    private String thirdTrophyUri;
    private String fourthTrophyUri;

    public BoardListAdapter(Context context, int layoutId, List<CategoryBoardItem> items) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.itemList = items;
        this.firstTrophyUri = CategoryBoardModel.getSharedInstance().getFirstTrophyUri();
        this.secondTrophyUri = CategoryBoardModel.getSharedInstance().getSecondTrophyUri();
        this.thirdTrophyUri = CategoryBoardModel.getSharedInstance().getThirdTrophyUri();
        this.fourthTrophyUri = CategoryBoardModel.getSharedInstance().getFourthTrophyUri();
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
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView trophyImage;
        final TextView rankingText;
        final TextView playerText;
        final TextView timeText;
        final TextView dateText;

        ViewHolder(View itemView) {
            super(itemView);
            this.trophyImage = itemView.findViewById(R.id.trophy);
            this.rankingText = itemView.findViewById(R.id.ranking);
            this.playerText = itemView.findViewById(R.id.player);
            this.timeText = itemView.findViewById(R.id.time);
            this.dateText = itemView.findViewById(R.id.date);
        }

        void bind(final int position) {
            CategoryBoardItem item = itemList.get(position);

            rankingText.setText(item.getRanking());
            playerText.setText(item.getPlayer());

            if(("solid").equals(item.getNameStyle())) {
                playerText.setTextColor(Color.parseColor(item.getColor()));
            } else if(("gradient").equals(item.getNameStyle())) {
                int colorFrom = Color.parseColor(item.getColorFrom());
                int colorTo = Color.parseColor(item.getColorTo());
                Shader shader = new LinearGradient(0, 0, playerText.getHeight(), playerText.getTextSize(), colorFrom, colorTo, Shader.TileMode.CLAMP);
                playerText.getPaint().setShader(shader);
            }

            timeText.setText(item.getTime());
            dateText.setText(item.getDate());

            trophyImage.setVisibility(View.INVISIBLE);

            if (position <= 2) {
                trophyImage.setVisibility(View.VISIBLE);
                if (position == 0 && firstTrophyUri != null) {
                    Glide.with(context).load(firstTrophyUri).into(trophyImage);
                } else if (position == 1 && secondTrophyUri != null) {
                    Glide.with(context).load(secondTrophyUri).into(trophyImage);
                } else if (position == 2 && thirdTrophyUri != null) {
                    Glide.with(context).load(thirdTrophyUri).into(trophyImage);
                }
            }
            if (fourthTrophyUri != null){
                trophyImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(firstTrophyUri).into(trophyImage);
            }
        }
    }
}
