package com.speedrun_mobile_unofficial;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GamePlaceholderFragmentAdapter extends BaseAdapter {
    private Context context;
    private final int[] imageId;

    public GamePlaceholderFragmentAdapter (Context context) {
        Resources resource = context.getResources();
        this.context = context;

        TypedArray typedArray = resource.obtainTypedArray(R.array.images);
        int imageCount = typedArray.length();
        imageId = new int[imageCount];
        for(int i = 0; i < imageCount; i++) {
            imageId[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
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
