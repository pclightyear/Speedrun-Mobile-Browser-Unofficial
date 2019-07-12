package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.speedrun_mobile_unofficial.R;

/**
 * A placeholder fragment containing grid view to
 * display leader board of each category.
 */
public class BoardListFragment extends Fragment {
    private ListView mBoardListView;
    private BoardListAdapter mBoardListAdapter;
    private CategoryBoard categoryBoard;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        mBoardListAdapter = new BoardListAdapter(context, categoryBoard);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leaderboard_list, container, false);
        mBoardListView = rootView.findViewById(R.id.boardList);
        mBoardListView.setAdapter(mBoardListAdapter);
//        mBoardListView.setOnItemClickListener(onItemClickListener);
        return rootView;
    }

    public void setCateGoryBoard(CategoryBoard categoryBoard) {
        this.categoryBoard = categoryBoard;
        if(mBoardListAdapter == null) {
            mBoardListAdapter = new BoardListAdapter(context, categoryBoard);
        }
        if(mBoardListAdapter.getCategoryBoard() == null) {
            this.mBoardListAdapter.setCategoryBoard(categoryBoard);
        }
    }

    public  void forceListChange() {
        mBoardListAdapter.notifyDataSetChanged();
    }
//    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//        }
//    };
}
