package com.speedrun_mobile_unofficial.leaderboard;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedrun_mobile_unofficial.R;

import java.util.ArrayList;

public class BoardListAdapter extends BaseAdapter {

    private Context context;
    private CategoryBoard categoryBoard;

    public BoardListAdapter(Context context, CategoryBoard categoryBoard) {
        this.context = context;
        this.categoryBoard = categoryBoard;
    }

    @Override
    public int getCount() {
        if(categoryBoard != null) {
            return categoryBoard.getLeaderboard().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_leaderboard_list_item, parent, false);
            holder = new ViewHolder();
            holder.rankingText = convertView.findViewById(R.id.ranking);
            holder.playerText = convertView.findViewById(R.id.player);
            holder.timeText = convertView.findViewById(R.id.time);
            holder.dateText = convertView.findViewById(R.id.date);
            holder.trophyImage = convertView.findViewById(R.id.trophy);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArrayList<CategoryBoardItem> leaderboard = categoryBoard.getLeaderboard();

        holder.rankingText.setText(leaderboard.get(position).getRanking());
        holder.playerText.setText(leaderboard.get(position).getPlayer());
        holder.timeText.setText(leaderboard.get(position).getTime());
        holder.dateText.setText(leaderboard.get(position).getDate());
//        if(position < trophy.length) {
//            holder.trophyImage.setImageResource(trophy[position]);
//        } else {
//            holder.trophyImage.setVisibility(View.INVISIBLE);
//        }

        if(position > 2) {
            holder.trophyImage.setVisibility(View.INVISIBLE);
        }

        convertView.bringToFront();

        return convertView;
    }

    public CategoryBoard getCategoryBoard() {
        return categoryBoard;
    }

    public void setCategoryBoard(CategoryBoard categoryBoard) {
        this.categoryBoard = categoryBoard;
    }

    private static class ViewHolder {
        ImageView trophyImage;
        TextView rankingText;
        TextView playerText;
        TextView timeText;
        TextView dateText;
    }
}
