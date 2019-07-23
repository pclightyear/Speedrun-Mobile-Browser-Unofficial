package com.speedrun_mobile_unofficial.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.speedrun_mobile_unofficial.R;
import com.speedrun_mobile_unofficial.entities.Enums;
import com.speedrun_mobile_unofficial.leaderboard.LeaderBoardActivity;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private int layoutId;
    private List<SearchListItem> itemList;

    public SearchListAdapter() {}

    public SearchListAdapter(Context context, int layoutId, List<SearchListItem> items) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.itemList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        final ImageView coverImage;
        final TextView nameText;
        String abbreviation;

        ViewHolder(View itemView) {
            super(itemView);
            this.coverImage = itemView.findViewById(R.id.search_item_cover_image);
            this.nameText = itemView.findViewById(R.id.search_item_name);
            itemView.setOnClickListener(this);
        }

        void bind(final int position) {
            SearchListItem item = itemList.get(position);

            abbreviation = item.getAbbreviation();
            Glide.with(context).load(item.getImageUri()).into(coverImage);
            nameText.setText(item.getName());
        }

        @Override
        public void onClick(View itemView) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, LeaderBoardActivity.class);
            intent.putExtra(Enums.EXTRA.GAMENAME, abbreviation);
            context.startActivity(intent);
        }
    }
}
