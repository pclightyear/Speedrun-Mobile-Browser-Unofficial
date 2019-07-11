package com.speedrun_mobile_unofficial.homepage;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.speedrun_mobile_unofficial.R;

public class GamePlaceholderFragmentAdapter extends BaseAdapter {
    private Context context;
    private final int[] imageId;

    public GamePlaceholderFragmentAdapter (Context context, int[] imageId) {
        this.context = context;
        this.imageId = imageId;
    }

    @Override
    public int getCount() {
        return imageId.length;
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
    public View getView(int position, View convertview, ViewGroup parent) {
        ViewHolder holder;

        if(convertview == null) {
            convertview = LayoutInflater.from(context).inflate(R.layout.fragment_game_grid_item, parent, false);
            holder = new ViewHolder();
            holder.gameCover = convertview.findViewById(R.id.grid_image);

            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        holder.gameCover.setImageResource(imageId[position]);

        return convertview;
    }

    private static class ViewHolder {
        ImageView gameCover;
    }
}
