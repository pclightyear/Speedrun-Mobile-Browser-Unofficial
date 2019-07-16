package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.speedrun_mobile_unofficial.R;

/**
 * A placeholder fragment containing grid view to
 * display leader board of each category.
 */
public class BoardListFragment extends Fragment {
    private RecyclerView mBoardListView;
    private BoardListAdapter mBoardListAdapter;
    private CategoryBoard mCategoryBoard;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leaderboard_list, container, false);
        mBoardListView = rootView.findViewById(R.id.boardList);
        mBoardListView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        mCategoryBoard = (CategoryBoard) getArguments().getSerializable("board");
        mBoardListAdapter = new BoardListAdapter(context, R.layout.fragment_leaderboard_list_item, mCategoryBoard.getLeaderboard());
        mBoardListView.setAdapter(mBoardListAdapter);
//        mBoardListView.setOnItemClickListener(onItemClickListener);
        return rootView;
    }

//    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//        }
//    };
}
