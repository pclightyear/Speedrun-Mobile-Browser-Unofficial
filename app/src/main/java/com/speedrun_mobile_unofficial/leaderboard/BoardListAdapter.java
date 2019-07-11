package com.speedrun_mobile_unofficial.leaderboard;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedrun_mobile_unofficial.R;

public class BoardListAdapter extends BaseAdapter {

    private Context context;
    private String[] ranking;
    private String[] player;
    private String[] time;
    private String[] date;
    private int[] trophy;

    public BoardListAdapter(Context context, String[] ranking, String[] player, String[]time, String[] date, int[] trophy) {
        this.context = context;
        this.ranking = ranking;
        this.player = player;
        this.time = time;
        this.date = date;
        this.trophy = trophy;
    }

    @Override
    public int getCount() {
        return player.length;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.leaderboard_list_item, parent, false);
            System.out.println(convertView);
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

        holder.rankingText.setText(ranking[position]);
        holder.playerText.setText(player[position]);
        holder.timeText.setText(time[position]);
        holder.dateText.setText(date[position]);
        if(position < trophy.length) {
            holder.trophyImage.setImageResource(trophy[position]);
        } else {
            holder.trophyImage.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView trophyImage;
        TextView rankingText;
        TextView playerText;
        TextView timeText;
        TextView dateText;
    }
}
